package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PredmetIGrupaRepository {
    companion object {
        suspend fun getPredmeti(): List<Predmet> {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getPredmeti()
                val responseBody = response.body()

                return@withContext responseBody
            }!!
        }

        /*
        fun getPredmetsByGodinama(godina: Int): List<Predmet> {
            return PredmetRepository.getAll().filter { predmet -> predmet.godina == godina  }.toList()
        }

         */

        suspend fun getGrupe(): List<Grupa> {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getGrupe()
                val responseBody = response.body()

                return@withContext responseBody
            }!!
        }

        suspend fun getGrupeZaPredmet(idPredmeta: Int): List<Grupa>? {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getGrupeZaPredmet(idPredmeta)
                val responseBody = response.body()

                when (responseBody) {
                    is List<Grupa> -> return@withContext responseBody
                    else -> return@withContext null
                }
            }
        }

        suspend fun upisiUGrupu(idGrupa: Int): Boolean {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.upisiUGrupu(idGrupa, AccountRepository.getHash())
                val responseBody = response.body()

                return@withContext responseBody.equals("upisan")
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
    }

}