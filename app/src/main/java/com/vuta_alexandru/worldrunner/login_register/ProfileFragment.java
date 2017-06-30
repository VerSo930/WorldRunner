package com.vuta_alexandru.worldrunner.login_register;

/**
 * Created by vuta on 26/04/2017.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vuta_alexandru.worldrunner.R;
import com.vuta_alexandru.worldrunner.Tools.SpTools;
import com.vuta_alexandru.worldrunner.authorization.RestController;
import com.vuta_alexandru.worldrunner.models.MyResponse;
import com.vuta_alexandru.worldrunner.retrofit.DatabaseCallback;
import com.vuta_alexandru.worldrunner.retrofit.DatabaseOperations;
import com.vuta_alexandru.worldrunner.retrofit.RequestInterface;
import com.vuta_alexandru.worldrunner.retrofit.RestCallback;
import com.vuta_alexandru.worldrunner.retrofit.request_beans.ServerRequest;
import com.vuta_alexandru.worldrunner.retrofit.response_beans.ServerResponse;
import com.vuta_alexandru.worldrunner.models.Step;
import com.vuta_alexandru.worldrunner.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment implements View.OnClickListener,DatabaseCallback {

    private TextView tv_name,tv_email,tv_message;
    private SharedPreferences pref;
    private AppCompatButton btn_change_password,btn_logout, btn_update;
    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        initViews(view);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //user = (User) getArguments().getSerializable("User");

        user = SpTools.getUser(pref);
        tv_name.setText("Welcome : " + user.getFirstname() + " " + user.getLastname());
        tv_email.setText(SpTools.getToken(pref));

    }

    private void initViews(View view){

        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_email = (TextView)view.findViewById(R.id.tv_email);
        btn_change_password = (AppCompatButton)view.findViewById(R.id.btn_chg_password);
        btn_logout = (AppCompatButton)view.findViewById(R.id.btn_logout);
        btn_change_password.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        btn_update = (AppCompatButton) view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);

    }

    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        et_old_password = (EditText)view.findViewById(R.id.et_old_password);
        et_new_password = (EditText)view.findViewById(R.id.et_new_password);
        tv_message = (TextView)view.findViewById(R.id.tv_message);
        progress = (ProgressBar)view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = et_old_password.getText().toString();
                String new_password = et_new_password.getText().toString();
                if(!old_password.isEmpty() && !new_password.isEmpty()){

                    progress.setVisibility(View.VISIBLE);
                    changePasswordProcess(pref.getString(Constants.EMAIL,""),old_password,new_password);

                }else {

                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Fields are empty");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_chg_password:
                //showDialog();
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

                break;
            case R.id.btn_logout:
                logout();
                break;

            // TODO: This is a test case: remove after finish
            case R.id.btn_update:
                Log.d(Constants.TAG, "On update button ");
                User user = new User();
                //user.setSteps("456");
                //user.setUnique_id(pref.getString(Constants.UNIQUE_ID,""));
                DatabaseOperations databaseOperations = new DatabaseOperations(getActivity());
                //databaseOperations.getStepsReq(user, Constants.GET_STEPS_OPERATION, "1 DAY", "100", this);
                //databaseOperations.updateSteps(user, this);
                break;
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.NAME,"");
        editor.putString(Constants.UNIQUE_ID,"");
        editor.apply();
        goToLogin();
    }

    private void goToLogin(){

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }

    private void changePasswordProcess(String email,String old_password,String new_password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        //user.setOld_password(old_password);
        //user.setNew_password(new_password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.userReq(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                } else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG,"failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());
            }
        });
    }


    @Override
    public void UpdateError(Object o) {

        Log.d(Constants.TAG, " error: "+ o.toString());

    }

    @Override
    public void UpdateSuccess(Object o) {
        String msg = (String) o;
       /*List<Step> obj = (List<Step>) o;
        for(Step s: obj) {
            Log.d(Constants.TAG, " success: "+ s.getSteps());
        }*/

        Log.d(Constants.TAG, " success: "+ msg);

    }
}