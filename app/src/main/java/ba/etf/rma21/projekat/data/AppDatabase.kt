package ba.etf.rma21.projekat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ba.etf.rma21.projekat.Converters
import ba.etf.rma21.projekat.data.dao.*
import ba.etf.rma21.projekat.data.models.*

@Database(entities = arrayOf(Account::class, Kviz::class, Predmet::class,KvizTaken::class, Grupa::class,Pitanje::class, GrupaKviz::class, Odgovor::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun kvizDao(): KvizDao
    abstract fun kvizTakenDao(): KvizTakenDao
    abstract fun pitanjeDao(): PitanjeDao
    abstract fun predmetDao(): PredmetDao
    abstract fun grupaDao(): GrupaDao
    abstract fun grupaKvizDao(): GrupaKvizDao
    abstract fun odgovorDao(): OdgovorDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun setInstance(appdb:AppDatabase):Unit{
            INSTANCE=appdb
        }

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        private  fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "RMA21DB"
            ).build()
    }
}