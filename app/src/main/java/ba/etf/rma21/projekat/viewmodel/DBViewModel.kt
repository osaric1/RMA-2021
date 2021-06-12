package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.repositories.DBRepository

class DBViewModel {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateNow(): Boolean{
        return DBRepository.updateNow()
    }

    fun setContext(_context: Context){
        return DBRepository.setContext(_context)
    }
}