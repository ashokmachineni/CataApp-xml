package com.example.cats.presentation.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.cats.R
import com.example.cats.utility.SharedPrefHelper
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest : TestCase() {


    @Before
    override fun setUp() {
        super.setUp()
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun testButtonVisibility() {
        ActivityScenario.launch(HomeActivity::class.java)

        onView(withId(R.id.main)).check(matches(isDisplayed()))

        onView(withId(R.id.themeChange)).check(matches(isDisplayed()))

    }

    @Test
    fun testThemeChangeHome() {
        onView(withId(R.id.main)).check(matches(isDisplayed()))
        val tempCheck = SharedPrefHelper.get(SharedPrefHelper.THEME, false)
        onView(withId(R.id.themeChange)).perform(click())
        assertThat(tempCheck != SharedPrefHelper.get(SharedPrefHelper.THEME, false)).isTrue()
    }
}