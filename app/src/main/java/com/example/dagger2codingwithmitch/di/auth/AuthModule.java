package com.example.dagger2codingwithmitch.di.auth;

import com.example.dagger2codingwithmitch.models.User;
import com.example.dagger2codingwithmitch.network.auth.AuthApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class  AuthModule  {

    @AuthScope
    @Provides
    @Named("auth-user")
    static User someUser(){
        return  new User();
    }

    @AuthScope
    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }
}
