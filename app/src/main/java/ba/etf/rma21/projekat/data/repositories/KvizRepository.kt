package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class KvizRepository {

    companion object {
        private var kvizovi: List<Kviz>
        init {
            kvizovi = listOf(
                Kviz("Kviz 1", "IM", Date(2021, 4,1),Date(2021,4,1), Date(2021,4,1), 30,"Grupa 1", 10f),
                Kviz("Kviz 2", "IM", Date(2021, 5,6),Date(2021,5,6), Date(2021,5,6), 30,"Grupa 2", 10f),
                Kviz("Kviz 3", "OE", Date(2021, 4,7),Date(2021,4,7), Date(2021,4,7), 60,"Grupa 1", 40f),
                Kviz("Kviz 4", "OE", Date(2021, 4,7),Date(2021,4,7), Date(2021,4,7), 60,"Grupa 2", 40f),
                Kviz("Kviz 5", "RMA", Date(2021, 4,8),Date(2021,4,8), Date(2021,4,8), 90,"Grupa 1", 5f),
                Kviz("Kviz 6", "RMA", Date(2021, 4,8),Date(2021,4,8), Date(2021,4,8), 90,"Grupa 2", 5f))
        }

        fun getMyKvizes(): List<Kviz> {
            return kvizovi.filter { kviz -> kviz.nazivGrupe == "DM_Grupa_1" && kviz.nazivPredmeta == "DM" }.toList()
        }

        fun getAll(): List<Kviz> {
            return kvizovi
        }

        fun getDone(): List<Kviz> {
            return getMyKvizes().filter { kviz -> kviz.datumKraj.before(Date()) }.toList()
        }

        fun getFuture(): List<Kviz> {
            return getMyKvizes().filter { kviz -> kviz.datumKraj.after(Date()) }.toList()
        }

        fun getNotTaken(): List<Kviz> {
            return kvizovi.filter{kviz -> kviz.nazivPredmeta != "DM" }.toList()
        }
        // TODO: Implementirati i ostale potrebne metode
    }
}