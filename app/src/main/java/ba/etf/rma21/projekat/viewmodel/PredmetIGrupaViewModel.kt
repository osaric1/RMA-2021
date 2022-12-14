package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository

class PredmetIGrupaViewModel {
    suspend fun getPredmeti(): List<Predmet> {
        return PredmetIGrupaRepository.getPredmeti()
    }

    suspend fun getGrupe(): List<Grupa> {
        return PredmetIGrupaRepository.getGrupe()
    }

    suspend fun getGrupeIzBaze(): List<Grupa>{
        return PredmetIGrupaRepository.getGrupe()
    }

    suspend fun getGrupa(grupaId: Int): Grupa?{
        return PredmetIGrupaRepository.getGrupa(grupaId)
    }

    fun setContext(context: Context){
        PredmetIGrupaRepository.setContext(context)
    }

    suspend fun getGrupeZaPredmet(idPredmeta: Int): List<Grupa>? {
        return PredmetIGrupaRepository.getGrupeZaPredmet(idPredmeta)
    }

    suspend fun upisiUGrupu(idGrupa: Int): Boolean {
        return PredmetIGrupaRepository.upisiUGrupu(idGrupa)
    }

    suspend fun getUpisaneGrupe(): List<Grupa>? {
        return PredmetIGrupaRepository.getUpisaneGrupe()
    }

    suspend fun getGrupeZaKviz(idKviza: Int): List<Grupa>?{
        return PredmetIGrupaRepository.getGrupeZaKviz(idKviza)
    }

    suspend fun getPredmetById(predmetId: Int): Predmet?{
        return PredmetIGrupaRepository.getPredmetById(predmetId)
    }

    suspend fun getPredmetByIdIzBaze(predmetId: Int): Predmet?{
        return PredmetIGrupaRepository.getPredmetByIdIzBaze(predmetId)
    }
}