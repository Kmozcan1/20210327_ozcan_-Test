package com.kmozcan1.a20210327_ozcan_yooxtest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter.ProductListAdapter
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter.ProductListAdapter.*
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Kadir Mert Özcan on 03-Apr-21.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class ProductListFragmentTest {
    @get: Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isListVisible_onAppLaunch() {
        onView(withId(R.id.productListRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectedListItem_isVisible() {
        onView(withId(R.id.productListRecyclerView))
            .perform(actionOnItemAtPosition<ProductListItemViewHolder>(1, click()))
    }
}