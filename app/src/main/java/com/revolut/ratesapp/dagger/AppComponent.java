package com.revolut.ratesapp.dagger;

import com.revolut.ratesapp.Rest.ApiClientModule;
import com.revolut.ratesapp.Rest.Repository;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, ApiClientModule.class})
@Singleton
public interface AppComponent {

    //Consumer
    void doInjection(Repository repository);


}
