package lo.radak.shape.app.booking.vaccination.ui.Booking.Detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import lo.radak.shape.app.booking.vaccination.databinding.FragmentBookingDetailBinding
import lo.radak.shape.app.booking.vaccination.models.BookingInfo
import lo.radak.shape.app.booking.vaccination.ui.Booking.BookingViewModel

class BookingDetailFragment : Fragment() {

    companion object {
        fun newInstance() = BookingDetailFragment()

        @JvmStatic
        fun newInstance(param1: BookingInfo) = BookingDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(BookingDetailActivity.BUNDLE_EXTRA_BOOKING_DETAIL, param1)
            }
        }
    }

    private var _binding: FragmentBookingDetailBinding? = null
    private val binding get() = _binding!!
    private var bookingInfo: BookingInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookingInfo =
                it.getSerializable(BookingDetailActivity.BUNDLE_EXTRA_BOOKING_DETAIL) as BookingInfo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bookingDetailVaccineText.text = bookingInfo?.vaccineSelected?.name
        binding.bookingDetailDateText.text = bookingInfo?.date
        binding.bookingDetailTimeText.text = bookingInfo?.timeSlot
        binding.bookingDetailCampusText.text = bookingInfo?.venue?.name
        val personalInfo = bookingInfo?.personalInfo
        binding.bookingDetailFNameText.text = personalInfo?.firstName
        binding.bookingDetailLNameText.text = personalInfo?.lastName
        binding.bookingDetailIdText.text = personalInfo?.id
        binding.bookingDetailGenderText.text = personalInfo?.gender
        binding.bookingDetailAgeText.text = personalInfo?.age.toString()

        // open the google map
        binding.bookingDetailBtnMap.setOnClickListener { view ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bookingInfo?.venue?.location))
            startActivity(intent)
        }
        // process to call phone
        binding.bookingDetailBtnCall.setOnClickListener { view ->
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${bookingInfo?.venue?.tel}"))
            startActivity(intent)
        }
        // open the email app
        binding.bookingDetailBtnMail.setOnClickListener { view ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:${bookingInfo?.venue?.email}"))
            startActivity(intent)
        }
    }

}