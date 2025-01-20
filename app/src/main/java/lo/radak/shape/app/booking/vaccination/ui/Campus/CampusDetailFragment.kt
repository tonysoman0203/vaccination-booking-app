package lo.radak.shape.app.booking.vaccination.ui.Campus

import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lo.radak.shape.app.booking.vaccination.databinding.FragmentCampusDetailBinding
import lo.radak.shape.app.booking.vaccination.models.Campus

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BUNDLE_EXTRA_CAMPUS  = "campus"

/**
 * A simple [Fragment] subclass.
 * Use the [CampusDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class CampusDetailFragment : Fragment() {
    private var campus: Campus? = null
    private var _binding: FragmentCampusDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            campus = it.getSerializable(BUNDLE_EXTRA_CAMPUS) as Campus
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCampusDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = campus?.name
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            campusDetailImageview.setImageResource(campus!!.image)
            campusDetailName.text = campus?.name
            campusDetailAddress.text = campus?.address
            campusDetailEmail.text = campus?.email
            campusDetailTel.text = campus?.tel

            // open the google map
            campusDetailAddressButton.setOnClickListener { view ->
                val intent = Intent(ACTION_VIEW, Uri.parse(campus?.location))
                startActivity(intent)
            }

            // call phone
            campusDetailTelButton.setOnClickListener { view ->
                val intent = Intent(ACTION_DIAL, Uri.parse("tel:${campus?.tel}"))
                startActivity(intent)
            }

            // open the email app
            campusDetailEmailButton.setOnClickListener { view ->
                val intent = Intent(ACTION_VIEW, Uri.parse("mailto:${campus?.email}"))
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CampusDetail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Campus) =
            CampusDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BUNDLE_EXTRA_CAMPUS, param1)
                }
            }
    }
}