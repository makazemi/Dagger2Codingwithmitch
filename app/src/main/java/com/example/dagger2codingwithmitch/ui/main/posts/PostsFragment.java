package com.example.dagger2codingwithmitch.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dagger2codingwithmitch.R;
import com.example.dagger2codingwithmitch.models.Post;
import com.example.dagger2codingwithmitch.ui.main.Resource;
import com.example.dagger2codingwithmitch.util.VerticalSpacingItemDecoration;
import com.example.dagger2codingwithmitch.viewmodels.ViewModelProvidersFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragment";

    private PostsViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProvidersFactory providersFactory;

    @Inject
    PostRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView=view.findViewById(R.id.recycler_view);
        viewModel= ViewModelProviders.of(this,providersFactory).get(PostsViewModel.class);
        initRecycler();
        subscribeObservers();

    }

    private void subscribeObservers(){
        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
        viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if(listResource!=null){
                    switch (listResource.status){
                        case LOADING:
                            break;
                        case SUCCESS:
                            adapter.setPosts(listResource.data);
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: Error..."+listResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void initRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration=new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }
}
