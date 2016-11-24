package ng.com.tinweb.www.simone20.data.contact;


import android.support.v4.util.LongSparseArray;

import java.util.List;

/**
 * Created by kamiye on 28/09/2016.
 */

interface DataStore {

    boolean save(LongSparseArray<SimOneContact> contacts);
    List<SimOneContact> search(String searchQuery);
    boolean saveOne(String contactName);
    boolean deleteOne(String contactName);
}
