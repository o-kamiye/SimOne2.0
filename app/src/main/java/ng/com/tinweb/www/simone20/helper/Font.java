package ng.com.tinweb.www.simone20.helper;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by kamiye on 30/08/2016.
 */
public class Font {

    private static final String APPNAME = "AppName";

    private static Typeface appNameFont;

    @BindingAdapter("font")
    public static void setFont(TextView textView, String fontIdentifier) {

        switch (fontIdentifier) {
            case APPNAME:
                setAppNameFont(textView);
                break;
        }
    }

    private static void setAppNameFont(TextView textView) {
        if (appNameFont == null) {
            appNameFont = Typeface.createFromAsset(textView.getContext()
                    .getAssets(), "fonts/phosphate.ttf");
        }
        textView.setTypeface(appNameFont);
    }
}
