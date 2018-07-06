package com.asanam.udacityrecipebook.network;

public interface NetworkApi {
    void get(String url, Callback apiCallback);

    public interface Callback {
        void onSuccess(String jsonResponse);
    }
}
