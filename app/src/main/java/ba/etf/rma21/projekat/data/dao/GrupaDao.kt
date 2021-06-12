package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa

@Dao
interface GrupaDao {
    @Query("DELETE FROM grupa")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(vararg grupa: Grupa)

    @Query("SELECT id FROM grupa WHERE id==:id")
    suspend fun checkDuplicate(id: Int): Int?

    @Query("SELECT * FROM grupa")
    suspend fun getGrupeIzBaze(): List<Grupa>

    @Query("SELECT * FROM grupa WHERE id==:id")
    suspend fun getGrupa(id: Int) : Grupa
}