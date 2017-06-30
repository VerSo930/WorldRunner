package com.vuta_alexandru.worldrunner.authorization;

import android.content.Context;
import android.content.res.Resources;
import android.util.Base64;
import android.util.Log;

import com.vuta_alexandru.worldrunner.MainActivity;
import com.vuta_alexandru.worldrunner.R;
import com.vuta_alexandru.worldrunner.models.Authentication;
import com.vuta_alexandru.worldrunner.models.MyResponse;
import com.vuta_alexandru.worldrunner.retrofit.RequestInterface;
import com.vuta_alexandru.worldrunner.retrofit.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

/**
 * Created by vuta on 29/06/2017.
 */

public class AuthHelper {

    public static String generateAuthorizationHeader(String user, String password) {

        String encodedUsernameAndPassword = user + ":" + password;
        byte[] byts = encodedUsernameAndPassword.getBytes();
        encodedUsernameAndPassword = "Basic " + Base64.encodeToString(byts, Base64.DEFAULT);
        return encodedUsernameAndPassword.replace("\n", "").replace("\r", "");
    }

    public static OkHttpClient.Builder createClient() {

        OkHttpClient.Builder client = null;

        CertificateFactory cf = null;
        InputStream cert = null;
        Certificate ca = null;
        SSLContext sslContext = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            //cert = context.getResources().openRawResource(R.raw.mycert); // Place your 'my_cert.crt' file in `res/raw`
            cert = MainActivity.getReso().openRawResource(R.raw.mycert);

            ca = cf.generateCertificate(cert);
            cert.close();

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);


            client = new OkHttpClient.Builder();
            client.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request request = chain.request();

                    Request.Builder builder = request.newBuilder();
                    builder.header("Accept", "application/json");


                    // try the request
                    Response response = chain.proceed(request);

                    if (response.code() == 401) {

                        // get a new token (I use a synchronous Retrofit call)
                        synchronized (builder) {
                            RequestInterface apiService = RestClient.getClient().create(RequestInterface.class);
                            Call<MyResponse<Authentication>> call = apiService.authentication(AuthHelper.generateAuthorizationHeader("verso.930@gmail.com", "verso9394"));
                            MyResponse<Authentication> myResponse = call.execute().body();

                            //int code = myResponse.getCode() / 100;
                            String token = null;
                            token = myResponse.getData().getToken();

                            Log.d("VTZ", "code: " + (myResponse.getCode()) + " message: " + myResponse.getMessage() + " toke: " + token);

                            builder.header("Authorization", myResponse.getData().getToken());
                            request = builder.build();

                            return chain.proceed(request);

                        }


                    }


                    // otherwise just pass the original response on
                    return response;
                }
            });
            client.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            })
                    .sslSocketFactory(sslContext.getSocketFactory());
            //.build();

        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | KeyManagementException e) {
            e.printStackTrace();
        }


        return client;
    }

}
