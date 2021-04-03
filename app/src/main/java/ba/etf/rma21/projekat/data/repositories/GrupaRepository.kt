package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.allGrupe
import ba.etf.rma21.projekat.data.models.Grupa

class GrupaRepository {
    companion object {

        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
            return allGrupe().filter { grupa -> grupa.nazivPredmeta.equals(nazivPredmeta) }.toList()
        }

        fun getAll(): List<Grupa>{
            return allGrupe()
        }
    }
}