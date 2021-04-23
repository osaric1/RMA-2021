package ba.etf.rma21.projekat


import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PrviZadatakTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun prviTest(){
        onView(withId(R.id.bottomNav)).check(matches(isDisplayed()))
        onView(withId(R.id.predmeti)).check(matches(isDisplayed()))

        onView(withId(R.id.predmeti)).perform(click())
        onView(withId(R.id.odabirGodina)).perform(click())

        onView(withText("2")).perform(click())

        onView(withId(R.id.odabirPredmet)).perform(click())
        onView(withText("RPR")).perform(click())

        onView(withId(R.id.odabirGrupa)).perform(click())
        onView(withText("RPR Grupa 1")).perform(click())

        onView(withId(R.id.kvizovi)).perform(click())
        onView(withId(R.id.predmeti)).perform(click())

        onView(withId(R.id.odabirGodina)).check(matches(withSpinnerText(containsString("2"))))

        onView(withId(R.id.odabirPredmet)).check(matches(withSpinnerText(containsString("RPR"))))

        onView(withId(R.id.odabirGrupa)).check(matches(withSpinnerText(containsString("RPR Grupa 1"))))

    }


}