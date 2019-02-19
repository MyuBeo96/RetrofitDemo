package com.myubeo.retrofitdemo;

public class ApiUtils {
    public static final String BASE_URL = "https://api.stackexchange.com";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
