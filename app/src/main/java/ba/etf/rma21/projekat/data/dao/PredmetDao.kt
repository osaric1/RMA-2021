package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface PredmetDao {
    @Query("DELETE FROM predmet")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(predmeti: List<Predmet>)
}