package com.revolut.ratesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.mynameismidori.currencypicker.ExtendedCurrency;
import com.revolut.ratesapp.dagger.GlideApp;
import com.revolut.ratesapp.models.BeanRate;
import com.revolut.ratesapp.rest.ApiResponse;
import com.revolut.ratesapp.utils.CheckingValues;
import com.revolut.ratesapp.utils.Logger;
import com.revolut.ratesapp.viewmodels.RatesViewModel;
import com.revolut.ratesapp.dagger.MyApplication;
import com.revolut.ratesapp.dagger.ViewModelFactory;
import com.revolut.ratesapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Sreedwish
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "~~" +  getClass().getSimpleName();

    @Inject
    ViewModelFactory viewModelFactory;

    RatesViewModel viewModel;

    List<BeanRate> list = new ArrayList<>();

    ItemsAdapter adapter;

    Context context;

    ActivityMainBinding binding;

    private final String key_base = "BASE_K", key_count = "COUNT_K", key_load = "LOAD_K";

    private String base_currency = "EUR";

    boolean isLoading = false;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if (savedInstanceState != null){
            isLoading = savedInstanceState.getBoolean(key_load, false);
            count = savedInstanceState.getInt(key_count,0);
            base_currency = savedInstanceState.getString(key_base);

        }

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        context = this;

        MyApplication.getInstance().getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RatesViewModel.class);

        if (count != 0){
            binding.progress.setVisibility(View.GONE);
        }

        initRecyclerView();

        initLiveData();

        init_rateEditText();

        //Extra details from currency code
        ExtendedCurrency currency =
                ExtendedCurrency.getCurrencyByISO(base_currency);

        // Initializing base currency
        initValue(currency.getCode(),currency.getName(), currency.getFlag());


    }

    private void init_rateEditText() {

        //Setting edittext input type to number
        binding.include.edtRate.setInputType(InputType.TYPE_CLASS_NUMBER);

        //Text change listener for edit text rate
        binding.include.edtRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    String str = editable.toString().trim();

                    int new_val = 0;

                    if (!TextUtils.isEmpty(str)) {

                        new_val = Integer.parseInt(str);


                    }

                    CheckingValues.base_value = new_val;

                    viewModel.convertValue(list);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * This method setting base currency
     * @param code
     * @param name
     * @param drawable
     */
    void initValue(String code, String name, int drawable){

        binding.include.edtRate.setText("" + CheckingValues.base_value);

        BeanRate rate = new BeanRate();
        rate.setC_code(code);
        rate.setC_name(name);

        binding.include.setDat(rate);


        GlideApp.with(context)
                .load(drawable)
                .placeholder(R.drawable.grey_circle)
                .circleCrop()
                .into(binding.include.imgFlag);


    }

    void initRecyclerView(){

        adapter = new ItemsAdapter(context, list, new ItemClickListener() {
            @Override
            public void onClick(int position, BeanRate item) {

                base_currency = item.getC_code();

                list.remove(position);
                adapter.notifyItemRemoved(position);


                initValue(item.getC_code(), item.getC_name(), item.getC_flag());

                viewModel.apiCallGetRates(base_currency);

            }

        });

        RecyclerView recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);


    }

    void initLiveData(){

        //Api call time check
        viewModel.timeCheck();

        //Observer of data
        viewModel.getLiveDat().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {

                switch (apiResponse.status){

                    case LOADING:
                        count ++;

                        if (count == 1) {
                            disableViews();
                            binding.progress.setVisibility(View.VISIBLE);
                        }
                        isLoading = true;
                        break;

                    case SUCCESS:
                        if (count ==1) {
                            disableViews();
                        }
                        isLoading = false;

                        list.clear();
                        list.addAll((Collection<? extends BeanRate>) apiResponse.data);

                        Logger.logFun(TAG, list.get(list.size()-1).getC_name());

                        if (!list.isEmpty()){

                            binding.recycler.setVisibility(View.VISIBLE);
                            adapter.notifyDataSetChanged();
                        }

                        break;

                    case ERROR:

                        if (count == 1) {
                            disableViews();
                            String msg = apiResponse.error.getMessage();
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        }
                        isLoading = false;


                        break;
                }


            }
        });

        //Observer 1 second count
        viewModel.getLiveDataTime().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {

                switch (apiResponse.status){

                    case SUCCESS:
                        if (!isLoading){
                            viewModel.apiCallGetRates(base_currency);
                        }
                        break;
                }

            }
        });


    }

    void disableViews(){
        binding.recycler.setVisibility(View.GONE);
        binding.progress.setVisibility(View.GONE);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        try {


            //Orientation change related
            outState.putString(key_base, base_currency);
            outState.putInt(key_count, count);
            outState.putBoolean(key_load, isLoading);


        }catch (Exception e){

        }

        super.onSaveInstanceState(outState);
    }
}
