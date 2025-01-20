package lo.radak.shape.app.booking.vaccination.ui.Booking.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.databinding.BookingDetailActivityBinding
import lo.radak.shape.app.booking.vaccination.models.BookingInfo
import lo.radak.shape.app.booking.vaccination.models.Campus
import lo.radak.shape.app.booking.vaccination.ui.Campus.CampusDetailActivity

class BookingDetailActivity : AppCompatActivity() {

    companion object {
        val BUNDLE_EXTRA_BOOKING_DETAIL = "BUNDLE_EXTRA_BOOKING_DETAIL"
    }

    private lateinit var binding: BookingDetailActivityBinding
    private lateinit var bookingInfo: BookingInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookingDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        bookingInfo = intent.extras?.get(BUNDLE_EXTRA_BOOKING_DETAIL) as BookingInfo

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BookingDetailFragment.newInstance(bookingInfo))
                .commitNow()
        }
    }
}