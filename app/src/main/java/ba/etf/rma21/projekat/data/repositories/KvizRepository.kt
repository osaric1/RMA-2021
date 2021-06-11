package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.*
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class KvizRepository() {
    companion object {
        private lateinit var context: Context
        fun setContext(_context: Context) {
            context = _context
        }

        @RequiresApi(Build.VERSION_CODES.O)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        //dobavi sve sa web servisa
        suspend fun getAll(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getAll()
                val responseBody = response.body()
                when (responseBody) {
                    is List<Kviz> -> return@withContext responseBody
                    else -> return@withContext listOf<Kviz>()
                }
            }!!
        }

        suspend fun getById(id: Int): Kviz? {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getById(id)
                val responseBody = response.body()
                when (responseBody) {
                    is Kviz -> return@withContext responseBody
                    else -> return@withContext null
                }
            }
        }

        //dobavi sa web servisa upisane kvizove
        suspend fun getUpisani(): List<Kviz> {
            var upisaneGrupe = listOf<Grupa>()
            upisaneGrupe = PredmetIGrupaRepository.getUpisaneGrupe()!!
            return withContext(Dispatchers.IO) {
                var listaKvizova: MutableList<Kviz> = mutableListOf()
                for (grupa in upisaneGrupe) {
                    val response = ApiAdapter.retrofit.getUpisani(grupa.id)
                    listaKvizova.addAll(response.body()!!)
                }
                return@withContext listaKvizova
            }
        }

        suspend fun getUpisaneIzBaze(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                try {
                    val db = AppDatabase.getInstance(context)
                    val kvizovi = db.kvizDao().getUpisaneIzBaze()

                    return@withContext kvizovi
                } catch (error: Exception) {
                    return@withContext listOf<Kviz>()
                }
            }
        }


        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getDone(): List<Kviz> {

            val zapocetiKvizovi = TakeKvizRepository.getPocetiKvizoviIzBaze()
            val myKvizovi = getUpisaneIzBaze()
            val rezultat: MutableList<Kviz> = mutableListOf()

            if (zapocetiKvizovi != null) {
                zapocetiKvizovi.filter { it.datumRada != null && LocalDateTime.parse(it.datumRada, formatter) <= LocalDateTime.now()}
                for (kviz in zapocetiKvizovi) {
                    for (mojKviz in myKvizovi) {
                        if (kviz.KvizId == mojKviz.id) rezultat.add(mojKviz)
                    }
                }
            }
            return rezultat
        }


        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getFuture(): List<Kviz> {
            return getUpisaneIzBaze().filter { kviz -> LocalDateTime.parse(kviz.datumPocetka, formatter) > LocalDateTime.now() }
                .toList()
        }


        fun toCalendar(date: Date): Calendar {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getNotTaken(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getPocetiKvizovi(AccountRepository.acHash)
                val responseBody = response.body()

                if (responseBody != null) {
                    val ids = responseBody.map { kvizTaken -> kvizTaken.KvizId }
                    return@withContext getUpisani().filter { kviz ->
                        kviz.datumKraj != null && !ids.contains(
                            kviz.id
                        ) && LocalDateTime.parse(kviz.datumKraj, formatter) < LocalDateTime.now()
                    }.toList()
                }
                return@withContext listOf<Kviz>()
            }
        }
    }

}

