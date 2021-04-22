package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.PitanjeKviz

fun allPitanjaKviz(): List<PitanjeKviz>{
    return listOf(PitanjeKviz("RMA-p1", "RMA Kviz 2", "RMA",1.0F),
                  PitanjeKviz("RMA-p2", "RMA Kviz 2", "RMA",1.0F),
                  PitanjeKviz("RMA-p3", "RMA Kviz 2", "RMA", 1.0F),
                  PitanjeKviz("RPR-p1", "RPR Kviz 1", "RPR",1.0F),
                  PitanjeKviz("RPR-p2", "RPR Kviz 1", "RPR",1.0F),
                  PitanjeKviz("RPR-p3", "RPR Kviz 1", "RPR",1.0F),
                  PitanjeKviz("IM-p1", "IM Kviz 2", "IM",1.0F),
                  PitanjeKviz("IM-p2", "IM Kviz 2", "IM",1.0F),
                  PitanjeKviz("IM-p3", "IM Kviz 2", "IM",1.0F),
                  PitanjeKviz("IM-p4", "IM Kviz 2", "IM", 1.0F),
                  PitanjeKviz("TP-p1", "TP Kviz 2", "TP", 1.0F),
                  PitanjeKviz("TP-p2", "TP Kviz 2", "TP", 1.0F),
                  PitanjeKviz("TP-p3", "TP Kviz 2", "TP", 1.0F),
                  PitanjeKviz("TP-p4", "TP Kviz 2", "TP", 1.0F),
                  PitanjeKviz("DM-p1", "DM Kviz 2", "DM", 1.0F),
                  PitanjeKviz("DM-p2", "DM Kviz 2", "DM", 1.0F),
                  PitanjeKviz("DM-p3", "DM Kviz 2", "DM", 1.0F),
                  PitanjeKviz("DM-p4", "DM Kviz 1", "DM", 1.0F),
                  PitanjeKviz("DM-p5", "DM Kviz 1", "DM", 1.0F),
                  PitanjeKviz("DM-p6", "DM Kviz 1", "DM", 1.0F),
                  PitanjeKviz("TP-p1", "TP Kviz 1", "TP", 1.0F),
                    PitanjeKviz("TP-p2", "TP Kviz 1", "TP", 1.0F),
                    PitanjeKviz("TP-p3", "TP Kviz 1", "TP", 1.0F),
                    PitanjeKviz("TP-p4", "TP Kviz 1", "TP", 1.0F)
    )
}

fun obracunajBodoveZaKviz(nazivKviza: String): Float{
    var lista = allPitanjaKviz().filter{ pitanjeKviz -> pitanjeKviz.kviz == nazivKviza  }.toList()
    var total = 0.0F
    for(pitanjeKviz in lista){
        total += pitanjeKviz.bodovi
    }
    return total
}