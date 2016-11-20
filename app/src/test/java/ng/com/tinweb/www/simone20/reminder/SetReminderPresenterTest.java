package ng.com.tinweb.www.simone20.reminder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ng.com.tinweb.www.simone20.data.reminder.Reminder;

import static org.mockito.Matchers.anyBoolean;
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
    private boolean isUpdate;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);

        setReminderPresenter = new ReminderPresenter.SetReminderPresenter(reminderFragmentView,
                reminder);
        reminderGroup = "family";
        interval = 1;
        isUpdate = false;
    }

    @Test
    public void testSetReminderSuccess() {
        setReminderPresenter.setReminder(reminderGroup, interval, isUpdate);

        verify(reminder).setContactGroup(reminderGroup);
        verify(reminder).setInterval(interval);
        verify(reminder).set(anyBoolean(), callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onSuccess();
        verify(reminderFragmentView).onSetReminderSuccess();
    }

    @Test
    public void testSetReminderError() {
        setReminderPresenter.setReminder(reminderGroup, interval, isUpdate);

        verify(reminder).setContactGroup(reminderGroup);
        verify(reminder).setInterval(interval);
        verify(reminder).set(anyBoolean(), callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onError(13);
        verify(reminderFragmentView).onSetReminderError("Oops! Please try setting reminder again");
    }

}