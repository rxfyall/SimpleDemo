package com.github.rxfyall.simpledemo.ui.presenter;

import android.os.Bundle;

import com.github.rxfyall.simpledemo.ui.viewmodel.MvpView;

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

    void saveState(Bundle state);

    void restoreState(Bundle state);

    void onDestroy();

}