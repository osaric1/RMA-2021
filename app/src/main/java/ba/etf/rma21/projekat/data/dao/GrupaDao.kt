package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.KvizTaken

@Dao
interface GrupaDao {
    @Query("DELETE FROM grupa")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(grupe: List<Grupa>)
}