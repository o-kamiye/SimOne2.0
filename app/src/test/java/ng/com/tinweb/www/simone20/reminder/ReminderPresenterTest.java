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

import ng.com.tinweb.www.simone20.data.reminder.Reminder;
import ng.com.tinweb.www.simone20.helper.Injection;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 09/09/2016.
 */
public class ReminderPresenterTest {

    @Mock
    private IReminderView reminderView;

    @Mock
    public Reminder reminder;

    @Captor
    private ArgumentCaptor<Reminder.GetAllCallback> argumentCaptor;

    private ReminderPresenter reminderPresenter;

    private HashMap<String, String> mockMetaData = new HashMap<>();
    private List<Reminder> mockReminders = new ArrayList<>();
    private String weeklyCount;

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        reminderPresenter = new ReminderPresenter(reminder, reminderView);
        weeklyCount = "2";
        mockMetaData.put("dueWeekly", weeklyCount);
    }

    @Test
    public void testSetWeeklyReminderCount() {
        reminderPresenter.loadReminders();
        verify(reminder).getAll(anyBoolean(), argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(mockMetaData, mockReminders);
        verify(reminderView).setWeekReminderTextView(Integer.valueOf(weeklyCount));
    }

    @Test
    public void testDeleteReminder() {
        String contactId = "23";
        reminderPresenter.deleteReminder(contactId);

        // TODO success call and error call should be separated to separate tests
        // todo(cont) when implementation is complete
        verify(reminderView).showDeleteSuccessInfo();
        verify(reminderView).showDeleteErrorInfo();
    }

}