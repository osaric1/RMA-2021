package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

class OdgovorKviz(
    @SerializedName("odgovor") val odgovor: Int,
    @SerializedName("pitanje") val pitanjeId: Int,
    @SerializedName("bodovi") val osvojeniBodovi: Int
){}