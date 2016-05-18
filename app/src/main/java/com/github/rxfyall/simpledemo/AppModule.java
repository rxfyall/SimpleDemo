package com.github.rxfyall.simpledemo;

import com.github.rxfyall.simpledemo.Interactor.PersonsInteractor;
import com.github.rxfyall.simpledemo.data.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rcfgnu on 5/17/16.
 */
@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public ApiService provideApiService() {
        return new ApiService();
    }

    @Provides
    @Singleton
    public PersonsInteractor providePersonsInteractor(ApiService apiService) {
        return new PersonsInteractor(apiService);
    }
}
