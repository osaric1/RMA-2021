package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ba.etf.rma21.projekat.Converters
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity
data class Pitanje(
        @PrimaryKey @SerializedName("id") val id: Int,
        @ColumnInfo(name="naziv") @SerializedName("naziv") val naziv: String,
        @ColumnInfo(name="tekstPitanja") @SerializedName("tekstPitanja") val tekstPitanja: String,
        @ColumnInfo(name="opcije") @SerializedName("opcije") val opcije: List<String>,
        @ColumnInfo(name="tacan") @SerializedName("tacan") val tacan: Int,
        @ColumnInfo(name = "KvizId") @SerializedName("KvizId") var KvizId: Int
) {


}