package com.revolut.ratesapp;

import com.revolut.ratesapp.Models.BeanRate;

public interface ItemClickListener {

    void onClick(int position, BeanRate item);

    void onValueType(int position, BeanRate item);

}
