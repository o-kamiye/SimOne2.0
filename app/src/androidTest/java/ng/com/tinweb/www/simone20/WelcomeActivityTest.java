package ng.com.tinweb.www.simone20;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by kamiye on 04/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class WelcomeActivityTest {

    @Rule
    public IntentsTestRule<WelcomeActivity> activityTestRule = new IntentsTestRule<>(WelcomeActivity.class);

    @Test
    public void testButton_disappearsAfterClick() {
        onView(withId(R.id.button))
                .perform(click());

        onView(withId(R.id.button))
                .check(doesNotExist());
    }

    @Test
    public void testButton_launchNewActivity() {
        onView(withId(R.id.button))
                .perform(click());

        intended(isInternal());
    }

}