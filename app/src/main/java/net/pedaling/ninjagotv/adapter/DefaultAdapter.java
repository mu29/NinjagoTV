package net.pedaling.ninjagotv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.pedaling.ninjagotv.data.model.Adaptable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class DefaultAdapter<I extends Adaptable> extends BaseAdapter {

    protected DefaultListener mListener;
    protected List<I> mItems;

    public DefaultAdapter() {
        mItems = new ArrayList<>();
    }

    public DefaultAdapter(DefaultListener listener) {
        mItems = new ArrayList<>();
        this.mListener = listener;
    }

    public void addAll(List<I> items) {
        for (I item : items)
            mItems.add(item);
    }

    public void clear() {
        mItems.clear();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        Adaptable item = mItems.get(position);

        return item.buildView(context, convertView, parent, item, mListener);
    }

}