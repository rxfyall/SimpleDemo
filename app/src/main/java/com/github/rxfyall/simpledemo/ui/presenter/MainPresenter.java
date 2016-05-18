package com.github.rxfyall.simpledemo.ui.presenter;

import com.github.rxfyall.simpledemo.Interactor.PersonsInteractor;
import com.github.rxfyall.simpledemo.data.event.ErrorEvent;
import com.github.rxfyall.simpledemo.ui.viewmodel.MainView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainView> {

    private final EventBus bus = EventBus.getDefault();


    private final PersonsInteractor personsInteractor;

    @Inject
    public MainPresenter(PersonsInteractor personsInteractor) {
        this.personsInteractor = personsInteractor;
    }

    public void loadPersons() {
        checkCompositeSubscription();
        compositeSubscription.add(personsInteractor.value().subscribe(
                person -> {
                    if (isViewAttached()) {
                        getView().get().OnAddPerson(person);
                    }
                },
                throwable -> {
                    if (isViewAttached()) {
                        getView().get().OnRefreshing(false);
                        Timber.e(throwable, "Error on loadPersons");
                    }
                    bus.post(new ErrorEvent());
                }, () -> {
                    if (isViewAttached()) {
                        getView().get().OnSetVisibility(true);
                        getView().get().OnRefreshing(false);
                    }
                }

        ));
    }
}