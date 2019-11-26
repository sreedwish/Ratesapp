package com.revolut.ratesapp.rest;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import static com.revolut.ratesapp.rest.Status.ERROR;
import static com.revolut.ratesapp.rest.Status.LOADING;
import static com.revolut.ratesapp.rest.Status.SUCCESS;

public class ApiResponse {

    public final Status status;

    @Nullable
    public  Object data;



    @Nullable
    public final Throwable error;

    public ApiResponse(Status status, @Nullable Object data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }



    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull Object data) {
        return new ApiResponse(SUCCESS, data, null);
    }


    public static ApiResponse error(@NonNull Throwable error) {

        return new ApiResponse(ERROR, null, error);
    }

}
