package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    suspend fun insertAcHash(vararg acHash: String)

    @Query("DELETE FROM account WHERE id != (:accountId)")
    suspend fun deleteAccounts(vararg accountId: Int)

    @Query("SELECT id FROM account WHERE acHash == (:accountHash)")
    suspend fun getAccountId(vararg accountHash: String): Int
}