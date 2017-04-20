package ng.com.tinweb.www.simone20.contact;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import ng.com.tinweb.www.simone20.data.contact.SimOneContact;

import static org.mockito.Mockito.verify;

/**
 * ContactPresenterTest - Test class for {@link ContactPresenter}
 */
public class ContactPresenterTest {

    @Mock
    private DialogFragmentContract.View view;

    @Mock
    private SimOneContact contact;

    private ContactPresenter presenter;

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        presenter = new ContactPresenter(view, contact);
    }

    @Test
    public void testLoadContacts() {
        String query = "contact";
        presenter.fetchContacts(query);

        verify(view).loadContacts(new ArrayList<SimOneContact>());
    }

}