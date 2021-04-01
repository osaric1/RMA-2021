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
                Kviz("IM Kviz", "IM", Date(2021, 4,1),Date(2021,4,1), Date(2021,4,1), 30,"IM_Grupa_1", 10f),
                Kviz("IM Kviz", "IM", Date(2021, 5,6),Date(2021,5,6), Date(2021,5,6), 30,"IM_Grupa_2", 10f),
                Kviz("OE Kviz", "OE", Date(2021, 4,7),Date(2021,4,7), Date(2021,4,7), 60,"OE_Grupa_1", 40f),
                Kviz("OE Kviz", "OE", Date(2021, 4,7),Date(2021,4,7), Date(2021,4,7), 60,"OE_Grupa_2", 40f),
                Kviz("RMA Kviz", "RMA", Date(2021, 4,8),Date(2021,4,8), Date(2021,4,8), 90,"RMA_Grupa_1", 5f),
                    Kviz("IM Kviz", "IM", Date(2021, 4,1),Date(2021,4,1), Date(2021,4,1), 30,"IM_Grupa_1", 10f),
                    Kviz("IM Kviz", "IM", Date(2021, 5,6),Date(2021,5,6), Date(2021,5,6), 30,"IM_Grupa_2", 10f),
                    Kviz("OE Kviz", "OE", Date(2021, 4,7),Date(2021,4,7), Date(2021,4,7), 60,"OE_Grupa_1", 40f),
                    Kviz("OE Kviz", "OE", Date(2021, 4,7),Date(2021,4,7), Date(2021,4,7), 60,"OE_Grupa_2", 40f),
                    Kviz("OE Kviz", "OE", Date(2021, 4,7),Date(2021,4,7), Date(2021,4,7), 60,"OE_Grupa_2", 40f))
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