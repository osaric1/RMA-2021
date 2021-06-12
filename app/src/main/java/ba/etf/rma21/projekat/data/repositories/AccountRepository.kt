package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import androidx.room.ColumnInfo
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AccountRepository {
    companion object{
        private lateinit var context: Context

        fun setContext(_context:Context){
            context=_context
        }
        var acHash: String = "e6aba651-b4ba-4b26-85b8-c15fe9eededd"

        suspend fun postaviHash(acHash:String):Boolean{
            return withContext(Dispatchers.IO){
                try {
                    val prethodni = AccountRepository.acHash
                    AccountRepository.acHash = acHash
                    var db = AppDatabase.getInstance(context)

                    val response = ApiAdapter.retrofit.getAccount(acHash)

                    if(response.body() is Account) {
                        db.accountDao().changeUser(response.body()!!.id, response.body()!!.student, response.body()!!.acHash, prethodni)
                        updateData()
                        return@withContext true
                    }
                    return@withContext false
                }
                catch(error:Exception){
                    return@withContext false
                }
            }
        }

        fun getHash():String{
            return acHash
        }

        suspend fun getAll() : List<Account>{
            return withContext(Dispatchers.IO){
                try {
                    var db = AppDatabase.getInstance(context)
                    val accounts = db.accountDao().getAll()
                    return@withContext accounts
                }
                catch(error:Exception){
                    return@withContext listOf<Account>()
                }
            }
        }

        suspend fun getUser(): Account?{
            return withContext(Dispatchers.IO){
                val response = ApiAdapter.retrofit.getAccount(AccountRepository.getHash())

                when(response.body()){
                    is Account -> return@withContext response.body()
                    else -> return@withContext null
                }
            }
        }

        suspend fun updateData(){
            return withContext(Dispatchers.IO){
                try {
                    val db = AppDatabase.getInstance(context)
                    izbrisiIzBaze()
                    val noviKvizovi = KvizRepository.getUpisani()
                    val noveGrupe = PredmetIGrupaRepository.getUpisaneGrupe()
                    val noviPredmeti = PredmetIGrupaRepository.getPredmeti().filter{predmet ->  noveGrupe!!.map { grupa -> grupa.predmetId  }.contains(predmet.id) } //dobavi sve upisane predmete za korisnika
                    val noviPokusaji = TakeKvizRepository.getPocetiKvizovi()

                    var novaPitanja: MutableList<Pitanje> = mutableListOf()
                    for(kviz in noviKvizovi){
                        val pitanja = PitanjeKvizRepository.getPitanja(kviz.id)
                        pitanja.forEach { pitanje -> pitanje.KvizId = kviz.id }
                        for(novoPitanje in pitanja){
                            if(db.pitanjeDao().checkDuplicate(novoPitanje.id) == null)
                                db.pitanjeDao().insert(novoPitanje)
                            else{
                                novoPitanje.id = db.pitanjeDao().najveciId() + 1
                                println(novoPitanje.id)
                                db.pitanjeDao().insert(novoPitanje)
                            }
                        }
                        novaPitanja.addAll(pitanja)
                    }

                    db.kvizDao().insertAll(noviKvizovi)

                    if(noviPokusaji != null)
                    db.kvizTakenDao().insertAll(noviPokusaji)

                    for(noviPredmet in noviPredmeti){
                        db.predmetDao().insert(noviPredmet)
                    }

                    for(novaGrupa in noveGrupe!!){
                        db.grupaDao().insert(novaGrupa)
                    }

                    db.pitanjeDao().insertAll(novaPitanja)

                }
                catch(error: Exception){
                    println(error.printStackTrace())
                }
            }
        }

        suspend fun izbrisiIzBaze(){
            return withContext(Dispatchers.IO) {
                try {
                    val db = AppDatabase.getInstance(context)
                    db.kvizDao().deleteAll()
                    db.kvizTakenDao().deleteAll()
                    db.predmetDao().deleteAll()
                    db.pitanjeDao().deleteAll()
                    db.grupaDao().deleteAll()
                } catch (error: Exception) {
                    println("Desila se greska tokom brisanja")
                }
            }
        }
    }
}