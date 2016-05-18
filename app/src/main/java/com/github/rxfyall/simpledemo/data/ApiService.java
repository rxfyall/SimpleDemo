package com.github.rxfyall.simpledemo.data;

import com.github.rxfyall.simpledemo.App;
import com.github.rxfyall.simpledemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import rx.Observable;

/**
 * Created by rcfgnu on 5/17/16.
 */
public class ApiService {

    public Observable<Person> getPersons() {
        return rx.Observable.defer(() -> {
            try {
                InputStream is = App.getAppContext().getResources().openRawResource(R.raw.mockdata);
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                Gson gson = new Gson();
                List<Person> persons = gson.fromJson(reader, new TypeToken<List<Person>>() {
                }.getType());
                return Observable.from(persons);
            } catch (Exception e) {
                return Observable.error(e);
            }
        });

    }
}
