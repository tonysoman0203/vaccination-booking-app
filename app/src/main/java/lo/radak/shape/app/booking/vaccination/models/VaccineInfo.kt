package lo.radak.shape.app.booking.vaccination.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class VaccineInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("manufacturer")
                       val manufacturer: String,
    @SerializedName("dosages")
                       val dosages: Array<String>,
    @SerializedName("sideEffects")
                       val sideEffects: List<SideEffect>,
    var image: Int): Serializable
