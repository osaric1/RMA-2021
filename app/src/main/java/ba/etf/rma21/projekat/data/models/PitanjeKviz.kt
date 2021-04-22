package ba.etf.rma21.projekat.data.models

import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import java.util.*

data class PitanjeKviz(
        val naziv: String,
        val kviz: String,
        val predmet: String,
        val bodovi: Float //bodovi po pitanju
) {
    override fun toString(): String {
        return naziv
    }
}