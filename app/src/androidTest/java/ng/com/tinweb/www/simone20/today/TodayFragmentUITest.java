package ng.com.tinweb.www.simone20.today;

import android.os.IBinder;
import android.support.test.espresso.Root;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.simone20.MainActivity;
import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.helper.RecyclerViewAction;
import ng.com.tinweb.www.simone20.helper.ToastMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ng.com.tinweb.www.simone20.helper.ToastMatcher.isToast;
import static org.hamcrest.Matchers.not;

/**
 * Created by kamiye on 06/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TodayFragmentUITest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void restartActivity() {
        mainActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivityTestRule.getActivity().recreate();
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

        onView(withId(R.id.todayCallsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        RecyclerViewAction.clickCallIconImageView(R.id.callIconImageView)));


        onView(withText("I am going to call Kamiye at position 0")).inRoot(isToast()).check(matches(isDisplayed()));

    }

}