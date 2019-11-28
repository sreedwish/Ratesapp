package com.revolut.ratesapp.utils;

import android.util.Log;

import com.mynameismidori.currencypicker.ExtendedCurrency;
import com.revolut.ratesapp.models.BeanRate;

import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.revolut.ratesapp.utils.CheckingValues.base_value;


/**
 * Created by Sreedwish
 */
public class Converter {

    //Api response to the display format list
    public List<BeanRate> toList(JSONObject object, String base)  {

        List<BeanRate> rateList = new ArrayList<>();

        try {

            Iterator<String> keysItr = object.keys();


            while(keysItr.hasNext()) {

                String key = keysItr.next();
                double value = (double) object.get(key);


                //Finally adding object to return list
                rateList.add(getRateObj(key,value,base));

            }

            //if last item is euro then update its position to 1
            BeanRate lastItem = rateList.get(rateList.size() - 1);
            if (lastItem.getC_code().equals("EUR")){
                rateList.remove(lastItem);
                rateList.add(0,lastItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




        return rateList;
    }

    public BeanRate getRateObj(String key, double value, String base){
        BeanRate rate = new BeanRate();


        try {

            //Extra details from currency code
            ExtendedCurrency currency =
                    ExtendedCurrency.getCurrencyByISO(key);


            //Setting up values
            rate.setC_code(key);

            double new_val = Double.parseDouble(String.format("%.2f", base_value * value));

            if (new_val < 0.01){
                new_val = base_value * value;
            }

            //Logger.logFun(null, key + " computed val " + new_val);

            rate.setC_value( new_val);

            //Additional values
            rate.setC_name(currency.getName());
            rate.setC_flag(currency.getFlag());



        }catch (Exception e){
            e.printStackTrace();
        }

        return rate;

    }


}
