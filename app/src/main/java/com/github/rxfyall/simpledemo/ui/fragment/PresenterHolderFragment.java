package com.github.rxfyall.simpledemo.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.github.rxfyall.simpledemo.ui.presenter.Presenter;
import com.github.rxfyall.simpledemo.ui.presenter.PresenterFactory;
import com.github.rxfyall.simpledemo.ui.presenter.PresenterHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by frubino on 5/18/16.
 */
public class PresenterHolderFragment extends Fragment implements PresenterHolder {

    private static final String STATE_PRESENTERS = "presenters";

    private final Map<String, Presenter<?>> mPresenterMap = new HashMap<String, Presenter<?>>();

    private Bundle mPresentersStates;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);

        if (state != null) {
            mPresentersStates = state.getBundle(STATE_PRESENTERS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        Bundle presentersStates = new Bundle();
        for (Map.Entry<String, Presenter<?>> entry : mPresenterMap.entrySet()) {
            Bundle presenterState = new Bundle();
            entry.getValue().saveState(presenterState);
            presentersStates.putBundle(entry.getKey(), presenterState);
        }
        state.putBundle(STATE_PRESENTERS, presentersStates);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (Presenter<?> presenter : mPresenterMap.values()) {
            presenter.onDestroy();
        }
    }

    @Override
    public <T extends Presenter<?>> T getOrCreatePresenter(PresenterFactory<T> presenterFactory) {
        String tag = presenterFactory.getPresenterTag();

        @SuppressWarnings("unchecked")
        T presenter = (T) mPresenterMap.get(tag);

        if (presenter == null) {
            presenter = presenterFactory.createPresenter();
            if (mPresentersStates != null && mPresentersStates.containsKey(tag)) {
                presenter.restoreState(mPresentersStates.getBundle(tag));
            }
            mPresenterMap.put(tag, presenter);
        }

        return presenter;
    }

    @Override
    public void destroyPresenter(PresenterFactory<?> presenterFactory) {
        String tag = presenterFactory.getPresenterTag();
        Presenter<?> presenter = mPresenterMap.get(tag);
        if (presenter != null) {
            presenter.onDestroy();
        }
        mPresenterMap.remove(tag);
    }
}
