package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.models.Change
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

                    var list = db.accountDao().getAll()
                    if(list.isEmpty()){
                        val account = AccountRepository.getUser()
                        try {
                            db.accountDao().insertAccount(account!!)
                        }
                        catch(error: Exception){
                            println(error)
                        }

                    }
                    val datum = db.accountDao().getLastUpdate(AccountRepository.getHash())
                    val response = ApiAdapter.retrofit.updateNow(AccountRepository.getHash(), datum.toString()) //mozda bude problema
                    val responseBody = response.body()
                    when(responseBody){
                        is Change -> {
                            if(responseBody.changed || datum == null){
                                db.accountDao().setLastUpdate(AccountRepository.getHash(), LocalDateTime.now().withNano(0).format(DateTimeFormatter.ISO_DATE_TIME))
                            }
                            return@withContext responseBody.changed
                        }
                        else -> return@withContext false
                    }
                }
                catch(error:Exception){
                   return@withContext false
                }
            }

        }
    }
}