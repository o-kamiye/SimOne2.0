package ng.com.tinweb.www.simone20.contact;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import ng.com.tinweb.www.simone20.data.contact.SimOneContact;

import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 29/09/2016.
 */
public class PresenterTest {

    @Mock
    private IContactView contactView;

    @Mock
    private SimOneContact simOneContact;

    private ContactPresenter presenter;

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        presenter = new ContactPresenter(contactView, simOneContact);
    }

    @Test
    public void testLoadContacts() {
        String query = "contact";
        presenter.fetchContacts(query);

        verify(contactView).loadContacts(new ArrayList<SimOneContact>());
    }

}