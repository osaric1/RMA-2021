package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.repositories.AccountRepository

class AccountViewModel {
    suspend fun postaviHash(acHash:String):Boolean{
        return AccountRepository.postaviHash(acHash)
    }

    fun getHash():String{
        return AccountRepository.getHash()
    }

    suspend fun updateData(){
        return AccountRepository.updateData()
    }

    suspend fun izbrisiIzBaze(){
        return AccountRepository.izbrisiIzBaze()
    }

    suspend fun getAll() : List<Account>{
        return AccountRepository.getAll()
    }

    fun setContext(_context: Context){
        AccountRepository.setContext(_context)
    }

    suspend fun getUser(): Account?{
        return AccountRepository.getUser()
    }
}