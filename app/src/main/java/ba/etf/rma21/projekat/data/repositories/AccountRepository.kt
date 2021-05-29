package ba.etf.rma21.projekat.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountRepository {
    companion object{
        var acHash: String = "e6aba651-b4ba-4b26-85b8-c15fe9eededd"
        suspend fun postaviHash(acHash:String):Boolean{
            return withContext(Dispatchers.IO){
                val prethodni = AccountRepository.acHash
                AccountRepository.acHash = acHash
                return@withContext AccountRepository.acHash != prethodni
            }
        }

        fun getHash():String{
            return acHash
        }
    }
}