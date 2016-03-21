package net.pedaling.ninjagotv.presenter;

import net.pedaling.ninjagotv.MvpPresenter;
import net.pedaling.ninjagotv.MvpView;
import net.pedaling.ninjagotv.adapter.DefaultListener;
import net.pedaling.ninjagotv.data.model.Video;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class MainPresenter extends MvpPresenter<MainPresenter.MainView> {

    public class VideoItemListener implements DefaultListener {

        public static final int ACTION_OPEN_VIDEO = 0;

        @Override
        public void onAction(int actionId, Object... params) {
            switch (actionId) {
                case ACTION_OPEN_VIDEO:
                    Video video = (Video) params[0];
                    mView.openVideoActivity("AIzaSyAj-se5kZ7n5100yAa6Wzj-9Q0EfH_pZkk", video.url);
                    break;
            }
        }

    }

    public VideoItemListener getVideoItemListener() {
        return new VideoItemListener();
    }

    public interface MainView extends MvpView {
        void openVideoActivity(String key, String url);
    }

}
