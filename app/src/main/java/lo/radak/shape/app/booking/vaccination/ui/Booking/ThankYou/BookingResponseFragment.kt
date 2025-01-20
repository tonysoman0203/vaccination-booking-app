package lo.radak.shape.app.booking.vaccination.ui.Booking.ThankYou

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import lo.radak.shape.app.booking.vaccination.databinding.FragmentBookingResponseBinding
import lo.radak.shape.app.booking.vaccination.manager.NotificationManager
import lo.radak.shape.app.booking.vaccination.ui.Booking.BookingViewModel
import lo.radak.shape.app.booking.vaccination.ui.Booking.Detail.BookingDetailActivity
import lo.radak.shape.app.booking.vaccination.ui.Booking.enum.BookingStep

class BookingResponseFragment : Fragment() {

    companion object {
        fun newInstance() = BookingResponseFragment()
        val TAG = BookingResponseFragment::class.java.simpleName

    }

    private lateinit var viewModel: BookingViewModel
    private var _binding: FragmentBookingResponseBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(BookingViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        // TODO: Use the ViewModel
        viewModel.updateCurrentStep(BookingStep.SUCCESS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingResponseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookingInfo = viewModel.bookingInfoLiveData.value
        binding.bookingResposneResponseText.text = "Booking Success ! \n Your Booking Reference Id is =${bookingInfo?.id}"

        binding.bookingResponseButton.setOnClickListener {
            val intent = Intent().apply {
                context?.let { it1 -> setClass(it1, BookingDetailActivity::class.java) }
                putExtra(BookingDetailActivity.BUNDLE_EXTRA_BOOKING_DETAIL, bookingInfo)
            }
            context?.startActivity(intent)
            activity?.finish()
        }
    }
}