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
         */
        fun getMyKvizes(): List<Kviz> {
            return allKvizes().filter { kviz -> upisaneGrupe.filter { grupa -> grupa.naziv == kviz.nazivGrupe }.any() }.toList()
        }

        suspend fun getAll(): List<Kviz>? {
            return withContext(Dispatchers.IO){
                var response = ApiAdapter.retrofit.getAll()
                val responseBody = response.body()
                when(responseBody){
                    is List<Kviz> -> return@withContext responseBody
                    else -> return@withContext null
                }
            }
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

        fun getDone(): List<Kviz> {
            return getMyKvizes().filter { kviz -> toCalendar(kviz.datumRada).get(Calendar.YEAR) != 1970 }.toList()
        }

        fun getFuture(): List<Kviz> {
            return getMyKvizes().filter { kviz -> toCalendar(kviz.datumPocetka) > Calendar.getInstance() }.toList()
        }

        fun getNotTaken(): List<Kviz> {
            return getMyKvizes().filter { kviz -> toCalendar(kviz.datumKraj) < Calendar.getInstance() &&  toCalendar(kviz.datumRada).get(Calendar.YEAR) == 1970 }.toList()
        }

        fun addGroup(grupa: Grupa){
            upisaneGrupe.add(grupa)
        }

        fun toCalendar(date: Date): Calendar{
            val cal = Calendar.getInstance()
            cal.time = date
            return cal
        }

        fun changeStatus(bodovi: Float, nazivKviza: String, nazivGrupe: String){
            kvizStatus(bodovi, nazivKviza, nazivGrupe)
        }

    }
}