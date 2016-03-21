package net.pedaling.ninjagotv.view.builder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.pedaling.ninjagotv.data.local.PreferenceHelper;
import net.pedaling.ninjagotv.util.CircleTransform;
import net.pedaling.ninjagotv.R;
import net.pedaling.ninjagotv.data.model.Video;
import net.pedaling.ninjagotv.presenter.MainPresenter;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class VideoBuilder extends Builder {

    private Video mVideo;
    private MainPresenter.VideoItemListener mListener;

    public static VideoBuilder set(Context context, View convertView, ViewGroup parent, Object... params) {
        return new VideoBuilder(context, convertView, parent, params);
    }

    public VideoBuilder(Context context, View convertView, ViewGroup parent, Object... params) {
        super(context, convertView, parent);
        mVideo = (Video) params[0];
        mListener = (MainPresenter.VideoItemListener) params[1];
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

        viewHolder.titleTV.setText(mVideo.title);
        viewHolder.minuteTV.setText(mVideo.minute);
        boolean read = PreferenceHelper.getInstance(mContext).getString(mVideo.videoId).equals("READ");
        viewHolder.readSticker.setVisibility(read ? View.VISIBLE : View.INVISIBLE);
        Picasso.with(mContext)
                .load(mVideo.thumbnailUrl)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .transform(new CircleTransform()).fit().centerCrop()
                .into(viewHolder.thumbnailIV);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAction(MainPresenter.VideoItemListener.ACTION_OPEN_VIDEO, mVideo);
            }
        });


        return mView;
    }

    class ViewHolder {
        ImageView thumbnailIV;
        TextView titleTV;
        TextView minuteTV;
        View readSticker;

        public ViewHolder(View v) {
            thumbnailIV = (ImageView) v.findViewById(R.id.iv_thumbnail);
            titleTV = (TextView) v.findViewById(R.id.tv_title);
            minuteTV = (TextView) v.findViewById(R.id.tv_minute);
            readSticker = v.findViewById(R.id.view_read);
        }
    }

}
