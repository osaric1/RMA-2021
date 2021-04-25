package ba.etf.rma21.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun prviTest(){
        Espresso.onView(ViewMatchers.withId(R.id.bottomNav)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.predmeti)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.predmeti)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.odabirGodina)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("2")).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.odabirPredmet)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("RPR")).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.odabirGrupa)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("RPR Grupa 1")).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.kvizovi)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.predmeti)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.odabirGodina)).check(ViewAssertions.matches(ViewMatchers.withSpinnerText(CoreMatchers.containsString("2"))))

        Espresso.onView(ViewMatchers.withId(R.id.odabirPredmet)).check(ViewAssertions.matches(ViewMatchers.withSpinnerText(CoreMatchers.containsString("RPR"))))

        Espresso.onView(ViewMatchers.withId(R.id.odabirGrupa)).check(ViewAssertions.matches(ViewMatchers.withSpinnerText(CoreMatchers.containsString("RPR Grupa 1"))))

    }

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
            Espresso.onView(ViewMatchers.withId(R.id.odgovoriLista)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onData(Matchers.anything()).inAdapterView(ViewMatchers.withId(R.id.odgovoriLista)).atPosition(0).perform(ViewActions.click())
            indeks++
        }
        Espresso.onView(ViewMatchers.withId(R.id.predajKviz)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.kvizovi)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(ViewMatchers.hasDescendant(ViewMatchers.withText("TP Kviz 2")),
                ViewMatchers.hasDescendant(ViewMatchers.withText("TP"))), ViewActions.click()))

        Espresso.onView(ViewMatchers.withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(pitanja.size))
        Espresso.onView(ViewMatchers.withSubstring("Završili ste kviz")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.kvizovi)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(ViewMatchers.hasDescendant(ViewMatchers.withText("TP Kviz 2")),
                ViewMatchers.hasDescendant(ViewMatchers.withText("TP"))), ViewActions.click()))

        indeks = 0
        for (pitanje in pitanja) {
            Espresso.onView(ViewMatchers.withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(indeks))
            Espresso.onView(ViewMatchers.withId(R.id.odgovoriLista)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onData(Matchers.anything()).inAdapterView(ViewMatchers.withId(R.id.odgovoriLista)).atPosition(0).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isClickable())))
            indeks++
        }


    }
}


