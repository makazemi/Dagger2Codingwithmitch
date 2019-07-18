package com.example.dagger2codingwithmitch.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dagger2codingwithmitch.R;
import com.example.dagger2codingwithmitch.models.User;
import com.example.dagger2codingwithmitch.ui.auth.AuthResource;
import com.example.dagger2codingwithmitch.viewmodels.ViewModelProvidersFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";

    private ProfileViewModel viewModel;

    private TextView txtEmail,txtUsername,txtWebsite;

    @Inject
    ViewModelProvidersFactory providersFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       viewModel= ViewModelProviders.of(this,providersFactory).get(ProfileViewModel.class);

       txtEmail=view.findViewById(R.id.email);
       txtUsername=view.findViewById(R.id.username);
       txtWebsite=view.findViewById(R.id.website);

       subscribeObservers();
    }

    private void subscribeObservers(){
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {

                if(userAuthResource!=null){
                    switch (userAuthResource.status){
                        case AUTHENTICATED:
                            setUserDetails(userAuthResource.data);
                            break;
                        case ERROR:
                            setErrorDetails(userAuthResource.message);
                            break;
                    }
                }
            }
        });

    }

    private void setErrorDetails(String message){
        txtEmail.setText(message);
        txtUsername.setText("error");
        txtWebsite.setText("error");
    }


    private void setUserDetails(User user){
        txtEmail.setText(user.getEmail());
        txtUsername.setText(user.getUsername());
        txtWebsite.setText(user.getWebsite());
    }
}
