package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Grupa

fun allGrupe(): List<Grupa> {
    return listOf(Grupa("IM Grupa 1", "IM"),
            Grupa("IM Grupa 2", "IM"),
            Grupa("OE Grupa 1", "OE"),
            Grupa("OE Grupa 2", "OE"),
            Grupa("RMA Grupa 1", "RMA"),
            Grupa("RMA Grupa 2", "RMA"),
            Grupa("DM Grupa 1", "DM"),
            Grupa("OOAD Grupa 1", "OOAD"),
            Grupa("TP Grupa 1", "TP"))
}