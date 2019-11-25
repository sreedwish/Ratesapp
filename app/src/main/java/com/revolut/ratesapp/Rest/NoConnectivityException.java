package com.revolut.ratesapp.Rest;

import com.revolut.ratesapp.Utils.CheckingValues;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return CheckingValues.NO_INTERNET_MSG;
    }

}