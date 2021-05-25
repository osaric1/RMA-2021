package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.http.POST
import retrofit2.http.Path

class TakeKvizRepository {
    companion object {
        var pocetiKvizovi: MutableList<KvizTaken> = mutableListOf()
        fun zapocniKviz(idKviza: Int): KvizTaken {
            GlobalScope.launch(Dispatchers.IO) {

            }
        }
    }

    @POST("/student/{id}/kviztaken")
    fun beginKviz(@Path("id") acHash: String ){

    }

}