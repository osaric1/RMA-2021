package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa

class GrupaRepository {
    companion object {
        private var grupe: List<Grupa>
        init {
            grupe = listOf(Grupa("IM Grupa 1", "IM"),
            Grupa("IM Grupa 2", "IM"),
            Grupa("OE Grupa 1", "OE"),
            Grupa("OE Grupa 2", "OE"),
            Grupa("RMA Grupa 1", "RMA"),
            Grupa("RMA Grupa 2", "RMA"),
            Grupa("DM Grupa 1", "DM"),
            Grupa("OOAD Grupa 1", "OOAD"))
        }

        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
            return grupe.filter { grupa -> grupa.nazivPredmeta.equals(nazivPredmeta) }.toList()
        }

        fun getAll(): List<Grupa>{
            return grupe
        }
    }
}