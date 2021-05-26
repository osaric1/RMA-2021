package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository

class OdgovorViewModel {
    suspend fun getOdgovoriKviz(idKviza:Int):List<Odgovor>{
        return OdgovorRepository.getOdgovoriKviz(idKviza)
    }

    suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int {
        return OdgovorRepository.postaviOdgovorKviz(idKvizTaken,idPitanje,odgovor)
    }
}