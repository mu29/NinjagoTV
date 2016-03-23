package mu29.ninjagotv.data.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class PreferenceHelper {

    private Context mContext;
    private static PreferenceHelper preferenceHelper;

    public PreferenceHelper(Context context) {
        this.mContext = context;
    }

    public static PreferenceHelper getInstance(Context context) {
        if (preferenceHelper == null)
            preferenceHelper = new PreferenceHelper(context);

        return preferenceHelper;
    }

    // 값 불러오기
    public String getString(String key) {
        SharedPreferences pref = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    // 값 저장하기
    public void setString(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // 값 지우기
    public void removePreferences(String key) {
        SharedPreferences pref = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

}
