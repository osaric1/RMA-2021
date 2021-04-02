package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa

class GrupaRepository {
    companion object {
        private var grupe: List<Grupa>
        init {
            grupe = listOf(Grupa("IM_Grupa_1", "IM"),
            Grupa("IM_Grupa_2", "IM"),
            Grupa("OE_Grupa_1", "OE"),
            Grupa("OE_Grupa_2", "OE"),
            Grupa("RMA_Grupa_1", "RMA"),
            Grupa("RMA_Grupa_2", "RMA"),
            Grupa("DM_Grupa_1", "DM"))
        }

        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
            return grupe.filter { grupa -> grupa.nazivPredmeta.equals(nazivPredmeta) }.toList()
        }

        fun getAll(): List<Grupa>{
            return grupe
        }
    }
}