package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Pitanje

@Dao
interface PitanjeDao {
    @Query("DELETE FROM pitanje")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(pitanja: List<Pitanje>)
}