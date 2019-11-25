package com.revolut.ratesapp.Rest;

import androidx.annotation.Nullable;

import com.revolut.ratesapp.Models.RespGetRates;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET(IpClass.GET_LATEST_RATE)
    Observable<RespGetRates> getRates(@Nullable @Query("base")String base_currency);

}
