package mu29.ninjagotv.view;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import net.daum.adam.publisher.AdInterstitial;
import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.impl.AdError;
import mu29.ninjagotv.MvpActivity;
import mu29.ninjagotv.R;
import mu29.ninjagotv.adapter.DefaultAdapter;
import mu29.ninjagotv.data.local.PreferenceHelper;
import mu29.ninjagotv.data.model.Video;
import mu29.ninjagotv.presenter.MainPresenter;
import mu29.ninjagotv.util.NinjaGoUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by InJung on 2016. 3. 21..
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends MvpActivity<MainPresenter> implements MainPresenter.MainView {

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

    private DefaultAdapter<Video> mAdapter;
    private static String mVideoId;
    private InterstitialAd mGoogleFullAd;
    @ViewById(R.id.view_progress) View progressView;
    @ViewById(R.id.lv_videos) ListView videoLV;
    @ViewById(R.id.ad_bar_google) com.google.android.gms.ads.AdView googleAdBar;

    @AfterViews
    protected void setView() {
        mAdapter = new DefaultAdapter<>(mPresenter.getVideoItemListener());
        videoLV.setAdapter(mAdapter);
        mPresenter.loadVideos();
        initBarAd();
        initFullAd();
    }

    @Override
    public void displayVideos(List<Video> videos) {
        mAdapter.addAll(videos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayError(String message) {
        Snackbar bar =  Snackbar.make(getCoordinatorView(), message, Snackbar.LENGTH_SHORT);

        View barView = bar.getView();
        barView.setBackgroundColor(getResources().getColor(R.color.deep_orange));
        TextView barText = (TextView) barView.findViewById(android.support.design.R.id.snackbar_text);
        barText.setTextColor(Color.WHITE);

        bar.show();
    }

    @Override
    public void openFullAd(String videoId) {
        mVideoId = videoId;
        if (mGoogleFullAd.isLoaded()) {
            mGoogleFullAd.show();
        } else {
            if (!mVideoId.isEmpty())
                openVideoActivity(mVideoId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_START_STANDALONE_PLAYER && resultCode != RESULT_OK) {
            YouTubeInitializationResult errorReason = YouTubeStandalonePlayer.getReturnedInitializationResult(data);
            displayError(errorReason.toString());
        } else {
            PreferenceHelper.getInstance(getApplicationContext()).setString(mVideoId, "READ");
            mAdapter.notifyDataSetChanged();
        }

        mVideoId = "";
        mGoogleFullAd.show();
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showProgressView() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressView() {
        progressView.setVisibility(View.GONE);
    }

    private void openVideoActivity(String videoId) {
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(this, NinjaGoUtils.YOUTUBE_KEY, videoId, 0, true, false);
        if (intent == null)
            return;

        if (!canResolveIntent(intent) || !YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(getApplicationContext()).equals(YouTubeInitializationResult.SUCCESS)) {
            YouTubeInitializationResult.SERVICE_MISSING.getErrorDialog(this, REQ_RESOLVE_SERVICE_MISSING).show();
            return;
        }

        startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
    }

    // 바 광고 세팅
    private void initBarAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        googleAdBar.loadAd(adRequest);
        googleAdBar.setVisibility(View.VISIBLE);
    }

    // 전면 광고 세팅
    private void initFullAd() {
        mGoogleFullAd = new InterstitialAd(this);
        mGoogleFullAd.setAdUnitId(getResources().getString(R.string.google_full_ad));
        requestGoogleFullAd();

        mGoogleFullAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestGoogleFullAd();
                if (!mVideoId.isEmpty())
                    openVideoActivity(mVideoId);
            }
        });

    }

    private void requestGoogleFullAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mGoogleFullAd.loadAd(adRequest);
    }

    @Override
    public void postDelayed(Runnable runnable, int delay) {
        videoLV.postDelayed(runnable, delay);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
