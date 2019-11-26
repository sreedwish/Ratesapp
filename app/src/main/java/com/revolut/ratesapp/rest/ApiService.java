package com.revolut.ratesapp.rest;

import androidx.annotation.Nullable;

import com.revolut.ratesapp.models.RespGetRates;


import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET(IpClass.GET_LATEST_RATE)
    Observable<ResponseBody> getRates(@Nullable @Query("base")String base_currency);

}
