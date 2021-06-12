package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Message
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PredmetIGrupaRepository {
    companion object {
        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }

        suspend fun getPredmeti(): List<Predmet> {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getPredmeti()
                val responseBody = response.body()

                return@withContext responseBody
            }!!
        }

        suspend fun getGrupe(): List<Grupa> {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getGrupe()
                val responseBody = response.body()

                return@withContext responseBody
            }!!
        }

        suspend fun getGrupeZaPredmet(idPredmeta: Int): List<Grupa> {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getGrupeZaPredmet(idPredmeta)
                val responseBody = response.body()

                when (responseBody) {
                    is List<Grupa> -> return@withContext responseBody
                    else -> return@withContext listOf<Grupa>()
                }
            }!!
        }

        suspend fun upisiUGrupu(idGrupa: Int): Boolean {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.upisiUGrupu(idGrupa, AccountRepository.getHash())
                val responseBody = response.body()


                when (responseBody) {
                    is Message -> {
                        if(responseBody.message.contains("je dodan u grupu"))
                        return@withContext true
                    }
                }
                return@withContext false
            }
        }

        suspend fun getUpisaneGrupe(): List<Grupa>? {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getUpisaneGrupe(AccountRepository.getHash())
                val responseBody = response.body()

                when (responseBody) {
                    is List<Grupa> -> return@withContext responseBody
                    else -> return@withContext null
                }
            }
        }

        suspend fun getGrupeZaKviz(idKviza: Int): List<Grupa>?{
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getGrupeZaKviz(idKviza)
                val responseBody = response.body()

                when (responseBody) {
                    is List<Grupa> -> return@withContext responseBody
                    else -> return@withContext null
                }
            }
        }

        suspend fun getPredmetById(predmetId: Int): Predmet?{
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getPredmetById(predmetId)
                val responseBody = response.body()

                when (responseBody) {
                    is Predmet -> return@withContext responseBody
                    else -> return@withContext null
                }
            }
        }

        suspend fun getGrupeIzBaze(): List<Grupa>{
            return withContext(Dispatchers.IO){
                try {
                    val db = AppDatabase.getInstance(context)
                    val grupe = db.grupaDao().getGrupeIzBaze()
                    return@withContext grupe
                }
                catch(error: Exception){
                    return@withContext listOf<Grupa>()
                }
            }
        }
    }

}