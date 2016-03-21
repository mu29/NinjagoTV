package net.pedaling.ninjagotv;

/**
 * Created by InJung on 2016. 3. 21..
 */
public class MvpPresenter<V extends MvpView> {

    protected V mView;

    public MvpPresenter() {

    }

    public void attachView(V view) {
        mView = view;
        afterAttach();
    }

    public void detachView() {
        mView = null;
    }

    protected void afterAttach() {

    }

}
