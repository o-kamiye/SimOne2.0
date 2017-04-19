package ng.com.tinweb.www.simone20.contact;

import java.lang.ref.WeakReference;
import java.util.List;

import ng.com.tinweb.www.simone20.data.contact.SimOneContact;

/**
 * Created by kamiye on 28/09/2016.
 */

class ContactPresenter implements DialogFragmentContract.Presenter {

    private WeakReference<DialogFragmentContract.View> view;
    private SimOneContact contact;

    ContactPresenter(DialogFragmentContract.View view, SimOneContact contact) {
        this.view = new WeakReference<>(view);
        this.contact = contact;
    }

    @Override
    public void fetchContacts(String searchQuery) {
        List<SimOneContact> contacts = contact.getList(searchQuery);
        if (view.get() != null) {
            view.get().loadContacts(contacts);
        }
    }
}
