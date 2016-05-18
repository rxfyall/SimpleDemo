package com.github.rxfyall.simpledemo.ui.presenter;

/**
 * Created by frubino on 5/18/16.
 */
public interface PresenterHolder {

    <T extends Presenter<?>> T getOrCreatePresenter(PresenterFactory<T> presenterFactory);

    void destroyPresenter(PresenterFactory<?> presenterFactory);

}