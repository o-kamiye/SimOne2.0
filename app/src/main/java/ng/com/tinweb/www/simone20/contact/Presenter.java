package ng.com.tinweb.www.simone20.contact;

import java.lang.ref.WeakReference;

/**
 * Created by kamiye on 28/09/2016.
 */

public class Presenter implements IContactPresenter {

    private WeakReference<IContactView> contactView;

    public Presenter(IContactView contactView) {
        this.contactView = new WeakReference<>(contactView);
    }
}
