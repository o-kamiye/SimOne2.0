package ng.com.tinweb.www.simone20.reminder;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.simone20.MainActivity;
import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.helper.RecyclerViewAction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ng.com.tinweb.www.simone20.helper.ToastMatcher.isToast;

/**
 * Created by kamiye on 09/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ReminderFragmentUITest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void restartActivity() {
        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().recreate();
            }
        });
    }

    @Test
    public void testReminderScreenVisible() {
        onView(withChild(withId(R.id.todayCallsTextView))).perform(swipeLeft());

        onView(withId(R.id.weeklyRemindersTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditIconClick() {
        onView(withChild(withId(R.id.todayCallsTextView))).perform(swipeLeft());

        onView(withId(R.id.weeklyRemindersRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        RecyclerViewAction.clickCallIconImageView(R.id.editIconImageView)));

        onView(withText("Edit reminder pop will appear here")).inRoot(isToast()).check(matches(isDisplayed()));
    }

    @Test
    public void testDeleteIconClick() {
        // deleteIcon not implemented yet
    }

    @Test
    public void testFABClick() {
        // floating action button not implemented yet
    }
}