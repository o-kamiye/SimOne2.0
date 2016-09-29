package ng.com.tinweb.www.simone20.contact;

import java.lang.ref.WeakReference;
import java.util.List;

import ng.com.tinweb.www.simone20.data.contact.SimOneContact;

/**
 * Created by kamiye on 28/09/2016.
 */

public class Presenter implements IContactPresenter {

    private WeakReference<IContactView> contactView;
    private SimOneContact contact;

    public Presenter(IContactView contactView, SimOneContact contact) {
        this.contactView = new WeakReference<>(contactView);
        this.contact = contact;
    }

    @Override
    public void fetchContacts(String searchQuery) {
        List<SimOneContact> contacts = contact.getList(searchQuery);
        if (contactView.get() != null) {
            contactView.get().loadContacts(contacts);
        }
    }
}
