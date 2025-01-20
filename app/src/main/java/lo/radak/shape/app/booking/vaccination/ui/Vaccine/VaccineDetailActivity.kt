package lo.radak.shape.app.booking.vaccination.ui.Vaccine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.databinding.ActivityCampusDetailBinding
import lo.radak.shape.app.booking.vaccination.databinding.ActivityVaccineDetailBinding
import lo.radak.shape.app.booking.vaccination.models.Campus
import lo.radak.shape.app.booking.vaccination.models.VaccineInfo

class VaccineDetailActivity : AppCompatActivity() {

    companion object {
        val BUNDLE_EXTRA_VACCINE = "BUNDLE_EXTRA_VACCINE"
        val BUNDLE_EXTRA_VACCINE_LIST = "BUNDLE_EXTRA_VACCINE_LIST"
    }

    private lateinit var mCampus: VaccineInfo

    private var mCampusList = arrayListOf<VaccineInfo>()
    private lateinit var binding: ActivityVaccineDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVaccineDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mCampus = intent.extras?.get(BUNDLE_EXTRA_VACCINE) as VaccineInfo
        mCampusList = intent.extras?.getSerializable(BUNDLE_EXTRA_VACCINE_LIST) as ArrayList<VaccineInfo>

        setSupportActionBar(binding.vaccineDetailToolBar)
        binding.vaccineDetailToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.vaccineDetailToolBar.setNavigationOnClickListener {
            finish()
        }
        val element = mCampusList.find { it.name == mCampus.name }
        val index = mCampusList.indexOf(element)
        binding.vaccineDetailViewpager.adapter = VaccineDetailViewPagerAdapter()
        binding.vaccineDetailViewpager.setCurrentItem(index ?: 0, false)

    }

    inner class VaccineDetailViewPagerAdapter: FragmentStateAdapter(this) {
        @NonNull
        override fun createFragment(position: Int): Fragment {
            return VaccineDetailFragment.newInstance(mCampusList[position])
        }

        override fun getItemCount(): Int {
            return mCampusList.size
        }
    }
}