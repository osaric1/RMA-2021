package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Predmet(
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String,
    @SerializedName("godina") val godina: Int
) {
    override fun toString(): String {
        return naziv
    }


}