package com.github.rxfyall.simpledemo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.rxfyall.simpledemo.App;
import com.github.rxfyall.simpledemo.R;
import com.github.rxfyall.simpledemo.data.Person;
import com.github.rxfyall.simpledemo.ui.adapter.PersonViewAdapter;
import com.github.rxfyall.simpledemo.ui.presenter.BasePresenter;
import com.github.rxfyall.simpledemo.ui.presenter.MainPresenter;
import com.github.rxfyall.simpledemo.ui.presenter.PresenterFactory;
import com.github.rxfyall.simpledemo.ui.viewmodel.MainView;
import com.github.rxfyall.simpledemo.ui.viewmodel.MvpView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends BaseFragment implements MainView,
        PresenterFactory<MainPresenter> {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.swipe_container)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    protected MainPresenter mPresenter;

    private PersonViewAdapter mAdapter;

    private ArrayList<Person> mAddedPersons = new ArrayList<>();

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getAppComponent(App.getAppContext()).inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mAdapter = new PersonViewAdapter(new ArrayList<>(), R.layout.content_main);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimary));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                        getResources().getDisplayMetrics()));

        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.setVisibility(View.GONE);
        mPresenter.loadPersons();
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected MvpView getMvpView() {
        return this;
    }

    @Override
    public void OnRefreshing(boolean enabled) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void OnSetVisibility(boolean enabled) {
        if (enabled) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnAddPerson(Person person) {
        mAddedPersons.add(person);
        mAdapter.addPerson(mAddedPersons.size() - 1, person);
    }

    @Override
    public MainPresenter createPresenter() {
        return mPresenter;
    }

    @Override
    public String getPresenterTag() {
        return MainPresenter.class.getName();
    }

}
