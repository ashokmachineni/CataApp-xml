package com.example.cats.presentation.details

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.cats.R
import com.example.cats.presentation.home.HomeActivity
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val PACKAGE_NAME = "com.example.cats"
private const val MESSAGE = "This is a test"

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailsActivityTest : TestCase() {

    @get:Rule
    var activityRule: IntentsTestRule<DetailsActivity> =
        IntentsTestRule(DetailsActivity::class.java)

    @Before
    override fun setUp() {
        super.setUp()
        Intents.init()
        ActivityScenario.launch(DetailsActivity::class.java)
    }

    @After
    override fun tearDown() {
        Intents.release()
    }

    @Test
    fun testButtonVisibility() {
        onView(withId(R.id.main)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.themeChange)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testBackClick() {
        // Clicks a button to send the message to another
        // activity through an explicit intent.
        onView(withId(R.id.home)).perform(click())

        // Verifies that the DisplayMessageActivity received an intent
        // with the correct package name and message.
        intended(hasComponent(hasShortClassName(HomeActivity::class.java.name)))

    }


}