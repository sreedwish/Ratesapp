package com.revolut.ratesapp.utils;

import com.mynameismidori.currencypicker.ExtendedCurrency;
import com.revolut.ratesapp.models.BeanRate;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.revolut.ratesapp.utils.CheckingValues.base_value;
import static org.junit.Assert.*;

public class ConverterTest {

    @Test
    public void connvertionJObjToList(){


        String key = "CAD", key_eur = "EUR";

        base_value = 1.0;

        double new_val = Double.parseDouble(String.format("%.2f", base_value * 1.54));


        ExtendedCurrency currency =
                ExtendedCurrency.getCurrencyByISO(key);
        BeanRate expected = new BeanRate();

        expected.setC_code(key);
        expected.setC_name(currency.getName());
        expected.setC_flag(currency.getFlag());


        expected.setC_value(new_val);
        expected.setBase_code(key_eur);


        Converter converter = new Converter();

        try {

            BeanRate actual =  converter.getRateObj(key,new_val,key_eur);

            assertEquals( (long)expected.getC_value(), (long)actual.getC_value());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}