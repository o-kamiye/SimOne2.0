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

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 06/09/2016.
 */
public class TodayPresenterTest {

    @Mock
    private ITodayView todayView;

    @Mock
    private SimOneReminder simOneReminder;

    @Captor
    private ArgumentCaptor<SimOneReminder.GetAllCallback> getAllArgumentCaptor;

    private TodayPresenter todayPresenter;

    @Before
    public void setUpPresenter() {
        MockitoAnnotations.initMocks(this);

        todayPresenter = new TodayPresenter(simOneReminder, todayView);
    }

    @Test
    public void testLoadReminderSuccess() {
        List<SimOneReminder> simOneReminders = new ArrayList<>();
        todayPresenter.loadReminders();
        verify(simOneReminder).getAll(anyBoolean(), getAllArgumentCaptor.capture());
        getAllArgumentCaptor.getValue().onSuccess(new HashMap<String, String>(), simOneReminders);
        verify(todayView).onRemindersLoaded(simOneReminders);
    }

    @Test
    public void testLoadReminderError() {
        todayPresenter.loadReminders();
        verify(simOneReminder).getAll(anyBoolean(), getAllArgumentCaptor.capture());
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