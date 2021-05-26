package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.OdgovorKviz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OdgovorRepository {
    companion object {
        suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor> {
            return withContext(Dispatchers.IO) {
                var response =
                    ApiAdapter.retrofit.getOdgovoriKviz(AccountRepository.getHash(), idKviza)
                val responseBody = response.body()
                when (responseBody) {
                    is List<Odgovor> -> {
                        return@withContext responseBody
                    }
                    else -> return@withContext listOf<Odgovor>()
                }
            }!!
        }

        suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.postaviOdgovorKviz(
                    AccountRepository.getHash(),
                    idKvizTaken,
                    OdgovorKviz(odgovor, idPitanje, 5.3F)
                )
                //TODO bodovi
                val responseBody = response.body()
                when (responseBody) {
                    is Odgovor -> return@withContext 4
                    else -> return@withContext -1
                }
            }
        }

    }
}