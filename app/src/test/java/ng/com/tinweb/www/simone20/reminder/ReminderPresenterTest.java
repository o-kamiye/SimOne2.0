package ng.com.tinweb.www.simone20.reminder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 09/09/2016.
 */
public class ReminderPresenterTest {

    @Mock
    private IReminderView reminderView;

    private ReminderPresenter reminderPresenter;

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        reminderPresenter = new ReminderPresenter(reminderView);
    }


    @Test
    public void testSetWeeklyReminderCount() {
        reminderPresenter.setWeeklyReminderCount();

        verify(reminderView).setWeeklyReminders(2);
    }

    @Test
    public void testEditReminder() {
        String contactId = "23";
        reminderPresenter.editReminder(contactId);

        verify(reminderView).showEditReminderPopUp();
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