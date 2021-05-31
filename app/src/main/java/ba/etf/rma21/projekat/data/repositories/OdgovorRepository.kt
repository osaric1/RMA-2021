package ba.etf.rma21.projekat.data.repositories

import android.util.Log
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.OdgovorKviz
import ba.etf.rma21.projekat.viewmodel.OdgovorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OdgovorRepository {
    companion object {
        suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor> {
            return withContext(Dispatchers.IO) {
                val pokusajKviza = TakeKvizRepository.getPocetiKvizovi()!!.find{ kvizTaken -> kvizTaken.KvizId == idKviza  }

                if(pokusajKviza != null) {
                    var response =
                        ApiAdapter.retrofit.getOdgovoriKviz(
                            AccountRepository.getHash(),
                            pokusajKviza.id
                        )
                    val responseBody = response.body()
                    when (responseBody) {
                        is List<Odgovor> -> {
                            return@withContext responseBody
                        }
                        else -> return@withContext listOf<Odgovor>()
                    }
                }
                else return@withContext listOf<Odgovor>()
            }!!
        }

        suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int {
            return withContext(Dispatchers.IO) {
                val pokusajKviza = TakeKvizRepository.getPocetiKvizovi()!!.find{ kvizTaken -> kvizTaken.id == idKvizTaken  }
                var bodovi: Float = pokusajKviza!!.osvojeniBodovi

                val pitanje = PitanjeKvizRepository.getPitanja(pokusajKviza.KvizId).find { pitanje -> pitanje.id == idPitanje  }


                if(pitanje!!.tacan == odgovor)
                    bodovi += 50.0f

                val response = ApiAdapter.retrofit.postaviOdgovorKviz(
                    AccountRepository.getHash(),
                    idKvizTaken,
                    OdgovorKviz(odgovor, idPitanje, bodovi.toInt())
                )
                val responseBody = response.body()
                when (responseBody) {
                    is Odgovor -> return@withContext bodovi.toInt()
                    else -> return@withContext -1
                }
            }
        }

    }
}