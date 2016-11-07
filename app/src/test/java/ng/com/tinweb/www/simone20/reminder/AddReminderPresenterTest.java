package ng.com.tinweb.www.simone20.reminder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ng.com.tinweb.www.simone20.data.reminder.Reminder;

import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 04/10/2016.
 */
public class AddReminderPresenterTest {

    @Mock
    public IReminderView.IReminderFragmentView reminderFragmentView;

    @Mock
    public Reminder reminder;

    @Captor
    private ArgumentCaptor<Reminder.ActionCallback> callbackArgumentCaptor;

    private ReminderPresenter.AddReminderPresenter addReminderPresenter;

    private String reminderGroup;
    private int interval;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);

        addReminderPresenter = new ReminderPresenter.AddReminderPresenter(reminderFragmentView,
                reminder);
        reminderGroup = "family";
        interval = 1;
    }

    @Test
    public void testAddReminderSuccess() {
        addReminderPresenter.addReminder(reminderGroup, interval);

        verify(reminder).setContactGroup(reminderGroup);
        verify(reminder).setInterval(interval);
        verify(reminder).create(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onSuccess();
        verify(reminderFragmentView).onAddReminderSuccess();
    }

    @Test
    public void testAddReminderError() {
        addReminderPresenter.addReminder(reminderGroup, interval);

        verify(reminder).setContactGroup(reminderGroup);
        verify(reminder).setInterval(interval);
        verify(reminder).create(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onError(13);
        verify(reminderFragmentView).onAddReminderError("Oops! Please try setting reminder again");
    }

}