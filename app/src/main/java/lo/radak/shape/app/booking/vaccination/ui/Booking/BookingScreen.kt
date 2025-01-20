package lo.radak.shape.app.booking.vaccination.ui.Booking

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import lo.radak.shape.app.booking.vaccination.R
import lo.radak.shape.app.booking.vaccination.databinding.BannerViewholderBinding
import lo.radak.shape.app.booking.vaccination.databinding.BookingEmptyViewholderBinding
import lo.radak.shape.app.booking.vaccination.databinding.BookingViewholderBinding
import lo.radak.shape.app.booking.vaccination.models.BookingInfo
import lo.radak.shape.app.booking.vaccination.navigation.NavigationItem
import lo.radak.shape.app.booking.vaccination.ui.Booking.Detail.BookingDetailActivity

@ExperimentalFoundationApi
@Composable
fun BookingScreen(navController: NavHostController) {

    var _MyRecord: BookingInfo? by remember {
        mutableStateOf(null)
    }

    @Composable
    fun updateRecord() {
        val context = LocalContext.current
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ) ?: return

        val record = sharedPref.getString(context.getString(R.string.preference_key), "")
        _MyRecord = if (record.isNullOrBlank()) {
            null
        } else {
            Gson().fromJson(record, BookingInfo::class.java)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        val context = LocalContext.current
        var bookingTime = "1st"
        val openDialog = remember { mutableStateOf(false)  }

        updateRecord()

        if (_MyRecord != null) {
            bookingTime = "2nd"
            Card(
                elevation = 4.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .combinedClickable(
                        onClick = {
                            val intent = Intent().apply {
                                setClass(context, BookingDetailActivity::class.java)
                                putExtra(
                                    BookingDetailActivity.BUNDLE_EXTRA_BOOKING_DETAIL,
                                    _MyRecord
                                )
                            }
                            context.startActivity(intent)
                        },
                        onLongClick = {
                            openDialog.value = true
                        }
                    )

            ){
                AndroidViewBinding(BookingViewholderBinding::inflate) {
                    bookingVhDate.text = _MyRecord?.date
                    bookingVhPlace.text = _MyRecord?.venue?.name
                    bookingVhTime.text = _MyRecord?.timeSlot
                    bookingVhTextview.text = _MyRecord?.vaccineSelected?.name
                }
            }
        } else {
            Card(
                elevation = 4.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        // Go Create New Booking
                        val intent = Intent(context, BookingStepActivity::class.java)
                        context.startActivity(intent)
                    }
            ){
                AndroidViewBinding(BookingEmptyViewholderBinding::inflate) {
                    bookingEmptyVhTextview.text = "$bookingTime booking"
                }
            }
        }

        Row {
            Card(
                elevation = 4.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(NavigationItem.Campus.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
            ){
                AndroidViewBinding(BannerViewholderBinding::inflate) {
                    bannerVhImageview.setImageResource(R.drawable.campus_hkdi)
                    bannerVhTextview.text = "Campus"
                }
            }
            Card(
                elevation = 4.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(NavigationItem.Vaccine.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
            ){
                AndroidViewBinding(BannerViewholderBinding::inflate) {
                    bannerVhImageview.setImageResource(R.drawable.comirnaty)
                    bannerVhTextview.text = "Vaccine"
                }
            }
        }

        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = false
                },
                title = {
                    Text(text = "Cancel Booking")
                },
                text = {
                    Text("Do you really need to delete this booking? ")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                            val sharedPref = context.getSharedPreferences(
                                context.getString(R.string.preference_file_key),
                                Context.MODE_PRIVATE
                            )
                            sharedPref.edit().remove(context.getString(R.string.preference_key)).apply()
                        }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("No")
                    }
                }
            )
        }
    }
}


@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun BookingScreenPreview() {
    val context = LocalContext.current
    val navController = rememberNavController()
    BookingScreen(navController)
}