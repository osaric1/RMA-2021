package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

class KvizRepository() {
    companion object {
        private var kvizovi: List<Kviz>
        private var upisaneGrupe : MutableList<Grupa> = mutableListOf()
        init {
            kvizovi = listOf(
                Kviz("IM Kviz", "IM",GregorianCalendar(2021,3,1).time,GregorianCalendar(2021,3,16).time, GregorianCalendar(1970,0,1).time, 30,"IM_Grupa_1", null),
                Kviz("IM Kviz", "IM", GregorianCalendar(2021,3,1).time, GregorianCalendar(2021,3,18).time, GregorianCalendar(1970,0,1).time,30 ,"IM_Grupa_2", null),
                Kviz("OE Kviz", "OE", GregorianCalendar(2021,3,2).time,GregorianCalendar(2021,3,4).time, GregorianCalendar(1970,0,1).time, 40, "OE_Grupa_1", null),
                Kviz("OE Kviz", "OE", GregorianCalendar(2021,3,1).time,GregorianCalendar(2021,3,6).time,  GregorianCalendar(1970,0,1).time, 40, "OE_Grupa_2", null),
                Kviz("RMA Kviz", "RMA", GregorianCalendar(2021,2,31).time,GregorianCalendar(2021,2,31).time, GregorianCalendar(1970,0,1).time, 40, "RMA_Grupa_1", null),
                Kviz("RMA Kviz", "RMA", GregorianCalendar(2021,3,1).time,GregorianCalendar(2021,3,1).time, GregorianCalendar(1970,0,1).time, 40, "RMA_Grupa_2", null),
                Kviz("OOAD Kviz", "OOAD", GregorianCalendar(2021,3,15).time,GregorianCalendar(2021,3,15).time,  GregorianCalendar(1970,0,1).time, 90, "OOAD Grupa 1", null),
                Kviz("DM Kviz", "DM", GregorianCalendar(2020,9,20).time,GregorianCalendar(2020,9,20).time, GregorianCalendar(2020,9,20).time, 90, "OOAD Grupa 1", 5f) )
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