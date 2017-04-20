package ng.com.tinweb.www.simone20.contact;

import java.util.List;

import ng.com.tinweb.www.simone20.data.contact.SimOneContact;

/**
 * DialogFragmentContract - Contract class to manage MVP
 * for contact package dialog fragment
 */

class DialogFragmentContract {

    interface View {
        void loadContacts(List<SimOneContact> contacts);
    }

    interface Presenter {
        void fetchContacts(String searchQuery);
    }
}
