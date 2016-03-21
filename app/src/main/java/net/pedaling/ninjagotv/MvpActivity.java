package net.pedaling.ninjagotv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by InJung on 2016. 3. 21..
 */
@SuppressWarnings("all")
public abstract class MvpActivity<P extends MvpPresenter> extends AppCompatActivity implements MvpView {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract P createPresenter();

    public P getPresenter() {
        return mPresenter;
    }

//    public View getCoordinatorView() {
//        return findViewById(R.id.content_wrapper);
//    }

}
