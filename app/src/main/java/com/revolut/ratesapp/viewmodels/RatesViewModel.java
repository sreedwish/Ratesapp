package com.revolut.ratesapp.viewmodels;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.revolut.ratesapp.models.BeanRate;
import com.revolut.ratesapp.rest.ApiResponse;
import com.revolut.ratesapp.rest.Repository;
import com.revolut.ratesapp.utils.CheckingValues;
import com.revolut.ratesapp.utils.Converter;
import com.revolut.ratesapp.utils.Logger;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RatesViewModel extends ViewModel {


    Repository repository;

    Converter converter = new Converter();

    //Live data variables
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> liveDat = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse> liveDataTime = new MutableLiveData<>();

    //Constructor
    public RatesViewModel(Repository repository) {
        this.repository = repository;
    }

    //getter method
    public LiveData<ApiResponse> getLiveDat() {
        return liveDat;
    }

    public LiveData<ApiResponse> getLiveDataTime() {
        return liveDataTime;
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
        .subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody resp) throws Exception {



                List<BeanRate> rateList = new ArrayList<>();


                try {

                    if (resp != null){

                       if (resp != null){


                            String base = TextUtils.isEmpty(base_currency) ? "EUR" : base_currency;

                            JSONObject jsonObject = new JSONObject(resp.string());

                            if (jsonObject != null){

                                JSONObject rate = jsonObject.getJSONObject("rates");

                                //Logger.logFun(null,  rate.toString());

                                rateList.addAll(converter.toList( rate,base));

                            }


                        }


                    }


                }catch (Exception e){
                    e.printStackTrace();
                }

                liveDat.setValue(ApiResponse.success(rateList));

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

                liveDat.setValue(ApiResponse.error(throwable));
            }
        }));

    }


    public void convertValue(final List<BeanRate> rateList){

        Single<List<BeanRate>> singleObservable  = Single.create(new SingleOnSubscribe<List<BeanRate>>() {
            @Override
            public void subscribe(SingleEmitter<List<BeanRate>> emitter) throws Exception {

                try {

                    List<BeanRate> newList = new ArrayList<>();

                    for (BeanRate r : rateList) {

                        double old_val = r.getC_value();
                        double new_val = CheckingValues.base_value * old_val;

                        r.setC_value(new_val);

                        newList.add(r);
                    }

                    if (!emitter.isDisposed()){
                        emitter.onSuccess(newList);
                    }

                }catch (Exception e){

                }

            }
        });



        disposables.add(singleObservable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

                liveDat.setValue(ApiResponse.loading());
            }
        })
        .subscribe(new Consumer<List<BeanRate>>() {
            @Override
            public void accept(List<BeanRate> rateList) throws Exception {

                liveDat.setValue(ApiResponse.success(rateList));

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

                liveDat.setValue(ApiResponse.error(throwable));
            }
        }));



    }

    public void timeCheck(){

        Observable<Long> observable = Observable.interval(1000L, TimeUnit.MILLISECONDS);

        disposables.add(observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

                liveDataTime.setValue(ApiResponse.loading());
            }
        })
        .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

                liveDataTime.setValue(ApiResponse.success(aLong));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

                liveDataTime.setValue(ApiResponse.error(throwable));
            }
        }));

    }


    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }

}
