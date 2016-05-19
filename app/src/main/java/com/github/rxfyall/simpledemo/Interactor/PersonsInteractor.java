package com.github.rxfyall.simpledemo.Interactor;

import com.github.rxfyall.simpledemo.data.ApiService;
import com.github.rxfyall.simpledemo.data.Person;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by rcfgnu on 5/17/16.
 */
public class PersonsInteractor {

    private final ApiService apiService;
    private ReplaySubject<Person> personReplaySubject;
    private Subscription personSubscription;
    private List<Person> memoryCache = new ArrayList<>();

    @Inject
    public PersonsInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<Person> value() {
        if (personSubscription == null || personSubscription.isUnsubscribed()) {
            personReplaySubject = ReplaySubject.create();

            personSubscription = Observable.concat(memory(), disk())
                    .subscribe(personReplaySubject);
        }

        return personReplaySubject.asObservable();
    }

    private Observable<Person> memory() {
        return Observable.from(memoryCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Person> disk() {
        return apiService.getPersons().doOnNext(person -> memoryCache.add(person))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
