package lo.radak.shape.app.booking.vaccination.ui.Campus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.databinding.ActivityCampusDetailBinding
import lo.radak.shape.app.booking.vaccination.models.Campus

class CampusDetailActivity : AppCompatActivity() {

    companion object {
        val BUNDLE_EXTRA_CAMPUS = "campus"
        val BUNDLE_EXTRA_CAMPUS_LIST = "BUNDLE_EXTRA_CAMPUS_LIST"
    }

    private lateinit var mCampus: Campus

    private var mCampusList = arrayListOf<Campus>()
    private lateinit var binding: ActivityCampusDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCampusDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mCampus = intent.extras?.get(BUNDLE_EXTRA_CAMPUS) as Campus
        mCampusList = intent.extras?.getSerializable(BUNDLE_EXTRA_CAMPUS_LIST) as ArrayList<Campus>

        setSupportActionBar(binding.campusDetailToolBar)
        binding.campusDetailToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.campusDetailToolBar.setNavigationOnClickListener {
            finish()
        }
        val element = mCampusList.find { it.name == mCampus.name }
        val index = mCampusList.indexOf(element)

        binding.campusDetailViewpager.adapter = CampusDetailViewPagerAdapter()
        binding.campusDetailViewpager.setCurrentItem(index ?: 0, false)

    }

    inner class CampusDetailViewPagerAdapter: FragmentStateAdapter(this) {
        @NonNull
        override fun createFragment(position: Int): Fragment {
            return CampusDetailFragment.newInstance(mCampusList[position])
        }

        override fun getItemCount(): Int {
            return mCampusList.size
        }
    }
}