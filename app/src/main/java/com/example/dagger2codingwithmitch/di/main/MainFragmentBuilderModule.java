package com.example.dagger2codingwithmitch.di.main;

import com.example.dagger2codingwithmitch.ui.main.posts.PostsFragment;
import com.example.dagger2codingwithmitch.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();

}
