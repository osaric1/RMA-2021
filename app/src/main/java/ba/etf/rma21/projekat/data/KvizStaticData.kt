package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

fun allKvizes(): List<Kviz>{
    return listOf(
            Kviz("IM Kviz", "IM", GregorianCalendar(2021,3,1).time, GregorianCalendar(2021,3,16).time, GregorianCalendar(1970,0,1).time, 30,"IM Grupa 1", null),
            Kviz("IM Kviz", "IM", GregorianCalendar(2021,3,1).time, GregorianCalendar(2021,3,18).time, GregorianCalendar(1970,0,1).time,30 ,"IM Grupa 2", null),
            Kviz("OE Kviz", "OE", GregorianCalendar(2021,3,2).time, GregorianCalendar(2021,3,4).time, GregorianCalendar(1970,0,1).time, 40, "OE Grupa 1", null),
            Kviz("OE Kviz", "OE", GregorianCalendar(2021,3,1).time, GregorianCalendar(2021,3,6).time,  GregorianCalendar(1970,0,1).time, 40, "OE Grupa 2", null),
            Kviz("RMA Kviz", "RMA", GregorianCalendar(2021,2,31).time, GregorianCalendar(2021,2,31).time, GregorianCalendar(1970,0,1).time, 40, "RMA Grupa 1", null),
            Kviz("RMA Kviz", "RMA", GregorianCalendar(2021,3,1).time, GregorianCalendar(2021,3,1).time, GregorianCalendar(1970,0,1).time, 40, "RMA Grupa 2", null),
            Kviz("OOAD Kviz", "OOAD", GregorianCalendar(2021,3,15).time, GregorianCalendar(2021,3,15).time,  GregorianCalendar(1970,0,1).time, 90, "OOAD Grupa 1", null),
            Kviz("DM Kviz", "DM", GregorianCalendar(2020,9,20).time, GregorianCalendar(2020,9,20).time, GregorianCalendar(2020,9,20).time, 90, "DM Grupa 1", 5f),
            Kviz("TP Kviz", "TP", GregorianCalendar(2021,4,15).time, GregorianCalendar(2021,4,15).time,  GregorianCalendar(1970,0,1).time, 45, "TP Grupa 1", null)
    )
}

