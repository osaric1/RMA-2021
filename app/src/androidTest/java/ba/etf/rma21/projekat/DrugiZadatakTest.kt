package ba.etf.rma21.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DrugiZadatakTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun drugiTest(){
        Espresso.onView(ViewMatchers.withId(R.id.filterKvizova)).perform(ViewActions.click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Svi moji kvizovi"))).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(ViewMatchers.hasDescendant(ViewMatchers.withText("TP Kviz 2")),
                ViewMatchers.hasDescendant(ViewMatchers.withText("TP"))), ViewActions.click()))

        Espresso.onView(ViewMatchers.withId(R.id.navigacijaPitanja)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        val pitanja = PitanjeKvizRepository.getPitanja("TP Kviz 2", "TP")
        var indeks = 0
        for (pitanje in pitanja) {
            Espresso.onView(ViewMatchers.withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(indeks))
            Espresso.onView(ViewMatchers.withId(R.id.odgovoriLista)).check(matches(isDisplayed()))
            Espresso.onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(0).perform(click())
            indeks++
        }
        Espresso.onView(withId(R.id.predajKviz)).perform(click())
        Espresso.onView(withId(R.id.kvizovi)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(ViewMatchers.hasDescendant(ViewMatchers.withText("TP Kviz 2")),
                ViewMatchers.hasDescendant(ViewMatchers.withText("TP"))), ViewActions.click()))

        Espresso.onView(ViewMatchers.withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(pitanja.size))
        Espresso.onView(ViewMatchers.withSubstring("Završili ste kviz")).check(matches(isDisplayed()))

        Espresso.onView(withId(R.id.kvizovi)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(ViewMatchers.hasDescendant(ViewMatchers.withText("TP Kviz 2")),
                ViewMatchers.hasDescendant(ViewMatchers.withText("TP"))), ViewActions.click()))

        indeks = 0
        for (pitanje in pitanja) {
            Espresso.onView(ViewMatchers.withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(indeks))
            Espresso.onView(ViewMatchers.withId(R.id.odgovoriLista)).check(matches(isDisplayed()))
            Espresso.onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(0).check(matches(not(isClickable())))
            indeks++
        }


    }


}