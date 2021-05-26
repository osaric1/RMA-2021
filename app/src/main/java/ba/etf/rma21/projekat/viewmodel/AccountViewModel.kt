package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.repositories.AccountRepository

class AccountViewModel {
    suspend fun postaviHash(acHash:String):Boolean{
        return AccountRepository.postaviHash(acHash)
    }

    fun getHash():String{
        return AccountRepository.getHash()
    }
}