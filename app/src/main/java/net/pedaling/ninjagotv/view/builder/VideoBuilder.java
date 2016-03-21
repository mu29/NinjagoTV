package net.pedaling.ninjagotv.view.builder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.pedaling.ninjagotv.R;
import net.pedaling.ninjagotv.data.model.Video;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class VideoBuilder extends Builder {

    private Video mVideo;

    public static VideoBuilder set(Context context, View convertView, ViewGroup parent, Object... params) {
        return new VideoBuilder(context, convertView, parent, params);
    }

    public VideoBuilder(Context context, View convertView, ViewGroup parent, Object... params) {
        super(context, convertView, parent);
        mVideo = (Video) params[0];
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public View buildView() {
        ViewHolder viewHolder;

        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.item_video, mParent, false);
            viewHolder = new ViewHolder(mView);
            mView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) mView.getTag();
        }

        viewHolder.titleTV.setTag(mVideo.title);
        viewHolder.minuteTV.setTag(mVideo.minute);

        return mView;
    }

    class ViewHolder {
        ImageView thumbnailIV;
        TextView titleTV;
        TextView minuteTV;

        public ViewHolder(View v) {
            thumbnailIV = (ImageView) v.findViewById(R.id.iv_thumbnail);
            titleTV = (TextView) v.findViewById(R.id.tv_title);
            minuteTV = (TextView) v.findViewById(R.id.tv_minute);
        }
    }

}
