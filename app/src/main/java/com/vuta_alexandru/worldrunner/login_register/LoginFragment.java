package com.vuta_alexandru.worldrunner.login_register;

/**
 * Created by vuta on 26/04/2017.
 */


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vuta_alexandru.worldrunner.R;
import com.vuta_alexandru.worldrunner.Tools.SpTools;
import com.vuta_alexandru.worldrunner.authorization.AuthHelper;
import com.vuta_alexandru.worldrunner.authorization.RestController;
import com.vuta_alexandru.worldrunner.models.Authentication;
import com.vuta_alexandru.worldrunner.models.MyResponse;
import com.vuta_alexandru.worldrunner.retrofit.RequestInterface;
import com.vuta_alexandru.worldrunner.retrofit.RestCallback;
import com.vuta_alexandru.worldrunner.retrofit.request_beans.ServerRequest;
import com.vuta_alexandru.worldrunner.retrofit.response_beans.ServerResponse;
import com.vuta_alexandru.worldrunner.models.User;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button btn_login;
    private EditText et_email, et_password;
    private TextView tv_register;
    private ProgressBar progress;
    private SharedPreferences pref;
    private String TAG = "VTZ";
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;
    }


    private void initViews(View view) {

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        btn_login = (Button) view.findViewById(R.id.btn_login);
        tv_register = (TextView) view.findViewById(R.id.tv_register);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_register:
                goToRegister();
                break;

            case R.id.btn_login:
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                RestController.getUserById(28, new RestCallback<MyResponse<User>>() {
                    @Override
                    public void onSuccess(MyResponse<User> userMyResponse) {
                       /* Gson gson = new Gson();
                        String usr = gson.toJson(userMyResponse.getData());*/
                        Log.d("VTZ", "Success call");
                    }

                    @Override
                    public void onFail(String string) {

                    }
                }, getActivity().getApplicationContext());

/*
                if (!email.isEmpty() && !password.isEmpty()) {

                    //progress.setVisibility(View.VISIBLE);
                    loginProcess(email, password);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }*/
                break;

        }
    }

    private void loginProcess(String email, String password) {

        RestController.authentication(email, password, new RestCallback<MyResponse<Authentication>>() {

            @Override
            public void onSuccess(MyResponse<Authentication> authenticationMyResponse) {
                Log.d(TAG, "OK: " + authenticationMyResponse.getMessage());
                user = authenticationMyResponse.getData().getUser();
                if (authenticationMyResponse.getCode() == 200) {

                    goToProfile(authenticationMyResponse.getData().getUser());
                    SpTools.saveUser(pref, user);
                    SpTools.saveToken(pref, authenticationMyResponse.getData().getToken());

                } else {

                    Snackbar.make(getView(), authenticationMyResponse.getMessage(), Snackbar.LENGTH_LONG);
                }
            }

            @Override
            public void onFail(String string) {
                Log.d(TAG, "FAIL: " + string);
            }
        }, getActivity().getApplicationContext());

    }

    private void goToRegister() {

        Fragment register = new RegisterFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, register);
        ft.commit();
    }

    private void goToProfile(User user) {

        Fragment profile = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("User", user);
        profile.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, profile);
        ft.commit();
    }

}