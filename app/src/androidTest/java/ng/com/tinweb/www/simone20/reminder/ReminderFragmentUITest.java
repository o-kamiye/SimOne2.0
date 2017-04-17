package ng.com.tinweb.www.simone20.reminder;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.simone20.MainActivity;
import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.helper.RecyclerViewAction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by kamiye on 09/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ReminderFragmentUITest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private static String testContactName = "test_contact";

    @Before
    public void restartActivity() {
        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().recreate();
            }
        });

        onView(withChild(withId(R.id.todayCallsTextView))).perform(swipeLeft());
    }

    @Test
    public void testReminderScreenVisible() {
        onView(withId(R.id.weeklyRemindersTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditIconClick() {
        String titleSubstring = "Update Reminder";
        onView(withId(R.id.weeklyRemindersRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        RecyclerViewAction.clickCallIconImageView(R.id.editIconImageView)));

        onView(withId(android.R.id.title)).check(matches(withText(containsString(titleSubstring))));
        onView(withId(R.id.intervalEditText)).check(matches(isDisplayed()));
    }

    @Test
    public void testDeleteIconClick() {

        String dialogTextSubstring = "Do you want to delete";

        onView(withId(R.id.weeklyRemindersRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        RecyclerViewAction.clickCallIconImageView(R.id.deleteIconImageView)));

        onView(withId(android.R.id.message))
                .check(matches(withText(containsString(dialogTextSubstring))));
    }

    @Test
    public void testFABClick() {
        // floating action button not implemented yet
        onView(allOf(withId(R.id.bottom_navigation_container), withChild(withText("Today"))))
                .perform(click());

        onView(allOf(withId(R.id.bottom_navigation_container), withChild(withText("Reminders"))))
                .perform(click());
        onView(withId(R.id.remindersFAB)).perform(click());

        onView(allOf(isAssignableFrom(EditText.class), withHint(R.string.action_search))).
                check(matches(isDisplayed()));
    }

}