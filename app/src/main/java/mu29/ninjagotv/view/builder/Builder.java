package mu29.ninjagotv.view.builder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by InJung on 2016. 3. 21..
 */
public abstract class Builder {

    protected View mView;
    protected ViewGroup mParent;
    protected Context mContext;

    public Builder(Context context, View convertView, ViewGroup parent) {
        mContext = context;
        mView = convertView;
        mParent = parent;
    }

    public abstract int getViewType();

    public abstract View buildView();

}
