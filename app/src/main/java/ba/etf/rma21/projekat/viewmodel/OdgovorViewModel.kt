package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository

class OdgovorViewModel {
    suspend fun getOdgovoriKviz(idKviza:Int):List<Odgovor>{
        return OdgovorRepository.getOdgovoriKviz(idKviza)
    }

    fun setContext(context: Context){
        return OdgovorRepository.setContext(context)
    }

    suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int {
        return OdgovorRepository.postaviOdgovorKviz(idKvizTaken,idPitanje,odgovor)
    }

    suspend fun predajOdgovore(idKviza: Int){
        OdgovorRepository.predajOdgovore(idKviza)
    }
    suspend fun getOdgovoreZaKvizIzBaze(idKviz: Int): List<Odgovor>{
        return OdgovorRepository.getOdgovoreZaKvizIzBaze(idKviz)
    }

}