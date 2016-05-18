package com.github.rxfyall.simpledemo.ui.presenter;

/**
 * Created by frubino on 5/18/16.
 */

public interface PresenterFactory<T extends Presenter<?>> {

    T createPresenter();

    String getPresenterTag();

}