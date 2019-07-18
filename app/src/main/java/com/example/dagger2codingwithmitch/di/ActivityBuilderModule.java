package com.example.dagger2codingwithmitch.di;

import com.example.dagger2codingwithmitch.di.auth.AuthModule;
import com.example.dagger2codingwithmitch.di.auth.AuthScope;
import com.example.dagger2codingwithmitch.di.auth.AuthViewModelsModule;
import com.example.dagger2codingwithmitch.di.main.MainFragmentBuilderModule;
import com.example.dagger2codingwithmitch.di.main.MainModule;
import com.example.dagger2codingwithmitch.di.main.MainScope;
import com.example.dagger2codingwithmitch.di.main.MainViewModelModule;
import com.example.dagger2codingwithmitch.ui.auth.AuthActivity;
import com.example.dagger2codingwithmitch.ui.main.MainActivity;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class,
                       AuthModule.class}
    )
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuilderModule.class,
                    MainViewModelModule.class,
                    MainModule.class,})
    abstract MainActivity contributeMainActivity();


}
