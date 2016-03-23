package mu29.ninjagotv.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class NinjaGoUtils {

    // DEBUG : AIzaSyCIrjD6QDva3V-RNfRJ7mwSGgcpuNDK7nU
    // PRODUCTION : AIzaSyBg6bvz3co156Xt11NM9Lcd1zhpGTOuUKo
    public static final String YOUTUBE_KEY = "AIzaSyBg6bvz3co156Xt11NM9Lcd1zhpGTOuUKo";
    public static final String FULL_AD_KEY = "DAN-s101mex41wdy";

    public static float getDP(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

}
