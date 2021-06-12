package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PitanjeKvizRepository {
    companion object {
        private lateinit var context: Context
        fun setContext(_context:Context){
            context=_context
        }
        /*
        fun getPitanja(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
            var nazivi: List<String> = allPitanjaKviz().filter { pitanjeKviz -> pitanjeKviz.kviz == nazivKviza && pitanjeKviz.predmet == nazivPredmeta }.map { pitanjeKviz -> pitanjeKviz.toString() }.toList()
            return allPitanja().filter { pitanje -> nazivi.contains(pitanje.naziv) }.toList()
        }

        fun dajBodove(nazivKviza: String): Float{
            return obracunajBodoveZaKviz(nazivKviza)
        }

         */

        suspend fun getPitanja(idKviza:Int):List<Pitanje>{
            return withContext(Dispatchers.IO){
                val response = ApiAdapter.retrofit.getPitanja(idKviza)
                val responseBody = response.body()

                when(responseBody){
                    is List<Pitanje> -> return@withContext responseBody
                    else -> return@withContext listOf<Pitanje>()
                }
            }!!
        }

//        suspend fun getPitanjaIzBaze(idKviza: Int): List<Pitanje>{
//            return withContext(Dispatchers.IO){
//                try {
//                    val db = AppDatabase.getInstance(context)
//
//                }
//                catch(error: Exception){
//                    return@withContext listOf<Pitanje>()
//                }
//            }
//        }

    }


}