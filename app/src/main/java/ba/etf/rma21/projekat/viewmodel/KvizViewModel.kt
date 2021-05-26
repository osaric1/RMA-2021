package ba.etf.rma21.projekat.viewmodel

import android.util.Log
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import java.util.*

class KvizViewModel() {
    /*
    fun getMyKvizes(): List<Kviz> {
        return KvizRepository.getMyKvizes()
    }

     */

    suspend fun getAll(): List<Kviz> {
        return KvizRepository.getAll()!!
    }

    suspend fun getById(id: Int):Kviz?{
        return KvizRepository.getById(id)
    }

    suspend fun getUpisani():List<Kviz> {
        return KvizRepository.getUpisani()
    }
/*
    fun getDone(): List<Kviz> {
        return KvizRepository.getDone()
    }

    fun getFuture(): List<Kviz> {
        return KvizRepository.getFuture()
    }

    fun getNotTaken(): List<Kviz> {
        return KvizRepository.getNotTaken()
    }
    fun addGroup(grupa: Grupa){
        KvizRepository.addGroup(grupa)
    }
    fun changeStatus(bodovi: Float, nazivKviza: String, nazivGrupe: String){
        KvizRepository.changeStatus(bodovi, nazivKviza, nazivGrupe)
    }
*/
}