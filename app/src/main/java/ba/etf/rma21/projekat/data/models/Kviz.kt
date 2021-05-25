package ba.etf.rma21.projekat.data.models

import java.util.*

data class Kviz(
        val id: Int,
        val naziv: String,
        val datumPocetka: Date,
        val datumKraj: Date?,
        val trajanje: Int
) {


}