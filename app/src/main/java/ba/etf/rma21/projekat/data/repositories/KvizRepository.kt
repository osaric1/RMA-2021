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
            kvizovi = listOf(
                Kviz("IM Kviz", "IM", Date(2021, 4,1),Date(2021,4,1), Date(2021,4,1), 30,"IM_Grupa_1", null),
                Kviz("IM Kviz", "IM", Date(2021, 5,6),Date(2021,5,6), Date(2021,5,6), 30,"IM_Grupa_2", null),
                Kviz("OE Kviz", "OE", Date(2021, 4,7),Date(2021,4,7), Date(2021,4,7), 60,"OE_Grupa_1", null),
                Kviz("OE Kviz", "OE", Date(2021, 4,7),Date(2021,4,7), Date(2021,4,7), 60,"OE_Grupa_2", null),
                Kviz("RMA Kviz", "RMA", Date(1, 4,8),Date(1,4,8), Date(2021,4,8), 90,"RMA_Grupa_1", null),
                Kviz("DM Kviz", "DM", Date(1, 3,25),Date(1,3,25), null, 4,"DM_Grupa_1", 500f))
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