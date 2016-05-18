package com.github.rxfyall.simpledemo;

import com.github.rxfyall.simpledemo.ui.fragment.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rcfgnu on 5/17/16.
 */

@Component(
        modules = AppModule.class
)
@Singleton
public interface AppComponent {
    void inject(MainFragment activity);
}