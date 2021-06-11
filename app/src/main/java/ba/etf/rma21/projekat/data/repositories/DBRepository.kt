package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Change
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class DBRepository {
    companion object {
        private lateinit var context: Context

        fun setContext(_context: Context){
            context=_context
        }


        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun updateNow(): Boolean {
            return withContext(Dispatchers.IO) {
                try {

                    val db = AppDatabase.getInstance(context)
                    val datum = db.accountDao().getLastUpdate(AccountRepository.getHash())

                    if(datum == null) return@withContext true

                    val response = ApiAdapter.retrofit.updateNow(AccountRepository.getHash(), datum) //mozda bude problema
                    val responseBody = response.body()
                    when(responseBody){
                        is Change -> {
                            return@withContext responseBody.changed
                        }
                        else -> return@withContext false
                    }
                }
                catch(error:Exception){
                   return@withContext false //doslo je do greske, nece biti promjena
                }
            }

        }
    }
}