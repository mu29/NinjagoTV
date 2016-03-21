package net.pedaling.ninjagotv.data.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by InJung on 2016. 3. 21..
 */

public interface Adaptable {

    View buildView(Context context, View convertView, ViewGroup parent, Object... params);

}