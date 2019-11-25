package com.revolut.ratesapp.Rest;

import androidx.annotation.Nullable;

import com.revolut.ratesapp.Models.RespGetRates;
import com.revolut.ratesapp.dagger.MyApplication;



import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class Repository {

    private final String TAG = "~~" + this.getClass().getSimpleName();

    ApiService apiService;

    //Constructor
    public Repository(ApiService apiService){
        this.apiService = apiService;

        MyApplication.getInstance().getAppComponent().doInjection(this);

    }

    /**
     * Api call to get currency
     * @param base_currency
     * @return
     */
    public Observable<RespGetRates> getCurrencyRates(@Nullable String base_currency){

        return apiService.getRates(base_currency);
    }


}
