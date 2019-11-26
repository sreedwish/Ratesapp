package com.revolut.ratesapp.rest;

import com.revolut.ratesapp.utils.CheckingValues;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return CheckingValues.NO_INTERNET_MSG;
    }

}