package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.KvizTaken

@Dao
interface KvizTakenDao {
    @Query("SELECT * FROM kviztaken")
    suspend fun getAll(): List<KvizTaken>

    @Query("DELETE FROM kviztaken")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(kvizTaken: List<KvizTaken>)

}