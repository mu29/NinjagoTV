package net.pedaling.ninjagotv.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import net.pedaling.ninjagotv.MvpActivity;
import net.pedaling.ninjagotv.R;
import net.pedaling.ninjagotv.adapter.DefaultAdapter;
import net.pedaling.ninjagotv.data.model.Video;
import net.pedaling.ninjagotv.presenter.MainPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by InJung on 2016. 3. 21..
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends MvpActivity<MainPresenter> implements MainPresenter.MainView {

    private DefaultAdapter<Video> mAdapter;
    @ViewById(R.id.view_progress) View progressView;
    @ViewById(R.id.lv_videos) ListView videoLV;

    @AfterViews
    protected void setView() {
        mAdapter = new DefaultAdapter<>(mPresenter.getVideoItemListener());
        videoLV.setAdapter(mAdapter);
        mPresenter.loadVideos();
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
    public void openVideoActivity(String key, String url) {
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                this, key, url, 0, true, false);
        startActivity(intent);
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

}
