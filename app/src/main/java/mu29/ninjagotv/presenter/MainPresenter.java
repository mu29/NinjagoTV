package mu29.ninjagotv.presenter;

import mu29.ninjagotv.MvpPresenter;
import mu29.ninjagotv.MvpView;
import mu29.ninjagotv.adapter.DefaultListener;
import mu29.ninjagotv.data.model.Video;
import mu29.ninjagotv.data.remote.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class MainPresenter extends MvpPresenter<MainPresenter.MainView> {

    public void loadVideos() {
        mView.showProgressView();
        Call<List<Video>> readVideos = RestClient.getService().getVideos();
        readVideos.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                mView.displayVideos(response.body());
                mView.hideProgressView();
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                t.printStackTrace();
                mView.hideProgressView();
                mView.displayError("서버 접속이 원활하지 않습니다. 재시도합니다!");
                mView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadVideos();
                    }
                }, 1000);
            }
        });
    }

    public class VideoItemListener implements DefaultListener {

        public static final int ACTION_OPEN_VIDEO = 0;

        @Override
        public void onAction(int actionId, Object... params) {
            switch (actionId) {
                case ACTION_OPEN_VIDEO:
                    Video video = (Video) params[0];
                    mView.openFullAd(video.videoId);
                    break;
            }
        }

    }

    public VideoItemListener getVideoItemListener() {
        return new VideoItemListener();
    }

    public interface MainView extends MvpView {
        void showProgressView();
        void hideProgressView();
        void displayVideos(List<Video> videos);
        void displayError(String message);
        void openFullAd(String videoId);
        void postDelayed(Runnable runnable, int delay);
    }

}
