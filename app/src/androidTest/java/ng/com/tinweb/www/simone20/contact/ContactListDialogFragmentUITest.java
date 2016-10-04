package ng.com.tinweb.www.simone20.contact;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.simone20.MainActivity;
import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.helper.RecyclerViewAction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by kamiye on 29/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ContactListDialogFragmentUITest {

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
    public void testSearchResultFragmentVisible() {
        String searchQuery = "contact name";
        onView(withId(R.id.action_search)).perform(click());

        onView(allOf(isAssignableFrom(EditText.class), withHint(R.string.action_search))).
                perform(typeText(searchQuery));

        onView(allOf(isAssignableFrom(EditText.class), withHint(R.string.action_search))).
                perform(pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withId(R.id.content_contact_search)).check(matches(isDisplayed()));

        onView(withId(android.R.id.title)).check(matches(withText(containsString(searchQuery))));

    }

    /**
     * Note that this method is subject to being tested by using
     * an existing contact as a search param. Using a param that
     * won't bring a result might cause this test to fail!
     *
     * Ideally, a contact should be fetched first, then used to
     * make the queries to as to guarantee perfect functionality
     */
    @Test
    public void testQueryItemClick() {
        String contact = "Contact";
        onView(withId(R.id.action_search)).perform(click());

        onView(allOf(isAssignableFrom(EditText.class), withHint(R.string.action_search))).
                perform(typeText(contact));

        onView(allOf(isAssignableFrom(EditText.class), withHint(R.string.action_search))).
                perform(pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withId(R.id.contactListRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        RecyclerViewAction.clickCallIconImageView(R.id.addIconImageView)));

        onView(withId(R.id.content_reminder_add)).check(matches(isDisplayed()));

        onView(withId(android.R.id.title)).check(matches(withText(containsString(contact))));
    }

    // TODO test contact removed when user adds reminder

    // TODO test database addition and removal
}