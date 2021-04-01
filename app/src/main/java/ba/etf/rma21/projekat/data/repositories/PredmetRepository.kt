package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Predmet

class PredmetRepository {
    companion object {
        fun getUpisani(): List<Predmet> {
            return listOf(Predmet("DM", 2))
        }

        fun getAll(): List<Predmet> {
            return listOf(Predmet("IM",1),
                Predmet("OE", 1),Predmet("RMA", 2),
                Predmet("DM", 2))
        }
        // TODO: Implementirati i ostale potrebne metode
    }

}