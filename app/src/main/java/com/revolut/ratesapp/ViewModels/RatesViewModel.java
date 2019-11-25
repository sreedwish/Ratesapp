package com.revolut.ratesapp.ViewModels;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.revolut.ratesapp.Models.BeanRate;
import com.revolut.ratesapp.Models.RespGetRates;
import com.revolut.ratesapp.Rest.ApiResponse;
import com.revolut.ratesapp.Rest.Repository;
import com.revolut.ratesapp.Utils.Converter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RatesViewModel extends ViewModel {


    Repository repository;

    Converter converter = new Converter();

    //Live data variables
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> liveDat = new MutableLiveData<>();

    //Constructor
    public RatesViewModel(Repository repository) {
        this.repository = repository;
    }

    //getter method
    public LiveData<ApiResponse> getLiveDat() {
        return liveDat;
    }

    //Api call
    public void apiCallGetRates(@Nullable final String base_currency){

        disposables.add(repository.getCurrencyRates(base_currency)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

                liveDat.setValue(ApiResponse.loading());
            }
        })
        .subscribe(new Consumer<RespGetRates>() {
            @Override
            public void accept(RespGetRates respGetRates) throws Exception {

                List<Object> objects = new ArrayList<>();


                try {

                    if (respGetRates != null){

                        objects.add(respGetRates.getBase());
                        objects.add(respGetRates.getDate());

                        if (respGetRates.getRates() != null){

                            List<BeanRate> rateList = new ArrayList<>();

                            String base = TextUtils.isEmpty(base_currency) ? "EUR" : base_currency;

                            rateList.addAll(converter.toList(respGetRates.getRates(),base));

                            objects.add(rateList);

                        }


                    }


                }catch (Exception e){

                }

                liveDat.setValue(ApiResponse.success(objects));

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

                liveDat.setValue(ApiResponse.error(throwable));
            }
        }));

    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }

}
