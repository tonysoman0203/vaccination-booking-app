package lo.radak.shape.app.booking.vaccination.ui.Booking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.databinding.ActivityBookingBinding
import lo.radak.shape.app.booking.vaccination.ui.Booking.Step1.BookingStep1Fragment
import lo.radak.shape.app.booking.vaccination.ui.Booking.enum.BookingStep

class BookingStepActivity : AppCompatActivity() {

    companion object {
        val TAG = BookingStepActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityBookingBinding

    private lateinit var viewModel: BookingViewModel

    private val mCurrentStepObserver: Observer<BookingStep> = Observer {
        Log.d(TAG, it.value)

        when (it){
            BookingStep.STEP1 -> {
                updateToolbar("Step 1/3")
            }
            BookingStep.STEP2 -> {

                updateToolbar("Step 2/3")
            }
            BookingStep.STEP3 -> {

                updateToolbar("Step 3/3")
            }
            else -> {
                updateToolbar("Finish")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        viewModel = ViewModelProvider(this).get(BookingViewModel::class.java)

        viewModel.currentStep.observe(this, mCurrentStepObserver)

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            val fragments = supportFragmentManager.backStackEntryCount
            Log.d(TAG, "fragment count = $fragments")
            if (fragments > 1) {
                supportFragmentManager.popBackStack()
            } else {
                finish()
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.booking_framelayout, BookingStep1Fragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun updateToolbar(toolbarTitle: String) {
        title = toolbarTitle
    }
}