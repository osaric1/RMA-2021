package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetRepository

class PredmetViewModel {
    fun getUpisani(): List<Predmet> {
        return PredmetRepository.getUpisani()
    }

    fun getAll(): List<Predmet> {
        return PredmetRepository.getAll()
    }

    fun getPredmetsByGodinama(godina: Int): List<Predmet> {
        return PredmetRepository.getPredmetsByGodinama(godina)
    }
}