package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.KvizTaken

@Dao
interface KvizTakenDao {
    @Query("SELECT * FROM kviztaken")
    suspend fun getAll(): List<KvizTaken>
}