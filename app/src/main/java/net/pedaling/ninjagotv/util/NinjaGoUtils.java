package net.pedaling.ninjagotv.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class NinjaGoUtils {

    public static float getDP(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

}
