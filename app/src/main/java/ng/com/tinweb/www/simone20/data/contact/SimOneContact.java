package ng.com.tinweb.www.simone20.data.contact;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.util.LongSparseArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ng.com.tinweb.www.simone20.SimOne;


/**
 * Created by kamiye on 28/09/2016.
 */

public class SimOneContact implements Serializable {
    protected int id;
    protected long contactId;
    protected String name;
    protected List<String> phones;

    private Context context;
    private DataStore dataStore;

    public SimOneContact() {
        context = SimOne.getContext();
    }

    public SimOneContact(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public SimOneContact(long contactId, String name) {
        super();
        this.contactId = contactId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public long getContactId() {
        return contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhones() {
        return phones.toString();
    }

    public void addPhone(String number) {
        if (phones == null) {
            phones = new ArrayList<>();
        }
        phones.add(number);
    }

    public List<SimOneContact> getList(String searchQuery) {
        initialiseDataStore();
        return dataStore.search(searchQuery);
    }

    public void syncAll(SyncCallback callback) {
        initialiseDataStore();
        Cursor cursor = getContactListCursor();

        if (cursor != null) {
            LongSparseArray<SimOneContact> contacts = generateContactsMap(cursor);
            if (dataStore.save(contacts)) {
                callback.onSuccess();
            }
            else {
                callback.onError("Contacts not successfully synchronised");
            }
        }
    }

    private LongSparseArray<SimOneContact> generateContactsMap(Cursor cursor) {
        LongSparseArray<SimOneContact> contacts = new LongSparseArray<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(ContactsContract.Data.CONTACT_ID)
            );
            String contactName = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            );
            SimOneContact simOneContact = contacts.get(id);
            if (simOneContact == null) {
                simOneContact = new SimOneContact(id, contactName);
                contacts.put(id, simOneContact);
            }
            String phoneNumber = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Contactables.DATA)
            );
            String mimeType = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactsContract.Data.MIMETYPE)
            );
            if (mimeType.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                simOneContact.addPhone(phoneNumber);
            }
        }
        cursor.close();
        return contacts;
    }

    private Cursor getContactListCursor() {

        String[] projection = {
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                ContactsContract.CommonDataKinds.Contactables.DATA,
                ContactsContract.CommonDataKinds.Contactables.TYPE,
        };
        String selection = ContactsContract.Data.MIMETYPE + " in (?)";
        String[] selectionArgs = {
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
        };
        String sortOrder = ContactsContract.Contacts.SORT_KEY_ALTERNATIVE;

        Uri uri = ContactsContract.Data.CONTENT_URI;

        return context.getContentResolver().query(uri, projection,
                selection, selectionArgs, sortOrder);
    }

    private void initialiseDataStore() {
        if (dataStore == null) {
            dataStore = new ContactDbHelper(context);
        }
    }

    interface SyncCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
}
