package com.revolut.ratesapp.Utils;

import com.mynameismidori.currencypicker.ExtendedCurrency;
import com.revolut.ratesapp.Models.BeanRate;
import com.revolut.ratesapp.Models.RespGetRates;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Converter {



    public List<BeanRate> toList(JSONObject object, String base)  {

        List<BeanRate> rateList = new ArrayList<>();

        try {

            Iterator<String> keysItr = object.keys();

            double base_value = object.getDouble(base);

            while(keysItr.hasNext()) {

                String key = keysItr.next();
                double value = (double) object.get(key);

                //Extra details from currency code
                ExtendedCurrency currency =
                        ExtendedCurrency.getCurrencyByISO(key);

                //New BeanRate object
                BeanRate rate = new BeanRate();

                //Setting up values
                rate.setC_code(key);
                rate.setC_value(value);

                //Additional values
                rate.setC_name(currency.getName());
                rate.setC_flag(currency.getFlag());

                //Difference between base and item
                double diff = base_value - value;

                //Set difference
                rate.setBase_code(base);
                rate.setRate_difference(diff);

                //Finally adding object to return list
                rateList.add(rate);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return rateList;
    }

}
