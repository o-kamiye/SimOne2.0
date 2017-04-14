package ng.com.tinweb.www.simone20.group;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.simone20.MainActivity;
import ng.com.tinweb.www.simone20.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by kamiye on 02/11/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddGroupDialogFragmentUITest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().recreate();
            }
        });

        //ContactHelper.insertSimOneContact(testContactName);
        onView(allOf(withId(R.id.bottom_navigation_container), withChild(withText("Reminders"))))
                .perform(click());

        onView(allOf(withId(R.id.bottom_navigation_container), withChild(withText("Groups"))))
                .perform(click());

        onView(withId(R.id.groupsFAB)).perform(click());

    }

    @Test
    public void testCancelButtonClick() {
        onView(withId(R.id.cancelButton)).perform(click());

        onView(withId(R.id.content_group_add)).check(doesNotExist());
    }

    @Test
    public void testSaveButtonClickWithoutInput() {
        onView(withId(R.id.saveButton)).perform(click());

        onView(withId(R.id.inputErrorTextView))
                .check(matches(isDisplayed()))
                .check(matches(withText("Group name and interval should not be empty")));
    }

    @Test
    public void testSaveButtonClickWithInput() {
        // TODO implement this test when the delete feature has been created
//        String groupName = "Test Group";
//        int reminderInterval = 3;
//        onView(withId(R.id.groupNameEditText)).perform(typeText(groupName));
//        onView(withId(R.id.groupIntervalEditText)).perform(typeText(String.valueOf(reminderInterval)));
//        onView(withId(R.id.saveButton)).perform(click());
//
//        onView(withText("New group added successfully")).inRoot(isToast()).check(matches(isDisplayed()));
        // TODO remove test group to clean up or use different product flavor

    }
}