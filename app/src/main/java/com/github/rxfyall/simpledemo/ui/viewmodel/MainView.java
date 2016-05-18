package com.github.rxfyall.simpledemo.ui.viewmodel;

import com.github.rxfyall.simpledemo.data.Person;

/**
 * Created by rcfgnu on 5/17/16.
 */
public interface MainView extends MvpView {

    void OnRefreshing(boolean enabled);

    void OnSetVisibility(boolean enabled);

    void OnAddPerson(Person person);


}
