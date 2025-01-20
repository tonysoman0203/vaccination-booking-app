package lo.radak.shape.app.booking.vaccination.ui.Vaccine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.databinding.FragmentVaccineDetailBinding
import lo.radak.shape.app.booking.vaccination.models.SideEffect
import lo.radak.shape.app.booking.vaccination.models.VaccineInfo

// TODO: Rename parameter arguments, choose names that matc
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BUNDLE_EXTRA_VACCINE  = "BUNDLE_EXTRA_VACCINE"

/**
 * A simple [Fragment] subclass.
 * Use the [CampusDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class VaccineDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var vaccineInfo: VaccineInfo? = null
    private var _binding: FragmentVaccineDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vaccineInfo = it.getSerializable(BUNDLE_EXTRA_VACCINE) as VaccineInfo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVaccineDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            requireActivity().title = vaccineInfo?.name
            vaccineDetailName.text = vaccineInfo?.name
            vaccineDetailManufactureText.text = vaccineInfo?.name
            vaccineDetailImageview.setImageResource(vaccineInfo!!.image)
            var string = ""
            vaccineInfo?.dosages?.forEachIndexed { index: Int, dosage: String ->
                string += "${index + 1}. ${dosage}\n"
            }
            vaccineDetailDosagesText.text = string
            vaccineDetailPossibleSideEffectComposeView.apply {
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
                )
                setContent {
                    SideEffectList(vaccineInfo!!.sideEffects)
                }
            }
        }
    }

    @Composable
    fun SideEffectList(sideEffects: List<SideEffect>) {
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .border(1.dp, Color(context.getColor(R.color.colorAccent)), RectangleShape)
                .fillMaxWidth()
        ) {
            sideEffects.forEach {
                Row(
                    modifier = Modifier
                        .border(width = 1.dp, shape = RectangleShape, color = Color.Black)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceAround) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .width(90.dp)) {
                        Text(it.level, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Column(
                        modifier = Modifier.border(width = 1.dp, shape = RectangleShape, color = Color.Black)
                    ) {
                        it.sideEffectInfo.forEach {
                            Text("- $it",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                                    .padding(
                                    start = 8.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                ).clipToBounds()
                            )
                        }
                    }
                }
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
         * @param vaccineInfo Parameter 1.
         * @return A new instance of fragment VaccineDetail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(vaccineInfo: VaccineInfo) =
            VaccineDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BUNDLE_EXTRA_VACCINE, vaccineInfo)
                }
            }
    }
}