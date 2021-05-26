package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository

class PitanjeKvizViewModel {
    suspend fun getPitanja(idKviza:Int):List<Pitanje>{
        return PitanjeKvizRepository.getPitanja(idKviza)
    }
    /*
    fun dajBodove(nazivKviza: String): Float{
        return PitanjeKvizRepository.dajBodove(nazivKviza)
    }

     */
}