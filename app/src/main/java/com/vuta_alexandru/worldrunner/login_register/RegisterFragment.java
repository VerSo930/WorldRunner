package com.vuta_alexandru.worldrunner.login_register;

/**
 * Created by vuta on 26/04/2017.
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vuta_alexandru.worldrunner.R;
import com.vuta_alexandru.worldrunner.models.Country;
import com.vuta_alexandru.worldrunner.models.ServerRequest;
import com.vuta_alexandru.worldrunner.models.ServerResponse;
import com.vuta_alexandru.worldrunner.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private Button btn_register;
    private EditText et_email, et_password, et_name;
    private TextView tv_login;
    private ProgressBar progress;
    private Spinner country;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);

        //setSpinnerData();
        //country.setPrompt("Select your Country!");

        return view;
    }

    private void initViews(View view) {

        btn_register = (Button) view.findViewById(R.id.btn_register);
        tv_login = (TextView) view.findViewById(R.id.tv_login);
        et_name = (EditText) view.findViewById(R.id.first_name);
        et_email = (EditText) view.findViewById(R.id.et_email);
        country = (Spinner) view.findViewById(R.id.country);
        et_password = (EditText) view.findViewById(R.id.et_password);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:

                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    registerProcess(name, email, password);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;

        }

    }

    private void registerProcess(String name, String email, String password) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed: " + t.getLocalizedMessage());
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void goToLogin() {

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, login);
        ft.commit();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = RegisterFragment.this.getResources().openRawResource(R.raw.countries);
            int size = 0;
            try {
                size = is.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void setSpinnerData() {

        ArrayList<Country> countryList = new ArrayList<>();
        //Add countries

        HashMap<String, String> m_li = null;
        ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("countries");


            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                Log.d("Details-->", jo_inside.getString("id"));
                String country_id = jo_inside.getString("id");
                String country_name = jo_inside.getString("name");
                String country_alpha2 = jo_inside.getString("alpha_2");

                //Add your values in your `ArrayList`
                countryList.add(new Country(country_id, country_name, country_alpha2));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //fill data in spinner
        ArrayAdapter<Country> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, countryList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        //ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(getActivity(),android.R.layout.simple_spinner_dropdown_item)
        country.setAdapter(adapter);
        country.setPrompt("Test");

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Country country = (Country) parent.getSelectedItem();
                Toast.makeText(getActivity(), "Country ID: " + country.getId() + ",  Country Name : " + country.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


}