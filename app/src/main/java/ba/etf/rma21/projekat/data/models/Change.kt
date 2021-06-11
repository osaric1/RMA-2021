package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

class Change(
    @SerializedName("changed") val changed: Boolean
) {
}