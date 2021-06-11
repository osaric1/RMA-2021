package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Grupa(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name="naziv") @SerializedName("naziv") val naziv: String,
    @ColumnInfo(name="predmetId") @SerializedName("PredmetId") val predmetId: Int) {
    override fun toString(): String {
        return naziv
    }
}