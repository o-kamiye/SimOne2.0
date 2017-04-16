package ng.com.tinweb.www.simone20.today;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.simone20.MainActivity;
import ng.com.tinweb.www.simone20.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Created by kamiye on 06/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TodayFragmentUITest {

    @Rule
    public IntentsTestRule<MainActivity> activityTestRule =
            new IntentsTestRule<>(MainActivity.class);

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
    public void testFragmentChangingUI() {
        onView(withChild(withId(R.id.todayCallsTextView))).perform(swipeLeft());

        onView(withId(R.id.todayCallsTextView)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testRecyclerView_callIconClick() {

        // TODO: 16/04/2017 Fix phone call tests later
//        onView(withId(R.id.todayCallsRecyclerView))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
//                        RecyclerViewAction.clickCallIconImageView(R.id.callIconImageView)));
//
//        onView(withText(containsString("Calling"))).inRoot(isToast()).check(matches(isDisplayed()));
//
//        intended(hasAction(Intent.ACTION_CALL));

    }

}