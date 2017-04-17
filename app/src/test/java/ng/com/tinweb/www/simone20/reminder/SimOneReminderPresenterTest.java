package ng.com.tinweb.www.simone20.reminder;

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
import static org.mockito.Mockito.when;

/**
 * Created by kamiye on 09/09/2016.
 */
public class SimOneReminderPresenterTest {

    @Mock
    private ReminderContract.View view;

    @Mock
    public SimOneReminder simOneReminder;

    @Captor
    private ArgumentCaptor<SimOneReminder.GetAllCallback> argumentCaptor;

    private ReminderPresenter reminderPresenter;

    private HashMap<String, String> mockMetaData = new HashMap<>();
    private List<SimOneReminder> mockSimOneReminders = new ArrayList<>();
    private String weeklyCount;

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        reminderPresenter = new ReminderPresenter(simOneReminder, view);
        weeklyCount = "2";
        mockMetaData.put("dueWeekly", weeklyCount);
    }

    @Test
    public void testSetWeeklyReminderCount() {
        reminderPresenter.loadReminders();
        verify(simOneReminder).getAll(anyBoolean(), argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(mockMetaData, mockSimOneReminders);
        verify(view).setWeekReminderTextView(Integer.valueOf(weeklyCount));
    }

    @Test
    public void testDeleteReminder() {
        int contact1 = 1;
        int contact2 = 2;
        when(simOneReminder.remove(contact1)).thenReturn(true);
        when(simOneReminder.remove(contact2)).thenReturn(false);

        reminderPresenter.deleteReminder(contact1);

        verify(view).showDeleteSuccessInfo();

        reminderPresenter.deleteReminder(contact2);

        verify(view).showDeleteErrorInfo();
    }

}