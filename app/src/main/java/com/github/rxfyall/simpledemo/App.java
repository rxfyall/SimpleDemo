package com.github.rxfyall.simpledemo;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

/**
 * Created by rcfgnu on 5/17/16.
 */
public class App extends Application {
    private static Context context;

    private AppComponent component;

    protected AppModule getApplicationModule() {
        return new AppModule(this);
    }


    public void onCreate() {
        super.onCreate();
        //Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(this));
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        App.context = getApplicationContext();

    }


    public static AppComponent getAppComponent(Context context) {
        App app = (App) context.getApplicationContext();
        if (app.component == null) {
            app.component = DaggerAppComponent.builder()
                    .appModule(app.getApplicationModule())
                    .build();
        }
        return app.component;
    }


    public static Context getAppContext() {
        return App.context;
    }

    public static void clearAppComponent(Context context) {
        App app = (App) context.getApplicationContext();
        app.component = null;
    }
}
