package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.allPredmeti
import ba.etf.rma21.projekat.data.models.Predmet

class PredmetRepository {
    companion object {

        private var upisaniPredmeti: MutableList<Predmet> = mutableListOf(Predmet("RMA", 2), Predmet("TP", 2), Predmet("DM",2))

        fun getUpisani(): List<Predmet> {
            return upisaniPredmeti
        }

        fun getAll(): List<Predmet> {
            return allPredmeti()
        }

        fun getPredmetsByGodinama(godina: Int): List<Predmet> {
            return getAll().filter { predmet -> predmet.godina == godina  }.toList()
        }

        fun addPredmet(predmet: Predmet){
            upisaniPredmeti.add(predmet)
        }

        fun getSlobodni(godina: Int): List<Predmet>{
            return allPredmeti().filter { predmet -> predmet.godina == godina && !upisaniPredmeti.contains(predmet) }.toList()
        }
        fun getSlobodniAll(): List<Predmet>{
            return allPredmeti().filter { predmet ->  !upisaniPredmeti.contains(predmet) }.toList()
        }

    }

}