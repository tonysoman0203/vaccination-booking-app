package lo.radak.shape.app.booking.vaccination.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SideEffect(val level: String,
                      @SerializedName("sideEffect")
                      val sideEffectInfo: Array<String>
                      ): Serializable
