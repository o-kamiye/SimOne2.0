package ng.com.tinweb.www.simone20.helper;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.ArrayList;

/**
 * Created by kamiye on 09/09/2016.
 */
public class RecyclerViewAction {

    public static ViewAction clickCallIconImageView(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    public static Matcher<View> withoutText(final String text) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                ArrayList<View> views = new ArrayList<>();
                recyclerView.findViewsWithText(views, text, View.FIND_VIEWS_WITH_TEXT);
                return views.isEmpty();
            }
        };
    }

}
