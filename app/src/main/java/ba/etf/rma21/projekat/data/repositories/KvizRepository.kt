package ba.etf.rma21.projekat.data.repositories

import android.util.Log
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.*
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class KvizRepository() {
    companion object {
        /*private var upisaneGrupe : MutableList<Grupa> = mutableListOf(Grupa("RMA Grupa 2", "RMA"), Grupa("TP Grupa 1", "TP"), Grupa("DM Grupa 1", "DM"),
        Grupa("RPR Grupa 1", "RPR"))

        fun getMyKvizes(): List<Kviz> {
            return allKvizes().filter { kviz -> upisaneGrupe.filter { grupa -> grupa.naziv == kviz.nazivGrupe }.any() }.toList()
        }

         */

        suspend fun getAll(): List<Kviz> {
            return withContext(Dispatchers.IO){
                val response = ApiAdapter.retrofit.getAll()
                val responseBody = response.body()
                when(responseBody){
                    is List<Kviz> -> return@withContext responseBody
                    else -> return@withContext listOf<Kviz>()
                }
            }!!
        }

        suspend fun getById(id: Int):Kviz?{
            return withContext(Dispatchers.IO){
                var response = ApiAdapter.retrofit.getById(id)
                val responseBody = response.body()
                when(responseBody){
                    is Kviz -> return@withContext responseBody
                    else -> return@withContext null
                }
            }
        }

        suspend fun getUpisani():List<Kviz> { // vraća listu svih kvizova za grupe na kojima je student upisan
            var upisaneGrupe = listOf<Grupa>()
            upisaneGrupe = PredmetIGrupaRepository.getUpisaneGrupe()!!
            return withContext(Dispatchers.IO){
                var listaKvizova:MutableList<Kviz> = mutableListOf()
                for(grupa in upisaneGrupe){
                    val response = ApiAdapter.retrofit.getUpisani(grupa.id)
                    listaKvizova.addAll(response.body()!!)
                }
                return@withContext listaKvizova
            }
        }


        suspend fun getDone(): List<Kviz> {
            return withContext(Dispatchers.IO){
                var response = ApiAdapter.retrofit.getPocetiKvizovi(AccountRepository.acHash)
                val responseBody = response.body()

                if(responseBody != null){
                    val ids = responseBody.map { kvizTaken -> kvizTaken.KvizId  }
                    return@withContext getUpisani().filter { kviz -> ids.contains(kviz.id) }.toList()
                }
                return@withContext listOf<Kviz>()
            }
        }


        suspend fun getFuture(): List<Kviz> {
            return getAll()!!.filter { kviz -> toCalendar(kviz.datumPocetka) > Calendar.getInstance() }.toList()
        }


        fun toCalendar(date: Date): Calendar{
            val cal = Calendar.getInstance()
            cal.time = date
            return cal
        }

        suspend fun getNotTaken(): List<Kviz> {
            return withContext(Dispatchers.IO){
                var response = ApiAdapter.retrofit.getPocetiKvizovi(AccountRepository.acHash)
                val responseBody = response.body()

                if(responseBody != null){
                    val ids = responseBody.map { kvizTaken -> kvizTaken.KvizId  }
                    return@withContext getUpisani().filter { kviz -> kviz.datumKraj != null && !ids.contains(kviz.id) && toCalendar(kviz.datumKraj) < Calendar.getInstance() }.toList()
                }
                return@withContext listOf<Kviz>()
            }
        }


    }
}