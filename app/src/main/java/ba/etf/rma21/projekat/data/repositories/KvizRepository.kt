package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.*
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

class KvizRepository() {
    companion object {
        private var upisaneGrupe : MutableList<Grupa> = mutableListOf(Grupa("RMA Grupa 2", "RMA"), Grupa("TP Grupa 1", "TP"), Grupa("DM Grupa 1", "DM"))
        init {

        }

        fun getMyKvizes(): List<Kviz> {
            return allKvizes().filter { kviz -> upisaneGrupe.filter { grupa -> grupa.naziv == kviz.nazivGrupe }.any() }.toList()
        }

        fun getAll(): List<Kviz> {
            return allKvizes()
        }

        fun getDone(): List<Kviz> {
            return getMyKvizes().filter { kviz -> toCalendar(kviz.datumRada).get(Calendar.YEAR) != 1970 && toCalendar(kviz.datumKraj) <= Calendar.getInstance() }.toList()
        }

        fun getFuture(): List<Kviz> {
            return getMyKvizes().filter { kviz -> toCalendar(kviz.datumPocetka) > Calendar.getInstance() }.toList()
        }

        fun getNotTaken(): List<Kviz> {
            return getMyKvizes().filter { kviz -> toCalendar(kviz.datumKraj) < Calendar.getInstance() &&  toCalendar(kviz.datumRada).get(Calendar.YEAR) == 1970 }.toList()
        }

        fun addGroup(grupa: Grupa){
            upisaneGrupe.add(grupa)
        }

        fun toCalendar(date: Date): Calendar{
            val cal = Calendar.getInstance()
            cal.time = date
            return cal
        }

    }
}