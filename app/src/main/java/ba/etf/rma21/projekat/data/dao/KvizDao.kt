package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Kviz

@Dao
interface KvizDao {

    @Query("SELECT * FROM kviz")
    suspend fun getUpisaneIzBaze(): List<Kviz>
}