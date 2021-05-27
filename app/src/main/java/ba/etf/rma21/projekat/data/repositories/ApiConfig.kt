package ba.etf.rma21.projekat.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object ApiConfig {
    var baseURL: String = "https://rma21-etf.herokuapp.com/"

    suspend fun postaviBaseURL(baseUrl:String):Unit {
        return withContext(Dispatchers.IO){
            baseURL = baseUrl
        }
    }
}