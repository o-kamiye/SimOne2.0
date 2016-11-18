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
public class SetReminderPresenterTest {

    @Mock
    public IReminderView.IReminderFragmentView reminderFragmentView;

    @Mock
    public Reminder reminder;

    @Captor
    private ArgumentCaptor<Reminder.ActionCallback> callbackArgumentCaptor;

    private ReminderPresenter.SetReminderPresenter setReminderPresenter;

    private String reminderGroup;
    private int interval;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);

        setReminderPresenter = new ReminderPresenter.SetReminderPresenter(reminderFragmentView,
                reminder);
        reminderGroup = "family";
        interval = 1;
    }

    @Test
    public void testAddReminderSuccess() {
        setReminderPresenter.setReminder(reminderGroup, interval);

        verify(reminder).setContactGroup(reminderGroup);
        verify(reminder).setInterval(interval);
        verify(reminder).set(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onSuccess();
        verify(reminderFragmentView).onSetReminderSuccess();
    }

    @Test
    public void testAddReminderError() {
        setReminderPresenter.setReminder(reminderGroup, interval);

        verify(reminder).setContactGroup(reminderGroup);
        verify(reminder).setInterval(interval);
        verify(reminder).set(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onError(13);
        verify(reminderFragmentView).onSetReminderError("Oops! Please try setting reminder again");
    }

}