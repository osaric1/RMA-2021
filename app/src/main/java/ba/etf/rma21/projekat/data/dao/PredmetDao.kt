package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface PredmetDao {
    @Query("DELETE FROM predmet")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(vararg predmet: Predmet)

    @Query("SELECT id FROM predmet WHERE id==:id")
    suspend fun checkDuplicate(id: Int): Int?
}