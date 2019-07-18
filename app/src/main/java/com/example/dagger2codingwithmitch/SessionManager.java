package com.example.dagger2codingwithmitch;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.dagger2codingwithmitch.models.User;
import com.example.dagger2codingwithmitch.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager";
    private MediatorLiveData<AuthResource<User>> cashedUser=new MediatorLiveData<>();

    @Inject
    public SessionManager(){

    }

    public void authenticateWitId(final LiveData<AuthResource<User>> source){
        if(cashedUser!=null){
            cashedUser.setValue(AuthResource.loading((User)null));
            cashedUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cashedUser.setValue(userAuthResource);
                    cashedUser.removeSource(source);
                }
            });
        }

    }
    public void logOut(){
        Log.d(TAG, "logOut: logging out...");
        cashedUser.setValue(AuthResource.<User>logout());
    }

    public LiveData<AuthResource<User>> getAuthUser(){
        return cashedUser;
    }
}
