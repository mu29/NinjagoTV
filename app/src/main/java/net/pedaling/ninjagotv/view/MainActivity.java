package net.pedaling.ninjagotv.view;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import net.daum.adam.publisher.AdInterstitial;
import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.impl.AdError;
import net.pedaling.ninjagotv.MvpActivity;
import net.pedaling.ninjagotv.R;
import net.pedaling.ninjagotv.adapter.DefaultAdapter;
import net.pedaling.ninjagotv.data.local.PreferenceHelper;
import net.pedaling.ninjagotv.data.model.Video;
import net.pedaling.ninjagotv.presenter.MainPresenter;
import net.pedaling.ninjagotv.util.NinjaGoUtils;

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
    private AdInterstitial mFullAd;
    @ViewById(R.id.view_progress) View progressView;
    @ViewById(R.id.lv_videos) ListView videoLV;
    @ViewById(R.id.view_ad) AdView adView;

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
        mFullAd.loadAd();
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

        if (!canResolveIntent(intent))
            YouTubeInitializationResult.SERVICE_MISSING.getErrorDialog(this, REQ_RESOLVE_SERVICE_MISSING).show();

        startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
    }

    // 전면 광고 세팅
    private void initFullAd() {
        mFullAd = new AdInterstitial(this);
        mFullAd.setClientId(NinjaGoUtils.FULL_AD_KEY);
        mFullAd.setOnAdFailedListener(new AdView.OnAdFailedListener() {
            @Override
            public void OnAdFailed(AdError error, String errorMessage) {
                openVideoActivity(mVideoId);
            }
        });
        mFullAd.setOnAdClosedListener (new AdView.OnAdClosedListener() {
            @Override
            public void OnAdClosed() {
                openVideoActivity(mVideoId);
            }
        });
    }

    // 바 광고 세팅
    private void initBarAd() {
        adView.setAdCache(true);
        adView.setAnimationType(AdView.AnimationType.NONE);
        adView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (adView != null) {
            adView.destroy();
            adView = null;
        }
    }

}
