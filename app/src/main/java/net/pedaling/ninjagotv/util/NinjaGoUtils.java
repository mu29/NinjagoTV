package net.pedaling.ninjagotv.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class NinjaGoUtils {

    // DEBUG : AIzaSyCIrjD6QDva3V-RNfRJ7mwSGgcpuNDK7nU
    // PRODUCTION : AIzaSyAbrZZnYA3eo1qH9XLfhhsquLtzYc7gN18
    public static final String YOUTUBE_KEY = "AIzaSyCIrjD6QDva3V-RNfRJ7mwSGgcpuNDK7nU";
    public static final String FULL_AD_KEY = "DAN-sondxxeo5nj8";

    public static float getDP(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

}