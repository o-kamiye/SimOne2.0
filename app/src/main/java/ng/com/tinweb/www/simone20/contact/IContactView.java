package ng.com.tinweb.www.simone20.contact;

import java.util.List;

import ng.com.tinweb.www.simone20.data.contact.SimOneContact;

/**
 * Created by kamiye on 28/09/2016.
 */

interface IContactView {
    void loadContacts(List<SimOneContact> contacts);
}
