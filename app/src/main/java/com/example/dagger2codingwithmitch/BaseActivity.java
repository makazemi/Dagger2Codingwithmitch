package com.example.dagger2codingwithmitch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.dagger2codingwithmitch.models.User;
import com.example.dagger2codingwithmitch.ui.auth.AuthActivity;
import com.example.dagger2codingwithmitch.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers(){
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource!=null){
                    switch (userAuthResource.status){
                        case LOADING:{
                            break;
                        }
                        case AUTHENTICATED:{
                            Log.d(TAG, "onChanged: LOGIN SUCCESSFUL "+userAuthResource.data.getEmail());
                            break;
                        }
                        case ERROR:{
                            break;
                        }
                        case NOT_AUTHENTICATED:{
                            navLoginScreen();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void navLoginScreen(){
        Intent intent=new Intent(this,AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
