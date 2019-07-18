package com.example.dagger2codingwithmitch.di.main;

import androidx.lifecycle.ViewModel;

import com.example.dagger2codingwithmitch.di.ViewModelKey;
import com.example.dagger2codingwithmitch.ui.auth.AuthViewModel;
import com.example.dagger2codingwithmitch.ui.main.posts.PostsFragment;
import com.example.dagger2codingwithmitch.ui.main.posts.PostsViewModel;
import com.example.dagger2codingwithmitch.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel viewModel);


}
