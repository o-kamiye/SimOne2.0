package ng.com.tinweb.www.simone20.reminder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ng.com.tinweb.www.simone20.data.reminder.Reminder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kamiye on 04/10/2016.
 */
public class AddReminderPresenterTest {

    @Mock
    public IReminderView.IReminderFragmentView reminderFragmentView;

    @Mock
    public Reminder reminder;

    private ReminderPresenter.AddReminderPresenter addReminderPresenter;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);

        addReminderPresenter = new ReminderPresenter.AddReminderPresenter(reminderFragmentView,
                reminder);
    }

    @Test
    public void testAddReminderSuccess() {
        String reminderGroup = "family";
        int interval = 1;
        when(reminder.create()).thenReturn(true);

        addReminderPresenter.addReminder(reminderGroup, interval);

        verify(reminder).setContactGroup(reminderGroup);
        verify(reminder).setInterval(interval);
        verify(reminder).create();
        verify(reminderFragmentView).onAddReminderSuccess();
    }

    @Test
    public void testAddReminderError() {
        String reminderGroup = "family";
        int interval = 1;
        when(reminder.create()).thenReturn(false);

        addReminderPresenter.addReminder(reminderGroup, interval);

        verify(reminder).setContactGroup(reminderGroup);
        verify(reminder).setInterval(interval);
        verify(reminder).create();
        verify(reminderFragmentView).onAddReminderError("Oops! Please try setting reminder again");
    }



}