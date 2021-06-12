package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Account

@Dao
interface AccountDao {
    @Insert
    suspend fun insertAccount(account: Account)

    @Query("DELETE FROM account WHERE id !=:id")
    suspend fun deleteAccounts(id: Int)

    @Query("SELECT id FROM account WHERE acHash ==:acHash")
    suspend fun getAccountId(acHash: String): Int

    @Query("SELECT lastUpdate FROM account WHERE acHash==:acHash")
    suspend fun getLastUpdate(acHash: String): String?

    @Query("UPDATE account SET id=:id, student=:student, acHash=:acHash WHERE id == :stariId")
    suspend fun changeUser(id: Int, student: String, acHash: String, stariId: String)

    @Query("UPDATE account SET lastUpdate=:lastUpdate WHERE acHash==:acHash")
    suspend fun setLastUpdate(acHash: String, lastUpdate: String)



}