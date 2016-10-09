package ng.com.tinweb.www.simone20.group;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ng.com.tinweb.www.simone20.MainActivity;
import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.helper.RecyclerViewAction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ng.com.tinweb.www.simone20.helper.ToastMatcher.isToast;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;


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
        onView(withChild(withId(R.id.container))).perform(swipeLeft());
        onView(withChild(withId(R.id.container))).perform(swipeLeft());
        onView(allOf(withId(R.id.bottom_navigation_container), withChild(withText("Groups"))))
                .perform(click());
    }

    @Test
    public void testGroupScreenVisible() {
        onView(withId(R.id.totalGroupsTextView)).check(matches(isDisplayed()));
    }
//
//    @Test
//    public void testGroupInfoVisible() {
//        // add this test when implemented
//    }
//
//    @Test
//    public void testEditIconClick() {
//        // edit icon not implemented yet
//    }
//
//    @Test
//    public void testDeleteIconClick() {
//        // deleteIcon not implemented yet
//    }

    @Test
    public void testFABClick() {
        String fragmentTitle = activityTestRule.getActivity().getString(R.string.new_group_title);
        onView(withId(R.id.groupsFAB)).perform(click());

        onView(withId(R.id.content_group_add)).check(matches(isDisplayed()));

        onView(withId(android.R.id.title)).check(matches(withText(containsString(fragmentTitle))));
    }

}