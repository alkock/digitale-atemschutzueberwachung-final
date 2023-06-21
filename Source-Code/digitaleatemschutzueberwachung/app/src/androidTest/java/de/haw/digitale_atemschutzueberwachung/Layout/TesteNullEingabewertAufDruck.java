package de.haw.digitale_atemschutzueberwachung.Layout;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.haw.digitale_atemschutzueberwachung.R;

/**Diese Klasse testet alle Eingabefelder für den Druck durch, und überprüft, ob bei keiner Eingabe kein Fehler auftritt **/
@LargeTest
@RunWith(AndroidJUnit4.class)
public class TesteNullEingabewertAufDruck {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testeNullEingabewertAufDruck() {
        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.start_truppfuehrer_druck), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                2),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.start_truppfuehrer_druck), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("0 bar")));

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.start_truppmann_druck), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                3),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.start_truppmann_druck), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView2.check(matches(withText("0 bar")));

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.start_truppmann2_druck), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                4),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.start_truppmann2_druck), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView3.check(matches(withText("0 bar")));

        ViewInteraction materialTextView4 = onView(
                allOf(withId(R.id.eindrittel_truppfuehrer_druck), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                2),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.eindrittel_truppfuehrer_druck), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView4.check(matches(withText("0 bar")));

        ViewInteraction materialTextView5 = onView(
                allOf(withId(R.id.eindrittel_truppmann_druck), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                3),
                        isDisplayed()));
        materialTextView5.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.eindrittel_truppmann_druck), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView5.check(matches(withText("0 bar")));

        ViewInteraction materialTextView6 = onView(
                allOf(withId(R.id.eindrittel_truppmann2_druck), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                4),
                        isDisplayed()));
        materialTextView6.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.eindrittel_truppmann2_druck), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView6.check(matches(withText("0 bar")));

        ViewInteraction materialTextView7 = onView(
                allOf(withId(R.id.zweidrittel_truppfuehrer_druck), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                2),
                        isDisplayed()));
        materialTextView7.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.zweidrittel_truppfuehrer_druck), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView7.check(matches(withText("0 bar")));

        ViewInteraction materialTextView8 = onView(
                allOf(withId(R.id.zweidrittel_truppmann_druck), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                3),
                        isDisplayed()));
        materialTextView8.perform(click());

        ViewInteraction materialButton8 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.zweidrittel_truppmann_druck), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView8.check(matches(withText("0 bar")));

        ViewInteraction materialTextView9 = onView(
                allOf(withId(R.id.zweidrittel_truppmann2_druck), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                4),
                        isDisplayed()));
        materialTextView9.perform(click());

        ViewInteraction materialButton9 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton9.perform(click());

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.zweidrittel_truppmann2_druck), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView9.check(matches(withText("0 bar")));

        ViewInteraction materialTextView10 = onView(
                allOf(withId(R.id.ziel_druck_truppfuehrer), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                2),
                        isDisplayed()));
        materialTextView10.perform(click());

        ViewInteraction materialButton10 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton10.perform(click());

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.ziel_druck_truppfuehrer), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView10.check(matches(withText("0 bar")));

        ViewInteraction materialTextView11 = onView(
                allOf(withId(R.id.ziel_druck_truppmann), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                3),
                        isDisplayed()));
        materialTextView11.perform(click());

        ViewInteraction materialButton11 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton11.perform(click());

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.ziel_druck_truppmann), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView11.check(matches(withText("0 bar")));

        ViewInteraction materialTextView12 = onView(
                allOf(withId(R.id.ziel_druck_truppmann2), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                4),
                        isDisplayed()));
        materialTextView12.perform(click());

        ViewInteraction materialButton12 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton12.perform(click());

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.ziel_druck_truppmann2), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView12.check(matches(withText("0 bar")));

        ViewInteraction materialTextView13 = onView(
                allOf(withId(R.id.rueckzugist_druck_truppfuehrer), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        7),
                                2),
                        isDisplayed()));
        materialTextView13.perform(click());

        ViewInteraction materialButton13 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton13.perform(click());

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.rueckzugist_druck_truppfuehrer), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView13.check(matches(withText("0 bar")));

        ViewInteraction materialTextView14 = onView(
                allOf(withId(R.id.rueckzugist_druck_truppmann), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        7),
                                3),
                        isDisplayed()));
        materialTextView14.perform(click());

        ViewInteraction materialButton14 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton14.perform(click());

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.rueckzugist_druck_truppmann), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView14.check(matches(withText("0 bar")));

        ViewInteraction materialTextView15 = onView(
                allOf(withId(R.id.rueckzugist_druck_truppmann2), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        7),
                                4),
                        isDisplayed()));
        materialTextView15.perform(click());

        ViewInteraction materialButton15 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton15.perform(click());

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.rueckzugist_druck_truppmann2), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView15.check(matches(withText("0 bar")));

        ViewInteraction materialTextView16 = onView(
                allOf(withId(R.id.ende_druck_truppfuehrer), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                2),
                        isDisplayed()));
        materialTextView16.perform(click());

        ViewInteraction materialButton16 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton16.perform(click());

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.ende_druck_truppfuehrer), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView16.check(matches(withText("0 bar")));

        ViewInteraction materialTextView17 = onView(
                allOf(withId(R.id.ende_druck_truppmann), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                3),
                        isDisplayed()));
        materialTextView17.perform(click());

        ViewInteraction materialButton17 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton17.perform(click());

        ViewInteraction textView17 = onView(
                allOf(withId(R.id.ende_druck_truppmann), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView17.check(matches(withText("0 bar")));

        ViewInteraction materialTextView18 = onView(
                allOf(withId(R.id.ende_druck_truppmann2), withText("0 bar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                4),
                        isDisplayed()));
        materialTextView18.perform(click());

        ViewInteraction materialButton18 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        materialButton18.perform(click());

        ViewInteraction textView18 = onView(
                allOf(withId(R.id.ende_druck_truppmann2), withText("0 bar"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView18.check(matches(withText("0 bar")));

        ViewInteraction materialButton19 = onView(
                allOf(withId(R.id.zweitertrupp), withText("FUNKRUFNAME TRUPP 2 0:00"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        materialButton19.perform(click());

        ViewInteraction materialButton20 = onView(
                allOf(withId(R.id.erstertrupp), withText("Funkrufname Trupp 1 00:00"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        materialButton20.perform(click());
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
}
