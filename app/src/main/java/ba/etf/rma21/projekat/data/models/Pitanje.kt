package ba.etf.rma21.projekat.data.models

import java.util.*

data class Pitanje(
        val naziv: String,
        val tekst: String,
        val opcije: List<String>,
        val tacan: Int
) {


}