package com.github.rxfyall.simpledemo.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.rxfyall.simpledemo.data.event.ErrorEvent;
import com.github.rxfyall.simpledemo.ui.presenter.BasePresenter;
import com.github.rxfyall.simpledemo.ui.viewmodel.MvpView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by frubino on 5/18/16.
 */
public abstract class BaseFragment extends Fragment {

    protected EventBus bus = EventBus.getDefault();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getPresenter() != null)
            getPresenter().attachView(getMvpView());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!bus.isRegistered(this)) {
            bus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bus.isRegistered(this)) {
            bus.unregister(this);
        }
    }

    @Override
    public void onDestroy() {
        if (getPresenter() != null)
            getPresenter().detachView();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent event) {
        //lunch dialog error
    }

    protected abstract int getLayout();

    @Nullable
    protected abstract BasePresenter getPresenter();


    protected abstract MvpView getMvpView();

}

