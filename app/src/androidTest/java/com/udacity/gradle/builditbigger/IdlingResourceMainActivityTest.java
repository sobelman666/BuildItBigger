package com.udacity.gradle.builditbigger;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

/**
 * Test for AsyncTask joke retrieval behavior
 */
@RunWith(AndroidJUnit4.class)
public class IdlingResourceMainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    /**
     * Test whether a joke is retrieved by the task. Fails if nothing is retrieved, or
     * if an error message is retrieved.
     */
    @Test
    public void asyncTask_returnsJoke() {
        // perform click on tell joke button
        onView(withId(R.id.btn_tell_joke)).perform(click());
        // check joke display text view is not empty
        onView(withId(R.id.tv_joke)).check(matches(withText(not(isEmptyOrNullString()))));
        // check text in joke display text view is not an error message
        onView(withId(R.id.tv_joke))
                .check(matches(withText(not(startsWith(EndpointsAsyncTask.ERROR_PREFIX)))));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}