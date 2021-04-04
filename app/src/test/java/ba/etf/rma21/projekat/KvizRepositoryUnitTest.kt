package ba.etf.rma21.projekat


import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers.hasProperty
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Test
import java.util.*


class KvizRepositoryUnitTest {
    @Test
    fun testGetAll(){
        val kvizovi = KvizRepository.getAll()
        assertEquals(kvizovi.size, 17)
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("naziv", Is("IM Kviz 1"))))
        assertThat(kvizovi, not(hasItem(hasProperty("naziv", Is("Neka vrijednsost")))))
    }

    @Test
    fun testGetMyKvizes(){
        val kvizovi = KvizRepository.getMyKvizes()
        assertEquals(kvizovi.size, 7)
        assertThat(kvizovi, hasItem(hasProperty("nazivGrupe", Is("RMA Grupa 2"))))
        assertThat(kvizovi, not(hasItem(hasProperty("nazivGrupe", Is("OOAD Grupa 1")))))
    }

    @Test
    fun testGetDone(){
        val kvizovi = KvizRepository.getDone()
        assertEquals(kvizovi.size, 3)
        assertThat(kvizovi, hasItem(hasProperty("datumRada", Is(GregorianCalendar(2020,9,20).time))))
    }

    @Test
    fun testGetFuture(){
        val kvizovi = KvizRepository.getFuture()
        assertEquals(kvizovi.size, 1)
        assertThat(kvizovi, not(hasItem(hasProperty("nazivGrupe", Is("OOAD Grupa 1")))))
        assertThat(kvizovi, hasItem(hasProperty("nazivPredmeta", Is("TP"))))
        assertThat(kvizovi, not(hasItem(hasProperty("nazivPredmeta", Is("RMA")))))
    }

    @Test
    fun testGetNotTaken(){
        val kvizovi = KvizRepository.getNotTaken()
        assertEquals(kvizovi.size, 2)
        assertThat(kvizovi, not(hasItem(hasProperty("nazivPredmeta", Is("TP")))))
        assertThat(kvizovi, hasItem(hasProperty("nazivPredmeta", Is("RMA"))))
    }
}