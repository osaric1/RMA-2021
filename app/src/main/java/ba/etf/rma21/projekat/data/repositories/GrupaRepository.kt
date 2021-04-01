package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa

class GrupaRepository {
    companion object {
        private var grupe: List<Grupa>
        init {
            grupe = listOf(Grupa("Grupa 1", "IM"),
            Grupa("Grupa 1", "IM"),
            Grupa("Grupa 1", "OE"),
            Grupa("Grupa 2", "OE"),
            Grupa("Grupa 1", "RMA"),
            Grupa("Grupa 2", "RMA"))
        }

        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
            return grupe.filter { grupa -> grupa.nazivPredmeta.equals(nazivPredmeta) }.toList()
        }
    }
}