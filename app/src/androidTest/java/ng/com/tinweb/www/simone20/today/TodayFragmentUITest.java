package ng.com.tinweb.www.simone20.today;

import android.content.Intent;
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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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

        onView(withId(R.id.section_label)).check(matches(isDisplayed()));
    }

    @Test
    public void testRecyclerView_callIconClick() {

        onView(withId(R.id.todayCallsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        MyViewAction.clickCallIconImageView(R.id.callIconImageView)));


        onView(withText("I am going to call Kamiye at position 0")).inRoot(isToast()).check(matches(isDisplayed()));

    }

    public static Matcher<Root> isToast() {
        return new ToastMatcher();
    }

    public static class MyViewAction {

        public static ViewAction clickCallIconImageView(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }

    public static class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    // windowToken == appToken means this window isn't contained by any other windows.
                    // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                    return true;
                }
            }
            return false;
        }

    }
}