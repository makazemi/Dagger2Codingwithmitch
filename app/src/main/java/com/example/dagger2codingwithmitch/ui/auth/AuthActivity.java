package com.example.dagger2codingwithmitch.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.example.dagger2codingwithmitch.R;
import com.example.dagger2codingwithmitch.models.User;
import com.example.dagger2codingwithmitch.ui.main.MainActivity;
import com.example.dagger2codingwithmitch.viewmodels.ViewModelProvidersFactory;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity
implements View.OnClickListener{

    private static final String TAG = "AuthActivity";

    @Inject
    Drawable logo;
    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProvidersFactory providersFactory;

    protected AuthViewModel viewModel;

    private EditText edtUserId;
    private ProgressBar progressBar;

    @Inject
    @Named("app-user")
    User user1;

    @Inject
    @Named("auth-user")
    User user2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        edtUserId=findViewById(R.id.user_id_input);
        findViewById(R.id.login_button).setOnClickListener(this);
        progressBar=findViewById(R.id.progress_bar);

        viewModel= ViewModelProviders.of(this,providersFactory).get(AuthViewModel.class);

        setLogo();
        subscribeObservers();

        Log.d(TAG, "onCreate: user1: "+user1);
        Log.d(TAG, "onCreate: user12: "+user2);

    }

    private void subscribeObservers(){
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource!=null){
                    switch (userAuthResource.status){
                        case LOADING:{
                            showProgressBar(true);
                            break;
                        }
                        case AUTHENTICATED:{
                            showProgressBar(false);
                            onLoginSuccess();
                            Log.d(TAG, "onChanged: LOGIN SUCCESSFUL "+userAuthResource.data.getEmail());
                            break;
                        }
                        case ERROR:{
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, userAuthResource.message+
                                    "\nDid you enter a number between 1 and 10?", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case NOT_AUTHENTICATED:{
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void onLoginSuccess(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void showProgressBar(boolean isVisible){

        if(isVisible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private void setLogo(){
        requestManager
                .load(logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:{
                attemptLogin();
                break;
            }
        }
    }
    private void attemptLogin(){
        if(TextUtils.isEmpty(edtUserId.getText().toString()))
            return;
        viewModel.authenticateWithId(Integer.parseInt(edtUserId.getText().toString()));
    }
}
