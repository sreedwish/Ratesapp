package com.revolut.ratesapp.dagger;

import com.revolut.ratesapp.MainActivity;
import com.revolut.ratesapp.rest.ApiClientModule;
import com.revolut.ratesapp.rest.Repository;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, ApiClientModule.class})
@Singleton
public interface AppComponent {

    //Consumer
    void doInjection(Repository repository);


    void doInjection(MainActivity mainActivity);
}
