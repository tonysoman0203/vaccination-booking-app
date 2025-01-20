package lo.radak.shape.app.booking.vaccination.models

import java.io.Serializable

data class BookingInfo(
    var id: Long? = -1,
    var venue: Campus? = null,
    var personalInfo: PersonalInfo? = null,
    var date: String = "",
    var vaccineSelected: VaccineInfo? = null,
    var timeSlot: String =  ""
): Serializable
