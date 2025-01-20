package lo.radak.shape.app.booking.vaccination.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.sharp.BusinessCenter
import androidx.compose.material.icons.sharp.Vaccines
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Booking : NavigationItem("Booking", Icons.Default.Home, "Booking")
    object Campus : NavigationItem("Campus", Icons.Sharp.BusinessCenter, "Campus")
    object Vaccine : NavigationItem("Vaccine", Icons.Sharp.Vaccines, "Vaccine")
}