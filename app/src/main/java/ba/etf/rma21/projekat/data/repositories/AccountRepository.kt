package ba.etf.rma21.projekat.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountRepository {
    companion object{
        var acHash: String = ""
        suspend fun postaviHash(acHash:String):Boolean{
            return withContext(Dispatchers.IO){
                AccountRepository.acHash = acHash
                return@withContext AccountRepository.acHash != ""
            }
        }

        fun getHash():String{
            return acHash
        }
    }
}