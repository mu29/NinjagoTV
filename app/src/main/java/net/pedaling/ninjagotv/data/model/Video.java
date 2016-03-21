package net.pedaling.ninjagotv.data.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class Video implements Parcelable, Adaptable {

    public int id;
    public String title;
    public String url;
    public String minute;
    public String thumbnailUrl;
    
    public Video(Parcel in) {
        
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(minute);
        dest.writeString(thumbnailUrl);
    }

    private void readFromParcel(Parcel src) {
        id = src.readInt();
        title = src.readString();
        url = src.readString();
        minute = src.readString();
        thumbnailUrl = src.readString();
    }

    @Override
    public View buildView(Context context, View convertView, ViewGroup parent, Object... params) {
        return null;
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>(){
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

}
