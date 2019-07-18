package com.example.dagger2codingwithmitch.di;

import androidx.lifecycle.ViewModelProvider;

import com.example.dagger2codingwithmitch.viewmodels.ViewModelProvidersFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProvidersFactory modelProvidersFactory);


}
