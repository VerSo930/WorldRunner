package com.vuta_alexandru.worldrunner.retrofit;


/**
 * Created by vuta on 30/06/2017.
 */

public interface RestCallback<T> {

    public void onSuccess(T t);

    public void onFail(String string);


}
