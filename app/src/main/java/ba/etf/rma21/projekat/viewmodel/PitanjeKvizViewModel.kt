package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository

class PitanjeKvizViewModel {
    suspend fun getPitanja(idKviza:Int):List<Pitanje>{
        return PitanjeKvizRepository.getPitanja(idKviza)
    }

    suspend fun getPitanjaIzBaze(idKviza: Int): List<Pitanje>{
        return PitanjeKvizRepository.getPitanjaIzBaze(idKviza)
    }

    fun setContext(context: Context){
        PitanjeKvizRepository.setContext(context)
    }
    /*
    fun dajBodove(nazivKviza: String): Float{
        return PitanjeKvizRepository.dajBodove(nazivKviza)
    }

     */
}