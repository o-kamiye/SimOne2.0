package ng.com.tinweb.www.simone20.today;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.Reminder;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 06/09/2016.
 */
public class TodayPresenterTest {

    @Mock
    private ITodayView todayView;

    @Mock
    private Reminder reminder;

    @Captor
    private ArgumentCaptor<Reminder.GetAllCallback> getAllArgumentCaptor;

    private TodayPresenter todayPresenter;

    @Before
    public void setUpPresenter() {
        MockitoAnnotations.initMocks(this);

        todayPresenter = new TodayPresenter(reminder, todayView);
    }

    @Test
    public void testLoadReminderSuccess() {
        List<Reminder> reminders = new ArrayList<>();
        todayPresenter.loadReminders();
        verify(reminder).getAll(anyBoolean(), getAllArgumentCaptor.capture());
        getAllArgumentCaptor.getValue().onSuccess(new HashMap<String, String>(), reminders);
        verify(todayView).onRemindersLoaded(reminders);
    }

    @Test
    public void testLoadReminderError() {
        todayPresenter.loadReminders();
        verify(reminder).getAll(anyBoolean(), getAllArgumentCaptor.capture());
        getAllArgumentCaptor.getValue().onError(2);
        verify(todayView).onReminderLoadingError("An unknown error occurred");
    }

    @Test
    public void testCallContact() {
        String contactName = "Test Contact";
        todayPresenter.callContact(contactName);

        verify(todayView).callContact(contactName);
    }

}