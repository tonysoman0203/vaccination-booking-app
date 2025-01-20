package lo.radak.shape.app.booking.vaccination.ui.Vaccine

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.gson.Gson
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.databinding.BannerViewholderBinding
import lo.radak.shape.app.booking.vaccination.models.VaccineInfo
import lo.radak.shape.app.booking.vaccination.models.VaccineInfoWrapper
import java.io.IOException
import java.io.Serializable


@Composable
fun VaccineList(vaccineInfos: List<VaccineInfo>) {
    LazyColumn(
        Modifier.background(Color.LightGray).fillMaxHeight()
    ) {
        items(vaccineInfos) { vaccineInfo ->
            VaccineRow(vaccineInfo, vaccineInfos)
        }
    }
}

@Composable
fun VaccineRow(vaccineInfo: VaccineInfo, vaccineInfos: List<VaccineInfo>) {
    val context = LocalContext.current
    if (vaccineInfo.name == "CoronaVac") {
        vaccineInfo.image = R.drawable.coronavac
    } else {
        vaccineInfo.image = R.drawable.comirnaty
    }
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                val intent = Intent().apply {
                    setClass(context, VaccineDetailActivity::class.java)
                    putExtra(VaccineDetailActivity.BUNDLE_EXTRA_VACCINE, vaccineInfo)
                    putExtra(
                        VaccineDetailActivity.BUNDLE_EXTRA_VACCINE_LIST,
                        vaccineInfos as Serializable
                    )
                }
                context.startActivity(intent)
            }
    ) {
        AndroidViewBinding(BannerViewholderBinding::inflate) {
            bannerVhImageview.setImageResource(vaccineInfo.image)
            bannerVhImageview.scaleType = ImageView.ScaleType.FIT_XY
            bannerVhTextview.text = vaccineInfo.name
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VaccineListPreview() {
    val context = LocalContext.current
    val vaccineData = initData(context)
    val vaccineInfos = vaccineData.vaccineInfo
    VaccineList(vaccineInfos = vaccineInfos)
}

@Composable
fun VaccineScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        val context = LocalContext.current
        val vaccineData = initData(context)
        val vaccineInfos = vaccineData.vaccineInfo
        VaccineList(vaccineInfos = vaccineInfos)
    }
}

fun initData(context: Context): VaccineInfoWrapper {
    val json = loadJSONFromAsset(context)
    val gson = Gson()
    val vaccineInfo = gson.fromJson(json, VaccineInfoWrapper::class.java)
    Log.d("Main", vaccineInfo.toString())
    return vaccineInfo
}

fun loadJSONFromAsset(context: Context): String? {
    var json: String? = null
    json = try {
        val inputStream = context.assets.open("vaccine_info.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}

@Preview(showBackground = true)
@Composable
fun VaccineScreenPreview() {
    VaccineScreen()
}