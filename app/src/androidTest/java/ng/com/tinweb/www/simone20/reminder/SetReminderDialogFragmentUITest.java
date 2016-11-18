package ng.com.tinweb.www.simone20.reminder;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.widget.EditText;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.simone20.MainActivity;
import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.helper.ContactHelper;
import ng.com.tinweb.www.simone20.helper.RecyclerViewAction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ng.com.tinweb.www.simone20.helper.ToastMatcher.isToast;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

/**
 * Created by kamiye on 04/10/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SetReminderDialogFragmentUITest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private static String testContactName = "test_contact";

    @Before
    public void setUp() {
        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().recreate();
            }
        });

        ContactHelper.insertSimOneContact(testContactName);
        // Do the search process in the before block
        // so that each test can contain only relevant test cases
        onView(withId(R.id.action_search)).perform(click());

        onView(allOf(isAssignableFrom(EditText.class), withHint(R.string.action_search))).
                perform(typeText(testContactName));

        onView(allOf(isAssignableFrom(EditText.class), withHint(R.string.action_search))).
                perform(pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withId(R.id.contactListRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        RecyclerViewAction.clickCallIconImageView(R.id.addIconImageView)));
    }

    @Test
    public void testRadioGroupSelection() {

        // Default view (Edit text mode)
        onView(withId(R.id.groupListSpinner)).check(matches(not(isDisplayed())));
        onView(withId(R.id.intervalEditText)).check(matches(isDisplayed()));

        // Group list selected
        onView(withId(R.id.groupRadioButton)).perform(click());
        onView(withId(R.id.intervalEditText)).check(matches(not(isDisplayed())));
        onView(withId(R.id.groupListSpinner)).check(matches(isDisplayed()));
    }

    @Test
    public void testCancelButtonClick() {
        onView(withId(R.id.cancelButton)).perform(click());

        onView(withId(R.id.content_reminder_add)).check(doesNotExist());
    }

    @Test
    public void testSaveButtonClickWithoutInput() {
        onView(withId(R.id.saveButton)).perform(click());

        onView(withId(R.id.inputErrorTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void testSaveButtonClickWithInput() {
        int reminderInterval = 3;
        onView(withId(R.id.intervalEditText)).perform(typeText(String.valueOf(reminderInterval)));
        onView(withId(R.id.saveButton)).perform(click());

        onView(withText(activityTestRule.getActivity().getString(R.string.add_reminder_success_toast,
                testContactName))).inRoot(isToast()).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() {
        ContactHelper.deleteSimOneContact(testContactName);
    }

}