package ng.com.tinweb.www.simone20.today;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 06/09/2016.
 */
public class TodayPresenterTest {

    @Mock
    private ITodayView todayView;

    private TodayPresenter todayPresenter;

    @Before
    public void setUpPresenter() {
        MockitoAnnotations.initMocks(this);

        todayPresenter = new TodayPresenter(todayView);
    }

    @Test
    public void testSetReminderCount() {
        todayPresenter.setReminderCount();

        verify(todayView).setTotalReminders(0);
    }

    @Test
    public void testCallContact() {
        String contactName = "Test Contact";
        todayPresenter.callContact(contactName);

        verify(todayView).callContact(contactName);
    }

}