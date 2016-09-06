package ng.com.tinweb.www.simone20;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by kamiye on 04/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class WelcomeActivityTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> activityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void testButton_opensTodayFragmentUI() {
        onView(withId(R.id.button))
                .perform(click());

        onView(withId(R.id.todayCallsTextView))
                .check(matches(isDisplayed()));

        onView(withId(R.id.todayCallsRecyclerView))
                .check(matches(isDisplayed()));
    }

}