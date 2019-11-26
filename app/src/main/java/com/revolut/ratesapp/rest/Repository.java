package com.revolut.ratesapp.rest;

import androidx.annotation.Nullable;

import com.revolut.ratesapp.models.RespGetRates;
import com.revolut.ratesapp.dagger.MyApplication;


import org.json.JSONObject;

import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

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
    public Observable<ResponseBody> getCurrencyRates(@Nullable String base_currency){

        return apiService.getRates(base_currency);
    }


}
