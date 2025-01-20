package lo.radak.shape.app.booking.vaccination.ui.Campus

import BannerTheme
import android.content.Intent
import android.content.res.Configuration
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.models.Campus
import java.io.Serializable

@Composable
fun CampusList(campuses: List<Campus>) {
    val listState = rememberLazyListState()
    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        state = listState
    ) {
        items(campuses) { campus ->
            CampusRow(campus, campuses)
        }
    }
}

@Composable
fun CampusRow(campus: Campus, campuses: List<Campus>) {
    val context = LocalContext.current
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                val intent = Intent().apply {
                    setClass(context, CampusDetailActivity::class.java)
                    putExtra(CampusDetailActivity.BUNDLE_EXTRA_CAMPUS, campus)
                    putExtra(
                        CampusDetailActivity.BUNDLE_EXTRA_CAMPUS_LIST,
                        campuses as Serializable
                    )
                }
                context.startActivity(intent)
            }
    ) {
//        AndroidViewBinding(BannerViewholderBinding::inflate) {
//            bannerVhImageview.setImageResource(campus.image)
//            bannerVhImageview.scaleType = ImageView.ScaleType.CENTER_CROP
//            bannerVhTextview.text = campus.name
//        }
        BannerCard(campus = campus)
    }
}



@Composable
fun BannerCard(campus: Campus) {
    BannerTheme() {
        Surface (
            elevation = 30.dp,
            color = MaterialTheme.colors.surface, // color will be adjusted for elevation
                ){
            Row(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .background(colors.primary)
            ) {
                Image(
                    painter = painterResource(campus.image),
                    contentDescription = "Contact profile picture",
                    modifier = Modifier
                        // Set image size to 40 dp
                        .size(40.dp)
                        // Clip image to be shaped as a circle
                        .clip(CircleShape)
                )

                // Add a horizontal space between the image and the column
                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(text = campus.name, color = colors.secondary)
                    // Add a vertical space between the author and message texts
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = campus.address, color =  colors.secondary)
                }
            }
        }

    }

}

@Preview(name = "Light Mode")
@Preview(showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,)
@Composable
fun CampusListPreview() {
    val context = LocalContext.current
    val campuses = arrayListOf(
        Campus(
            name = context.getString(R.string.campus_tsing_yi_name),
            address = context.getString(R.string.campus_tsing_yi_address),
            tel = context.getString(R.string.campus_tsing_yi_tel),
            location = context.getString(R.string.campus_tsing_yi_location),
            email = context.getString(R.string.campus_tsing_yi_email),
            image = R.drawable.campus_tsing_yi
        ),
        Campus(
            name = context.getString(R.string.campus_tuen_mun_name),
            address = context.getString(R.string.campus_tuen_mun_address),
            tel = context.getString(R.string.campus_tuen_mun_tel),
            location = context.getString(R.string.campus_tuen_mun_location),
            email = context.getString(R.string.campus_tuen_mun_email),
            image = R.drawable.campus_tuen_mun
        ),
        Campus(
            name = context.getString(R.string.campus_chai_wan_name),
            address = context.getString(R.string.campus_chai_wan_address),
            tel = context.getString(R.string.campus_chai_wan_tel),
            location = context.getString(R.string.campus_chai_wan_location),
            email = context.getString(R.string.campus_chai_wan_email),
            image = R.drawable.campus_chai_wan
        ),
        Campus(
            name = context.getString(R.string.campus_hkdi_name),
            address = context.getString(R.string.campus_hkdi_address),
            tel = context.getString(R.string.campus_hkdi_tel),
            location = context.getString(R.string.campus_hkdi_location),
            email = context.getString(R.string.campus_hkdi_email),
            image = R.drawable.campus_hkdi
        )
    )

    CampusList(campuses = campuses)
}


@Composable
fun CampusScreen(navController: NavController) {
    val context = LocalContext.current
    val campuses = arrayListOf(
        Campus(
            name = context.getString(R.string.campus_tsing_yi_name),
            address = context.getString(R.string.campus_tsing_yi_address),
            tel = context.getString(R.string.campus_tsing_yi_tel),
            location = context.getString(R.string.campus_tsing_yi_location),
            email = context.getString(R.string.campus_tsing_yi_email),
            image = R.drawable.campus_tsing_yi
        ),
        Campus(
            name = context.getString(R.string.campus_tuen_mun_name),
            address = context.getString(R.string.campus_tuen_mun_address),
            tel = context.getString(R.string.campus_tuen_mun_tel),
            location = context.getString(R.string.campus_tuen_mun_location),
            email = context.getString(R.string.campus_tuen_mun_email),
            image = R.drawable.campus_tuen_mun
        ),
        Campus(
            name = context.getString(R.string.campus_chai_wan_name),
            address = context.getString(R.string.campus_chai_wan_address),
            tel = context.getString(R.string.campus_chai_wan_tel),
            location = context.getString(R.string.campus_chai_wan_location),
            email = context.getString(R.string.campus_chai_wan_email),
            image = R.drawable.campus_chai_wan
        ),
        Campus(
            name = context.getString(R.string.campus_hkdi_name),
            address = context.getString(R.string.campus_hkdi_address),
            tel = context.getString(R.string.campus_hkdi_tel),
            location = context.getString(R.string.campus_hkdi_location),
            email = context.getString(R.string.campus_hkdi_email),
            image = R.drawable.campus_hkdi
        )
    )

    CampusList(campuses = campuses)
}

@Preview(showBackground = true)
@Composable
fun CampusScreenPreview() {
    val navController = rememberNavController()
    CampusScreen(navController)
}