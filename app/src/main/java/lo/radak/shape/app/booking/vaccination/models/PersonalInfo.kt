package lo.radak.shape.app.booking.vaccination.models

import java.io.Serializable

data class PersonalInfo(
    var firstName: String = "",
    var lastName: String = "",
    var id: String = "",
    var gender: String = "",
    var age: Int = 0
):Serializable {
}