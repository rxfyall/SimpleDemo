package com.github.rxfyall.simpledemo.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.rxfyall.simpledemo.R;
import com.github.rxfyall.simpledemo.ui.fragment.MainFragment;
import com.github.rxfyall.simpledemo.ui.presenter.Presenter;
import com.github.rxfyall.simpledemo.ui.presenter.PresenterFactory;
import com.github.rxfyall.simpledemo.ui.presenter.PresenterHolder;

public class MainActivity extends AppCompatActivity implements PresenterHolder {

    private PresenterHolder mPresenterHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainFragment(), "mainFragment").commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof PresenterHolder) {
            mPresenterHolder = (PresenterHolder) fragment;
        }
    }

    @Override
    public <T extends Presenter<?>> T getOrCreatePresenter(PresenterFactory<T> presenterFactory) {
        return mPresenterHolder.getOrCreatePresenter(presenterFactory);
    }

    @Override
    public void destroyPresenter(PresenterFactory<?> presenterFactory) {
        mPresenterHolder.destroyPresenter(presenterFactory);
    }

}
