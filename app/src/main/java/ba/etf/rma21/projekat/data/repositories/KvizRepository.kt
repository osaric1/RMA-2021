package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import java.util.*

class KvizRepository() {
    companion object {
        private var kvizovi: List<Kviz>
        private var upisaneGrupe : MutableList<Grupa> = mutableListOf()
        init {
            var calendar: Calendar = Calendar.getInstance()
            kvizovi = listOf(
                Kviz("IM Kviz", "IM",GregorianCalendar(2021,3,1).time,GregorianCalendar(2021,3,16).time, null, 30,"IM_Grupa_1", null),
                Kviz("IM Kviz", "IM", GregorianCalendar(2021,3,1).time, GregorianCalendar(2021,3,18).time,null,30 ,"IM_Grupa_2", null),
                Kviz("OE Kviz", "OE", GregorianCalendar(2021,3,2).time,GregorianCalendar(2021,3,4).time, null, 40, "OE_Grupa_1", null))
        }

        fun getMyKvizes(): List<Kviz> {
            return kvizovi.filter { kviz -> upisaneGrupe.filter { grupa -> grupa.naziv == kviz.nazivGrupe }.any()}.toList()
        }

        fun getAll(): List<Kviz> {
            return kvizovi
        }

        fun getDone(): List<Kviz> {
            return getMyKvizes().filter { kviz -> upisaneGrupe.filter { grupa -> grupa.naziv == kviz.nazivGrupe }.any() && kviz.datumKraj.before(Calendar.getInstance().time) && kviz.osvojeniBodovi != null }.toList()
        }

        fun getFuture(): List<Kviz> {
            return getMyKvizes().filter { kviz -> upisaneGrupe.filter { grupa -> grupa.naziv == kviz.nazivGrupe }.any() && kviz.datumKraj.after(Calendar.getInstance().time) }.toList()
        }

        fun getNotTaken(): List<Kviz> {
            return kvizovi.filter{kviz -> upisaneGrupe.filter { grupa -> grupa.naziv == kviz.nazivGrupe }.any() && kviz.datumKraj.before(Calendar.getInstance().time) && kviz.osvojeniBodovi == null }.toList()
        }

        fun addGroup(grupa: Grupa){
            upisaneGrupe.add(grupa)
        }

    }
}