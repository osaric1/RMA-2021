package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.POST
import retrofit2.http.Path
import java.lang.Appendable
import java.lang.Exception

class TakeKvizRepository {
    companion object {
        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
        }

        suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
            return withContext(Dispatchers.IO){
                var response = ApiAdapter.retrofit.zapocniKviz(AccountRepository.getHash(), idKviza)
                val responseBody = response.body()
                when(responseBody){
                    is KvizTaken -> return@withContext responseBody
                    else -> return@withContext null
                }
            }
        }

        suspend fun getPocetiKvizovi(): List<KvizTaken>? {
            return withContext(Dispatchers.IO){
                var response = ApiAdapter.retrofit.getPocetiKvizovi(AccountRepository.getHash())
                val responseBody = response.body()
                when(responseBody){
                    is List<KvizTaken> ->  {
                      if(responseBody.isEmpty()){
                          return@withContext null
                      }
                      else return@withContext responseBody
                    }
                    else -> return@withContext null
                }
            }
        }

        suspend fun getPocetiKvizoviIzBaze(): List<KvizTaken> {
            return withContext(Dispatchers.IO) {
                try {
                    val db = AppDatabase.getInstance(context)

                    val kvizTaken = db.kvizTakenDao().getAll()

                    return@withContext kvizTaken
                }
                catch(error: Exception){
                    return@withContext listOf<KvizTaken>()
                }
            }
        }

    }

}