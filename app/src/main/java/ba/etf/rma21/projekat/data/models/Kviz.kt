package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity
data class Kviz(
        @PrimaryKey @SerializedName("id") val id: Int,
        @ColumnInfo(name="naziv") @SerializedName("naziv") val naziv: String,
        @ColumnInfo(name="datumPocetka") @SerializedName("datumPocetak") val datumPocetka: Date,
        @ColumnInfo(name="datumKraj") @SerializedName("datumKraj") val datumKraj: Date?,
        @ColumnInfo(name="trajanje") @SerializedName("trajanje") val trajanje: Int,
        var predan: Boolean = false
) {


}