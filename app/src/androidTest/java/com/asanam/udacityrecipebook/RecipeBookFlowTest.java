package com.asanam.udacityrecipebook;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeBookFlowTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setupIdlingResource() {
        CountingIdlingResource mainActivityIdlingResource = mActivityTestRule.getActivity().getEspressoIdlingResourceForMainActivity();

        Espresso.registerIdlingResources(mainActivityIdlingResource);
    }

    @Test
    public void recipeBookFlowTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_recipe_card),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.rv_recipe_card),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                0)));
        recyclerView3.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction constraintLayout = onView(
                allOf(withId(R.id.inc_ingredients),
                        childAtPosition(
                                allOf(withId(R.id.frag_details),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        constraintLayout.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.rv_recipe_card),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                0)));
        recyclerView4.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.tv_details), withText("Recipe Introduction"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_steps),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(4940);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_next), withText("Next"),
                        childAtPosition(
                                allOf(withId(R.id.frag_step_details_fragment),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_previous), withText("Previous"),
                        childAtPosition(
                                allOf(withId(R.id.frag_step_details_fragment),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @After
    public void onComplete() {
        CountingIdlingResource mainActivityIdlingResource = mActivityTestRule.getActivity().getEspressoIdlingResourceForMainActivity();

        if(mainActivityIdlingResource != null) {
            Espresso.unregisterIdlingResources(mainActivityIdlingResource);
        }
    }
}
