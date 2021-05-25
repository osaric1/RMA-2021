package ba.etf.rma21.projekat.data.repositories

import android.util.Log
import ba.etf.rma21.projekat.data.allPitanja
import ba.etf.rma21.projekat.data.allPitanjaKviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.obracunajBodoveZaKviz

class PitanjeKvizRepository {
    companion object {

        fun getPitanja(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
            var nazivi: List<String> = allPitanjaKviz().filter { pitanjeKviz -> pitanjeKviz.kviz == nazivKviza && pitanjeKviz.predmet == nazivPredmeta }.map { pitanjeKviz -> pitanjeKviz.toString() }.toList()
            return allPitanja().filter { pitanje -> nazivi.contains(pitanje.naziv) }.toList()
        }

        fun dajBodove(nazivKviza: String): Float{
            return obracunajBodoveZaKviz(nazivKviza)
        }

        fun getPitanja(idKviza:Int):List<Pitanje>{
            return listOf()
        }

    }


}