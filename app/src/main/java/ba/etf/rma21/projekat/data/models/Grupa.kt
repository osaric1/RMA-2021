package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Grupa(
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String) {
    override fun toString(): String {
        return naziv
    }
}