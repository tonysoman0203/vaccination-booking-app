package lo.radak.shape.app.booking.vaccination.ui.Booking.Step3

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import com.google.gson.Gson
import lo.radak.shape.app.booking.vaccination.MainActivity
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.databinding.FragmentBookingStep3Binding
import lo.radak.shape.app.booking.vaccination.manager.DataBaseManager
import lo.radak.shape.app.booking.vaccination.manager.NotificationManager
import lo.radak.shape.app.booking.vaccination.ui.Booking.ThankYou.BookingResponseFragment
import lo.radak.shape.app.booking.vaccination.ui.Booking.enum.BookingStep
import lo.radak.shape.app.booking.vaccination.ui.Booking.BookingViewModel
import lo.radak.shape.app.booking.vaccination.ui.Booking.Detail.BookingDetailActivity
import lo.radak.shape.app.booking.vaccination.ui.Booking.Detail.BookingDetailActivity.Companion.BUNDLE_EXTRA_BOOKING_DETAIL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class BookingStep3Fragment : Fragment() {

    companion object {
        fun newInstance() = BookingStep3Fragment()
        val TAG = BookingStep3Fragment::class.java.simpleName
    }

    var currentNotificationID = 0

    private lateinit var viewModel: BookingViewModel
    private var _binding: FragmentBookingStep3Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(BookingViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        // TODO: Use the ViewModel
        viewModel.updateCurrentStep(BookingStep.STEP3)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personalInfo = viewModel._personalInfo.value
        val bookingInfo = viewModel.bookingInfoLiveData.value

        binding.bookingStep3FnameText.text = personalInfo?.firstName
        binding.bookingStep3LnameText.text = personalInfo?.lastName
        binding.bookingStep3GenderText.text = personalInfo?.gender
        binding.bookingStep3AgeText.text = personalInfo?.age.toString()
        binding.bookingStep3IdText.text = personalInfo?.id
        binding.bookingStep3BtnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.bookingStep3CampusText.text = bookingInfo?.venue?.name
        binding.bookingStep3VaccineText.text = bookingInfo?.vaccineSelected?.name
        binding.bookingStep3TimeslotText.text = bookingInfo?.timeSlot
        binding.bookingStep3DateText.text = bookingInfo?.date

        binding.bookingStep3BtnConfirm.setOnClickListener {
            if (bookingInfo != null) {
                val today = Calendar.getInstance().time
                bookingInfo.id = today.time
            }

           val bookingInfoJson = Gson().toJson(bookingInfo)
            DataBaseManager.save(requireActivity(),
                getString(R.string.preference_file_key),
                getString(R.string.preference_key),
                bookingInfoJson
            )

            with(NotificationManagerCompat.from(requireContext())) {
                // notificationId is a unique int for each notification that you must define
                val confirmationNotification = NotificationManager.getNotification(requireContext(),
                    Intent(context, BookingDetailActivity::class.java).apply {
                         putExtra(BUNDLE_EXTRA_BOOKING_DETAIL, bookingInfo)
                    },
                    "Your Booking has been Confirmed!"
                )
                currentNotificationID++
                if (confirmationNotification != null) {
                    notify(currentNotificationID, confirmationNotification)
                }
            }

            // schedule the reminder notification for the future
            val dateTime = bookingInfo?.date
            val timeslot = bookingInfo?.timeSlot
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm").parse("$dateTime $timeslot")

            val duration = 60 * 1000

            val sendDate =  sdf.time - duration

            val reminder = NotificationManager.getNotification(requireContext(),
                Intent(context, MainActivity::class.java),
                "Your Booking has been coming on tomorrow!!"
            )
            if (reminder != null) {
                NotificationManager.scheduleNotification(
                    requireContext(),
                    reminder,
                    sendDate
                )
            }


            parentFragmentManager.beginTransaction()
                .replace(R.id.booking_framelayout, BookingResponseFragment.newInstance())
                .addToBackStack(TAG)
                .commit()
        }
    }
}