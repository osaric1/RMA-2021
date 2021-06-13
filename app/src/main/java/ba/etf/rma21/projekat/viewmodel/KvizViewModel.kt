package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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

    fun setContext(context: Context){
        KvizRepository.setContext(context)
    }

    suspend fun getUpisaneIzBaze() : List<Kviz>{
        return KvizRepository.getUpisaneIzBaze()
    }

    suspend fun getAll(): List<Kviz> {
        return KvizRepository.getAll()!!
    }

    suspend fun getById(id: Int): Kviz? {
        return KvizRepository.getById(id)
    }

    suspend fun getUpisani(): List<Kviz> {
        return KvizRepository.getUpisani()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getDone(): List<Kviz> {
        return KvizRepository.getDone()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getNotTaken(): List<Kviz> {
        return KvizRepository.getNotTaken()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getFuture(): List<Kviz> {
        return KvizRepository.getFuture()
    }
}
