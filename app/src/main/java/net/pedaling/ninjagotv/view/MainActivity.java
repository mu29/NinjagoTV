package net.pedaling.ninjagotv.view;

import android.widget.ListView;

import net.pedaling.ninjagotv.MvpActivity;
import net.pedaling.ninjagotv.R;
import net.pedaling.ninjagotv.adapter.DefaultAdapter;
import net.pedaling.ninjagotv.data.model.Video;
import net.pedaling.ninjagotv.presenter.MainPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by InJung on 2016. 3. 21..
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends MvpActivity<MainPresenter> implements MainPresenter.MainView {

    @ViewById(R.id.lv_videos) ListView videoLV;

    @AfterViews
    protected void setView() {
        DefaultAdapter<Video> adapter = new DefaultAdapter<>();
        List<Video> videoList = new ArrayList<>();
        videoList.add(new Video(1, "레고® 닌자고 1화 스네이크군단의 부활", "9WHv9Nf9WAA", "21:50", ""));
        adapter.addAll(videoList);

        videoLV.setAdapter(adapter);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

}