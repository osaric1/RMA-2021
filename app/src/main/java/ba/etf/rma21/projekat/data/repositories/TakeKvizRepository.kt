package ba.etf.rma21.projekat.data.repositories

import android.util.Log
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.POST
import retrofit2.http.Path

class TakeKvizRepository {
    companion object {

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

    }

}