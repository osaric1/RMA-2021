package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers.hasProperty
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Test

class PredmetRepositoryUnitTest {

    @Test
    fun testGetAll(){
        val predmeti = PredmetRepository.getAll()
        assertEquals(predmeti.size, 6)
        assertThat(predmeti, hasItem(hasProperty("naziv", Is("IM"))))
        assertThat(predmeti, not(hasItem(hasProperty("naziv", Is("Neka vrijednsost")))))
    }

    @Test
    fun testGetPredmetsByGodinama(){
        assertEquals(PredmetRepository.getPredmetsByGodinama(1).size, 2)
        assertEquals(PredmetRepository.getPredmetsByGodinama(2).size, 4)
        assertEquals(PredmetRepository.getPredmetsByGodinama(3).size, 0)
        assertEquals(PredmetRepository.getPredmetsByGodinama(4).size, 0)
        assertEquals(PredmetRepository.getPredmetsByGodinama(5).size, 0)
    }

    @Test
    fun testGetSlobodni(){
        PredmetRepository.addPredmet(Predmet("OE", 1))
        PredmetRepository.addPredmet(Predmet("IM", 1))

        assertEquals(PredmetRepository.getSlobodni(1).size, 0)
    }

    @Test
    fun testGetSlobodniAll(){
        assertEquals(PredmetRepository.getSlobodniAll().size, 1)

        assertThat(PredmetRepository.getSlobodniAll(), not(hasItem(hasProperty("naziv", Is("OE")))))
        assertThat(PredmetRepository.getSlobodniAll(), not(hasItem(hasProperty("naziv", Is("IM")))))
    }
}