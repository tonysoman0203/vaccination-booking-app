package lo.radak.shape.app.booking.vaccination.ui.Booking.Step1

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.databinding.FragmentBookingStep1Binding
import lo.radak.shape.app.booking.vaccination.models.PersonalInfo
import lo.radak.shape.app.booking.vaccination.ui.Booking.Step2.BookingStep2Fragment
import lo.radak.shape.app.booking.vaccination.ui.Booking.enum.BookingStep
import lo.radak.shape.app.booking.vaccination.ui.Booking.BookingViewModel

class BookingStep1Fragment : Fragment() {

    companion object {
        val TAG = BookingStep1Fragment::class.java.simpleName
        fun newInstance() = BookingStep1Fragment()
    }

    private lateinit var viewModel: BookingViewModel
    private var _binding: FragmentBookingStep1Binding? = null
    private val binding get() = _binding!!
    private var personalInfo: PersonalInfo = PersonalInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(BookingViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // TODO: Use the ViewModel
        viewModel.updateCurrentStep(BookingStep.STEP1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextWatcher()

        binding.bookingStep1BtnNext.setOnClickListener {
            if (validateFirstName() && validateLastName() && validateId() && validateAge()) {
                personalInfo.firstName = binding.bookingStep1FnameEdittext.text.toString()
                personalInfo.lastName = binding.bookingStep1LnameEdittext.text.toString()
                personalInfo.id = binding.bookingStep1IdEdittext.text.toString()
                personalInfo.gender = binding.bookingStep1GenderSpinner.selectedItem.toString()
                personalInfo.age = binding.bookingStep1AgeEdittext.text.toString().toInt()
                viewModel.updatePersonalInfo(personalInfo)
                // navigate to next step
                parentFragmentManager.beginTransaction()
                    .replace(R.id.booking_framelayout, BookingStep2Fragment.newInstance())
                    .addToBackStack(TAG)
                    .commit()
            } else {
               return@setOnClickListener
            }

        }
    }

    private fun setTextWatcher() {
        binding.bookingStep1FnameEdittext.addTextChangedListener(TextFieldValidation(binding.bookingStep1FnameEdittext))
        binding.bookingStep1LnameEdittext.addTextChangedListener(TextFieldValidation(binding.bookingStep1LnameEdittext))
        binding.bookingStep1AgeEdittext.addTextChangedListener(TextFieldValidation(binding.bookingStep1AgeEdittext))
        binding.bookingStep1IdEdittext.addTextChangedListener(TextFieldValidation(binding.bookingStep1IdEdittext))
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.booking_step1_fname_edittext -> {
                    validateFirstName()
                }
                R.id.booking_step1_lname_layout -> {
                    validateLastName()
                }
                R.id.booking_step1_age_edittext -> {
                    validateAge()
                }
                R.id.booking_step1_id_edittext -> {
                    validateId()
                }
            }
        }
    }

    private fun validateFirstName(): Boolean {
        when {
            binding.bookingStep1FnameEdittext.text.toString().trim().isEmpty() -> {
                binding.bookingStep1FnameLayout.error = "Please Input first name"
                binding.bookingStep1FnameEdittext.requestFocus()
                return false
            }
            else -> {
                binding.bookingStep1FnameLayout.isErrorEnabled = false
            }
        }
        return true
    }

    private fun validateLastName(): Boolean {
        when {
            binding.bookingStep1LnameEdittext.text.toString().trim().isEmpty() -> {
                binding.bookingStep1LnameLayout.error = "Please Input last name"
                binding.bookingStep1LnameEdittext.requestFocus()
                return false
            }
            else -> {
                binding.bookingStep1LnameLayout.isErrorEnabled = false
            }
        }
        return true
    }

    private fun validateId(): Boolean {
        when {
            binding.bookingStep1IdEdittext.text.toString().trim().isEmpty() -> {
                binding.bookingStep1IdLayout.error = "Please Input Your Id"
                binding.bookingStep1IdEdittext.requestFocus()
                return false
            }
            else -> {
                binding.bookingStep1IdLayout.isErrorEnabled = false
            }
        }
        return true
    }

    private fun validateAge(): Boolean {
        when {
            binding.bookingStep1AgeEdittext.text.toString().trim().isEmpty() -> {
                binding.bookingStep1AgeLayout.error = "Please Input Your Age"
                binding.bookingStep1AgeEdittext.requestFocus()
                return false
            }
            binding.bookingStep1AgeEdittext.text.toString().toIntOrNull() == null -> {
                binding.bookingStep1AgeLayout.error = "Please Input Your Age in numeric"
                binding.bookingStep1AgeEdittext.requestFocus()
                return false
            }
            else -> {
                binding.bookingStep1IdLayout.isErrorEnabled = false
            }
        }
        return true
    }
}