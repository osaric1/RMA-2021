package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Predmet

class PredmetRepository {
    companion object {
        private var predmeti : List<Predmet> = listOf(Predmet("IM",1),
            Predmet("OE", 1),Predmet("RMA", 2),
            Predmet("DM", 2))

        fun getUpisani(): List<Predmet> {
            return listOf(Predmet("DM", 2))
        }

        fun getAll(): List<Predmet> {
            return predmeti
        }

        fun getPredmetsByGodinama(godina: Int): List<Predmet> {
            return predmeti.filter { predmet -> predmet.godina == godina }.toList()
        }
    }

}