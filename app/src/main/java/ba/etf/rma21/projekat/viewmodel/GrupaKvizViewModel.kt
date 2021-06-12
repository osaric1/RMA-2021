package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.data.models.GrupaKviz
import ba.etf.rma21.projekat.data.repositories.GrupaKvizRepository

class GrupaKvizViewModel {
    fun setContext(context: Context){
        GrupaKvizRepository.setContext(context)
    }

    suspend fun getGrupeZaKvizBaza(kvizId: Int): List<GrupaKviz>{
        return GrupaKvizRepository.getGrupeZaKvizBaza(kvizId)
    }
}