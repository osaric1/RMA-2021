package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.GrupaKviz

@Dao
interface GrupaKvizDao {

    @Insert
    suspend fun insert(grupaKviz: GrupaKviz)

    @Query("SELECT MAX(id) FROM grupakviz")
    suspend fun najveciId(): Int?

    @Query("SELECT * FROM grupakviz WHERE kvizId==:kvizId")
    suspend fun getGrupeZaKvizBaza(kvizId: Int): List<GrupaKviz>

    @Query("DELETE FROM grupakviz")
    suspend fun deleteAll()
}