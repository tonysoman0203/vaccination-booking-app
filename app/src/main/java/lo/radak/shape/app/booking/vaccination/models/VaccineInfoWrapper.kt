package lo.radak.shape.app.booking.vaccination.models

import com.google.gson.annotations.SerializedName

data class VaccineInfoWrapper(
    @SerializedName("vaccineInfo")
    val vaccineInfo: List<VaccineInfo>) {
}