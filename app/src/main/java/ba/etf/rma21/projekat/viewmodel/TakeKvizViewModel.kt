package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository

class TakeKvizViewModel {
    suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
        return TakeKvizRepository.zapocniKviz(idKviza)
    }

    suspend fun getPocetiKvizovi(): List<KvizTaken>? {
        return TakeKvizRepository.getPocetiKvizovi()
    }

    suspend fun getPocetiKvizoviIzBaze(): List<KvizTaken> {
        return TakeKvizRepository.getPocetiKvizoviIzBaze()
    }

    fun setContext(context: Context){
        TakeKvizRepository.setContext(context)
    }
}