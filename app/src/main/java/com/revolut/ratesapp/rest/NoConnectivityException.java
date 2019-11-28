package com.revolut.ratesapp.rest;

import com.revolut.ratesapp.utils.CheckingValues;

import java.io.IOException;

/**
 * Created by Sreedwish
 */
public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return CheckingValues.NO_INTERNET_MSG;
    }

}