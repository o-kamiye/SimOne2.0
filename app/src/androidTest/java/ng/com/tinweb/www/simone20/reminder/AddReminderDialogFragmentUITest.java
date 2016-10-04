package ng.com.tinweb.www.simone20.reminder;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.com.tinweb.www.simone20.MainActivity;

import static org.junit.Assert.*;

/**
 * Created by kamiye on 04/10/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddReminderDialogFragmentUITest {

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
        // TODO add testing contact
    }

    @Test
    public void testRadioGroupSelection() {
        // TODO edit text mode test

        // TODO group spinner mode test
    }

    @Test
    public void testCancelButtonClick() {

    }

    @Test
    public void testSaveButtonClickWithoutInput() {

    }

    @Test
    public void testSaveButtonClickWithInput() {

    }

    @After
    public void tearDown() {
        // TODO remove testing contact
    }

}