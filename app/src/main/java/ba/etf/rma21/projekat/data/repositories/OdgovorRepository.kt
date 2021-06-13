package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.OdgovorKviz
import ba.etf.rma21.projekat.viewmodel.OdgovorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class OdgovorRepository {
    companion object {
        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
        }

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
                try {
                    var pokusajKviza = TakeKvizRepository.getPocetiKvizoviIzBaze().find{ kvizTaken -> kvizTaken.id == idKvizTaken  }

                    if(pokusajKviza == null){
                        pokusajKviza = TakeKvizRepository.getPocetiKvizovi()!!.find{ kvizTaken -> kvizTaken.id == idKvizTaken  }
                    }
                    var bodovi: Float = pokusajKviza!!.osvojeniBodovi

                    var pitanje = PitanjeKvizRepository.getPitanjaIzBaze(pokusajKviza.KvizId).find { pitanje1 -> pitanje1.id == idPitanje  }

                    if(pitanje == null){
                        pitanje = PitanjeKvizRepository.getPitanja(pokusajKviza.KvizId).find { pitanje1 -> pitanje1.id == idPitanje  }
                    }

                    val db = AppDatabase.getInstance(context)

                    if(db.odgovorDao().checkDuplicate(odgovor, idPitanje, pokusajKviza.KvizId) == null) {
                        if(pitanje!!.tacan == odgovor)
                            bodovi += 50.0f

                        db.odgovorDao().insert(
                            Odgovor(
                                (if (db.odgovorDao().najveciId() == null) 0 else db.odgovorDao()
                                    .najveciId())!! + 1, odgovor, idPitanje, pokusajKviza.KvizId
                            )
                        )
                        db.kvizTakenDao().updateBodovi(bodovi, idKvizTaken)
                    }
                    return@withContext bodovi.toInt()
                }
                catch(error: Exception){
                    return@withContext -1
                }
            }
        }

        suspend fun getOdgovoreZaKvizIzBaze(idKviz: Int): List<Odgovor>{
            return withContext(Dispatchers.IO) {
                try {
                    val db = AppDatabase.getInstance(context)
                    val odgovori = db.odgovorDao().getOdgovoreZaKvizIzBaze(idKviz)
                    return@withContext odgovori
                }
                catch(error: Exception){
                    return@withContext listOf<Odgovor>()
                }
            }
        }

        suspend fun predajOdgovore(idKviza: Int){
            return withContext(Dispatchers.IO) {
                try {
                    val db = AppDatabase.getInstance(context)
                    val odgovori = db.odgovorDao().getOdgovoreZaKvizIzBaze(idKviza)
                    val pokusajKviza = db.kvizTakenDao().getAll().first { kvizTaken ->  kvizTaken.KvizId == idKviza }
                    var brojac = 0
                    for(odg in odgovori){
                        val response = ApiAdapter.retrofit.postaviOdgovorKviz(AccountRepository.getHash(), pokusajKviza.id, OdgovorKviz(odg.odgovoreno, odg.PitanjeId, pokusajKviza.osvojeniBodovi.toInt())
                        )
                        val responseBody = response.body()
                        when (responseBody) {
                            is Odgovor -> brojac++
                        }
                    }

                    if(odgovori.size -1 == brojac){
                        db.kvizDao().updatePredan(true, idKviza)
                    }
                }
                catch(error: Exception){
                    println(error.printStackTrace())
                }
            }
        }

    }
}