package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje

@Dao
interface PitanjeDao {
    @Query("DELETE FROM pitanje")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(pitanja: List<Pitanje>)

    @Query("SELECT * FROM pitanje WHERE KvizId==:idKviza")
    suspend fun getPitanjaZaKviz(idKviza: Int): List<Pitanje>

    @Query("SELECT id FROM pitanje WHERE id==:id")
    suspend fun checkDuplicate(id: Int): Int?

    @Insert
    suspend fun insert(pitanje: Pitanje)

    @Query("SELECT MAX(id) FROM pitanje")
    suspend fun najveciId(): Int
}