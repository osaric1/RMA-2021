package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Predmet

fun allPredmeti(): List<Predmet>{
    return listOf(Predmet("IM",1),
            Predmet("OE", 1), Predmet("RMA", 2),
            Predmet("DM", 2), Predmet("OOAD", 2),
            Predmet("TP", 2), Predmet("RPR", 2),
            Predmet("WT",3), Predmet("RG", 3),
            Predmet("OIS", 3))
}

