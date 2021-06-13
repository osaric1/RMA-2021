package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Odgovor

@Dao
interface OdgovorDao {
    @Insert
    suspend fun insert(odgovor: Odgovor)

    @Query("SELECT MAX(id) FROM odgovor")
    suspend fun najveciId(): Int?

    @Query("SELECT * FROM odgovor WHERE idKviz==:idKviz")
    suspend fun getOdgovoreZaKvizIzBaze(idKviz: Int): List<Odgovor>

    @Query("SELECT id,odgovoreno,PitanjeId,idKviz FROM odgovor WHERE PitanjeId==:PitanjeId AND idKviz==:idKviz")
    suspend fun checkDuplicate(PitanjeId: Int, idKviz: Int):Odgovor?

    @Query("DELETE FROM odgovor")
    suspend fun deleteAll()

}