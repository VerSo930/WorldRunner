package com.vuta_alexandru.worldrunner.login_register;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.vuta_alexandru.worldrunner.database_conn.RequestInterface;
import com.vuta_alexandru.worldrunner.models.Country;
import com.vuta_alexandru.worldrunner.database_conn.ServerRequest;
import com.vuta_alexandru.worldrunner.database_conn.ServerResponse;
import com.vuta_alexandru.worldrunner.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vuta on 05/05/2017.
 */

public class RegisterFragmentDetails extends Fragment implements View.OnClickListener{


    private Button btnNext;
    private EditText textWeightET, textHeightET, et_name;
    private TextView tv_login;
    private ProgressBar progress;
    private Spinner spinnerCountry;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        v = inflater.inflate(R.layout.fragment_register_details, container, false);
        initViews(v);

        setSpinnerData();
        spinnerCountry.setPrompt("Select your Country!");

        return v;
    }

    private void initViews(View view) {

        btnNext = (Button) view.findViewById(R.id.btnNext);
        textWeightET = (EditText) view.findViewById(R.id.textWeight);
        spinnerCountry = (Spinner) view.findViewById(R.id.spinnerCountry);
        textHeightET = (EditText) view.findViewById(R.id.textHeight);
        progress = (ProgressBar) view.findViewById(R.id.progress);

        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnNext:

                String textHeight = textHeightET.getText().toString();
                String textWeight = textWeightET.getText().toString();

                if (!textHeight.isEmpty() && !textWeight.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    Log.d("vuta2", "height: "+textHeight+" weight: "+textWeight);
                    //registerProcess(name, email, password);


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
        Call<ServerResponse> response = requestInterface.userReq(request);

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
            InputStream is = RegisterFragmentDetails.this.getResources().openRawResource(R.raw.countries);
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
        spinnerCountry.setAdapter(adapter);
        spinnerCountry.setPrompt("Test");

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
