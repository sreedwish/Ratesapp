package com.revolut.ratesapp.dagger;

import android.app.Application;
import android.content.Context;

import com.revolut.ratesapp.rest.ApiClientModule;
import com.revolut.ratesapp.rest.NetworkAvailability;


/**
 * Created by Sreedwish
 */

public class MyApplication  extends Application {

    //Dagger 2 components
    AppComponent appComponent;

    Context context;

    NetworkAvailability networkAvailability;

    ApiClientModule apiClientModule;

    protected static MyApplication instance;

    public static MyApplication getInstance(){

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        instance = this;

        networkAvailability = new NetworkAvailability(context);

        apiClientModule = new ApiClientModule(networkAvailability);



        appComponent = DaggerAppComponent.builder().
                appModule(new AppModule(this))
                .apiClientModule(apiClientModule).build();


    }


    public AppComponent getAppComponent(){
        return appComponent;
    }



    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
