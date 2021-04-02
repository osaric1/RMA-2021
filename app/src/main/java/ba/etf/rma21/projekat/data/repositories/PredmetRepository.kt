package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Predmet

class PredmetRepository {
    companion object {
        private var predmeti : List<Predmet> = listOf(Predmet("IM",1),
            Predmet("OE", 1),Predmet("RMA", 2),
            Predmet("DM", 2), Predmet("OOAD", 2))

        private var upisaniPredmeti: MutableList<Predmet> = mutableListOf()
        fun getUpisani(): List<Predmet> {
            return upisaniPredmeti
        }

        fun getAll(): List<Predmet> {
            return predmeti
        }

        fun getPredmetsByGodinama(godina: Int): List<Predmet> {
            return predmeti.filter { predmet -> predmet.godina == godina }.toList()
        }

        fun addPredmet(predmet: Predmet){
            upisaniPredmeti.add(predmet)
        }

        fun getSlobodni(godina: Int): List<Predmet>{
            return predmeti.filter { predmet -> predmet.godina == godina && !upisaniPredmeti.contains(predmet) }.toList()
        }
        fun getSlobodniAll(): List<Predmet>{
            return predmeti.filter { predmet ->  !upisaniPredmeti.contains(predmet) }.toList()
        }

    }

}