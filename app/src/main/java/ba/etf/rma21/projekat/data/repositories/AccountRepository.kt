package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AccountRepository {
    companion object{
        private lateinit var context: Context

        fun setContext(_context:Context){
            context=_context
        }
        var acHash: String = "e6aba651-b4ba-4b26-85b8-c15fe9eededd"

        suspend fun postaviHash(acHash:String):Boolean{
            return withContext(Dispatchers.IO){
                try {
                    val prethodni = AccountRepository.acHash
                    AccountRepository.acHash = acHash
                    var db = AppDatabase.getInstance(context)
                    return@withContext true
                }
                catch(error:Exception){
                    return@withContext false
                }
            }
        }

        fun getHash():String{
            return acHash
        }
    }
}