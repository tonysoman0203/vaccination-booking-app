package lo.radak.shape.app.booking.vaccination.ui.Booking.Step2

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.gson.Gson
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.databinding.FragmentBookingStep2Binding
import lo.radak.shape.app.booking.vaccination.manager.DataBaseManager
import lo.radak.shape.app.booking.vaccination.models.BookingInfo
import lo.radak.shape.app.booking.vaccination.models.Campus
import lo.radak.shape.app.booking.vaccination.models.VaccineInfo
import lo.radak.shape.app.booking.vaccination.models.VaccineInfoWrapper
import lo.radak.shape.app.booking.vaccination.ui.Booking.Step3.BookingStep3Fragment
import lo.radak.shape.app.booking.vaccination.ui.Booking.enum.BookingStep
import lo.radak.shape.app.booking.vaccination.ui.Booking.BookingViewModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BookingStep2Fragment : Fragment() {

    companion object {
        val TAG = BookingStep2Fragment::class.java.simpleName
        fun newInstance() = BookingStep2Fragment()
    }

    private lateinit var viewModel: BookingViewModel
    private var _binding: FragmentBookingStep2Binding? = null
    private val binding get() = _binding!!

    private lateinit var coronaVac: VaccineInfo
    private lateinit var comirnaty: VaccineInfo

    private var mSelectedVaccine: VaccineInfo? = null
    private var mSelectedCampus: Campus? = null
    private var mSelectedTimeSlot: String? = null
    private var mSelectedDateTime: String? = null

    private val campusData: ArrayList<Campus> by lazy {
        return@lazy arrayListOf(
            Campus(
                name = getString(R.string.campus_tsing_yi_name),
                address = getString(R.string.campus_tsing_yi_address),
                tel = getString(R.string.campus_tsing_yi_tel),
                location = getString(R.string.campus_tsing_yi_location),
                email = getString(R.string.campus_tsing_yi_email),
                image = R.drawable.campus_tsing_yi
            ),
            Campus(
                name = getString(R.string.campus_tuen_mun_name),
                address = getString(R.string.campus_tuen_mun_address),
                tel = getString(R.string.campus_tuen_mun_tel),
                location = getString(R.string.campus_tuen_mun_location),
                email = getString(R.string.campus_tuen_mun_email),
                image = R.drawable.campus_tuen_mun
            ),
            Campus(
                name = getString(R.string.campus_chai_wan_name),
                address = getString(R.string.campus_chai_wan_address),
                tel = getString(R.string.campus_chai_wan_tel),
                location = getString(R.string.campus_chai_wan_location),
                email = getString(R.string.campus_chai_wan_email),
                image = R.drawable.campus_chai_wan
            ),
            Campus(
                name = getString(R.string.campus_hkdi_name),
                address = getString(R.string.campus_hkdi_address),
                tel = getString(R.string.campus_hkdi_tel),
                location = getString(R.string.campus_hkdi_location),
                email = getString(R.string.campus_hkdi_email),
                image = R.drawable.campus_hkdi
            )
        )
    }
    
    private val mSelectedVaccineObserver: Observer<Pair<VaccineInfo, Boolean>> = Observer {
        val value = it.first
        mSelectedVaccine = value
        if (value.name == comirnaty.name && it.second) {
            binding.bookingStep2VaccineComirnatyChecked.visibility = View.VISIBLE
            binding.bookingStep2VaccineCoronavacChecked.visibility = View.GONE
            binding.bookingStep2VaccineTextview.text = "Vaccine: ${comirnaty.name}"
        } else if (value.name == coronaVac.name && it.second) {
            binding.bookingStep2VaccineCoronavacChecked.visibility = View.VISIBLE
            binding.bookingStep2VaccineComirnatyChecked.visibility = View.GONE
            binding.bookingStep2VaccineTextview.text = "Vaccine: ${coronaVac.name}"
        } else {
            binding.bookingStep2VaccineComirnatyChecked.visibility = View.GONE
            binding.bookingStep2VaccineCoronavacChecked.visibility = View.GONE
        }
    }

    private val mShowDatePickerObserver: Observer<Boolean> = Observer {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(requireActivity(), { view, year, monthOfYear, dayOfMonth ->

            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, monthOfYear)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy/MM/dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            mSelectedDateTime = sdf.format(c.time)
            // Display Selected date in textbox
            binding.bookingStep2DateEdittext.setText(sdf.format(c.time))

        }, year, month, day)

        dpd.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(BookingViewModel::class.java)
        val vaccineInfo = initData(requireContext()).vaccineInfo
        coronaVac = vaccineInfo[1]
        comirnaty = vaccineInfo[0]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // TODO: Use the ViewModel
        viewModel.updateCurrentStep(BookingStep.STEP2)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedVaccine.observe(requireActivity(), mSelectedVaccineObserver)
        viewModel.showDatePickerEvent.observe(requireActivity(), mShowDatePickerObserver)

        binding.bookingStep2CampusSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val campusName = p0?.getItemAtPosition(p2).toString()
                val campus = campusData.find { it.name == campusName }
                mSelectedCampus = campus
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.bookingStep2TimeslotSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mSelectedTimeSlot = p0?.getItemAtPosition(p2).toString()
                // generate capacity
                if (mSelectedVaccine != null && mSelectedCampus!=null && mSelectedDateTime!=null) {
                    val key = "${mSelectedVaccine?.name}_${mSelectedCampus?.name}_${mSelectedDateTime}_${mSelectedTimeSlot}"
                    val capacity = DataBaseManager.getCapacityByKey(
                        requireActivity(),
                        getString(R.string.preference_file_key),
                        key
                    )
                    binding.bookingStep2CapacityTextview.text = "Capacity: ${capacity}"
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.bookingStep2CapacityTextview.text = "Capacity:"
            }

        }

        binding.bookingStep2BtnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.bookingStep2BtnNext.setOnClickListener {

            try {
                val campus = campusData.find {
                    it.name == binding.bookingStep2CampusSpinner.selectedItem.toString()
                }?: throw Exception("No campus data")

                viewModel.updateBookingInfo(BookingInfo(
                    venue = campus,
                    date = mSelectedDateTime ?: throw Exception("No DateTime Selected"),
                    vaccineSelected = mSelectedVaccine?: throw Exception("No Vaccine Selected"),
                    timeSlot = mSelectedTimeSlot ?: throw Exception("No TimeSlot Selected")
                ))

                parentFragmentManager.beginTransaction()
                    .replace(R.id.booking_framelayout, BookingStep3Fragment.newInstance())
                    .addToBackStack(TAG)
                    .commit()

            } catch (e: Exception){
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage(e.message)
                    .setNegativeButton("OK", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            p0?.dismiss()
                        }
                    })
                    .show()
            }

        }

        binding.bookingStep2VaccineComirnatyFramelayout.setOnClickListener {
            viewModel.updateSelectedVaccine(Pair(comirnaty, true))
        }

        binding.bookingStep2VaccineCoronavacFramelayout.setOnClickListener {
            viewModel.updateSelectedVaccine(Pair(coronaVac, true))
        }

        binding.bookingStep2DateEdittext.setOnClickListener {
            viewModel.showDatePicker()
        }
    }

    private fun initData(context: Context): VaccineInfoWrapper {
        val json = loadJSONFromAsset(context)
        return Gson().fromJson(json, VaccineInfoWrapper::class.java)
    }

    private fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        json = try {
            val inputStream = context.assets.open("vaccine_info.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}