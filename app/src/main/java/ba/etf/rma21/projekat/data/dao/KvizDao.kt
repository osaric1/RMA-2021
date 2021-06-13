package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Kviz

@Dao
interface KvizDao {

    @Query("SELECT * FROM kviz")
    suspend fun getUpisaneIzBaze(): List<Kviz>

    @Query("DELETE FROM kviz")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(kviz: List<Kviz>)

    @Query("SELECT id FROM kviz WHERE id==:id")
    suspend fun checkDuplicate(id: Int): Int?

    @Insert
    suspend fun insert(kviz: Kviz)

    @Query("UPDATE kviz SET predan=:predan WHERE id==:id")
    suspend fun updatePredan(predan: Boolean, id: Int)

}