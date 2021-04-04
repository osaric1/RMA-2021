package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test

class GrupaRepositoryUnitTest {

    @Test
    fun testGetGroupsByPredmet1(){
        val grupe = GrupaRepository.getGroupsByPredmet("IM")

        assertEquals(grupe.size, 2)
        Assert.assertThat(grupe, CoreMatchers.hasItem(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("IM"))))
        Assert.assertThat(grupe, not(CoreMatchers.hasItem(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("RMA")))))
        Assert.assertThat(grupe, not(CoreMatchers.hasItem(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("OOAD")))))
    }

    @Test
    fun testGetGroupsByPredmet2(){
        val grupe = GrupaRepository.getGroupsByPredmet("String")

        assertEquals(grupe.size, 0)
        Assert.assertThat(grupe, not(CoreMatchers.hasItem(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("String")))))
    }

    @Test
    fun testGetAll(){
        val grupe = GrupaRepository.getAll()

        assertEquals(grupe.size, 17)
        Assert.assertThat(grupe, CoreMatchers.hasItem(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("IM"))))
        Assert.assertThat(grupe, CoreMatchers.hasItem(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("RMA"))))
        Assert.assertThat(grupe, CoreMatchers.hasItem(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("TP"))))
        Assert.assertThat(grupe, CoreMatchers.hasItem(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("OOAD"))))
        Assert.assertThat(grupe, CoreMatchers.hasItem(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("OE"))))
    }

}