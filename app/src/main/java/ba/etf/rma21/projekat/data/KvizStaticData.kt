package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*


var sviKvizovi: MutableList<Kviz> = mutableListOf(
        Kviz("IM Kviz 1", "IM", GregorianCalendar(2021,3,1).time, GregorianCalendar(2021,3,16).time, GregorianCalendar(1970,0,1).time, 30,"IM Grupa 1", null),
        Kviz("IM Kviz 2", "IM", GregorianCalendar(2021,3,1).time, GregorianCalendar(2021,3,18).time, GregorianCalendar(1970,0,1).time,30 ,"IM Grupa 2", null),
        Kviz("OE Kviz 1", "OE", GregorianCalendar(2021,3,2).time, GregorianCalendar(2021,3,4).time, GregorianCalendar(1970,0,1).time, 40, "OE Grupa 1", null),
        Kviz("OE Kviz 2", "OE", GregorianCalendar(2021,3,1).time, GregorianCalendar(2021,3,6).time,  GregorianCalendar(1970,0,1).time, 40, "OE Grupa 2", null),
        Kviz("RMA Kviz 1", "RMA", GregorianCalendar(2021,2,31).time, GregorianCalendar(2021,2,31).time, GregorianCalendar(1970,0,1).time, 40, "RMA Grupa 1", null),
        Kviz("RMA Kviz 2", "RMA", GregorianCalendar(2021,1,15).time, GregorianCalendar(2021,4,15).time, GregorianCalendar(1970,0,1).time, 40, "RMA Grupa 2", null),
        Kviz("OOAD Kviz 1", "OOAD", GregorianCalendar(2021,3,15).time, GregorianCalendar(2021,3,15).time,  GregorianCalendar(1970,0,1).time, 90, "OOAD Grupa 1", null),
        Kviz("DM Kviz 1", "DM", GregorianCalendar(2020,9,20).time, GregorianCalendar(2021,9,20).time, GregorianCalendar(1970,0,1).time, 90, "DM Grupa 1", null),
        Kviz("TP Kviz 1", "TP", GregorianCalendar(2021,4,15).time, GregorianCalendar(2021,4,15).time,  GregorianCalendar(1970,0,1).time, 45, "TP Grupa 1", null),
        Kviz("DM Kviz 2", "DM", GregorianCalendar(2020,8,29).time, GregorianCalendar(2021,8,29).time, GregorianCalendar(1970,0,1).time, 90, "DM Grupa 1", null),
        Kviz("RPR Kviz 1", "RPR", GregorianCalendar(2020,11,15).time, GregorianCalendar(2020,11,15).time, GregorianCalendar(1970,0,1).time, 40, "RPR Grupa 1", null),
        Kviz("TP Kviz 2", "TP", GregorianCalendar(2021,1,15).time, GregorianCalendar(2021,4,15).time,  GregorianCalendar(1970,0,1).time, 5, "TP Grupa 1", null),
        Kviz("RPR Kviz 2", "RPR", GregorianCalendar(2021,2,15).time, GregorianCalendar(2021,11,15).time, GregorianCalendar(1970,0,1).time, 35, "RPR Grupa 1", null),
        Kviz("OIS Kviz 1", "OIS", GregorianCalendar(2021,8,15).time, GregorianCalendar(2021,8,15).time, GregorianCalendar(1970,0,1).time, 85, "OIS Grupa 1", null),
        Kviz("OIS Kviz 1", "OIS", GregorianCalendar(2021,8,16).time, GregorianCalendar(2021,8,16).time, GregorianCalendar(1970,0,1).time, 85, "OIS Grupa 2", null),
        Kviz("RG Kviz 2", "RG", GregorianCalendar(2021,8,15).time, GregorianCalendar(2021,8,15).time, GregorianCalendar(1970,0,1).time, 34, "RG Grupa 2", null),
        Kviz("WT Kviz 3", "WT", GregorianCalendar(2021,2,15).time, GregorianCalendar(2021,5,10).time, GregorianCalendar(1970,0,1).time, 10, "WT Grupa 3", null)
)
fun allKvizes(): MutableList<Kviz>{
    return sviKvizovi
}

fun kvizStatus(bodovi: Float, nazivKviza: String, nazivGrupe: String){
   var kviz =  allKvizes().find{ kviz ->  kviz.naziv == nazivKviza && kviz.nazivGrupe == nazivGrupe }
    kviz?.osvojeniBodovi = bodovi
    kviz?.datumRada = Date()
}

