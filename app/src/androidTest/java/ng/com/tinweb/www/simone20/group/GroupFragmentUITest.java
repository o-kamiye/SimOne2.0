package ng.com.tinweb.www.simone20.group;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
 * Created by kamiye on 12/09/2016.
 */
public class GroupFragmentUITest {

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
    public void testGroupScreenVisible() {
        onView(withChild(withId(R.id.todayCallsTextView))).perform(swipeLeft());

        onView(withChild(withId(R.id.weeklyRemindersTextView))).perform(swipeLeft());

        onView(withId(R.id.totalGroupsTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void testGroupInfoVisible() {
        // add this test when implemented
    }

    @Test
    public void testEditIconClick() {
        // edit icon not implemented yet
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