package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

class Account(
    @SerializedName("id") val id: Int,
    @SerializedName("student") val student: String,
    @SerializedName("acHash") val acHash: String)
