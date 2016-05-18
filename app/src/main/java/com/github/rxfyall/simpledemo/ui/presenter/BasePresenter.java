package com.github.rxfyall.simpledemo.ui.presenter;

import android.os.Bundle;

import com.github.rxfyall.simpledemo.ui.viewmodel.MvpView;

import java.lang.ref.WeakReference;

import rx.subscriptions.CompositeSubscription;

public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private WeakReference<T> view;

    protected CompositeSubscription compositeSubscription;


    public BasePresenter() {
        this.compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void attachView(T view) {
        this.view = new WeakReference<>(view);
    }

    public WeakReference<T> getView() {
        return view;
    }

    @Override
    public void detachView() {
        this.compositeSubscription.clear();
        if (!this.compositeSubscription.isUnsubscribed())
            this.compositeSubscription.unsubscribe();
    }

    @Override
    public void saveState(Bundle state) {

    }

    @Override
    public void restoreState(Bundle state) {

    }

    @Override
    public void onDestroy() {

    }

    protected boolean isViewAttached() {
        return this.view != null && this.view.get() != null;
    }


    protected void checkCompositeSubscription() {
        if (this.compositeSubscription == null || this.compositeSubscription.isUnsubscribed())
            this.compositeSubscription = new CompositeSubscription();
    }
}