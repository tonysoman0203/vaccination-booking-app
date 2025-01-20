package lo.radak.shape.app.booking.vaccination.models

import java.io.Serializable

data class Campus(
    val name: String,
    val address: String,
    val tel: String,
    val location: String,
    val email: String,
    val image: Int
): Serializable


