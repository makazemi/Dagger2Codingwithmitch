package com.example.dagger2codingwithmitch.network.main;

import com.example.dagger2codingwithmitch.models.Post;

import java.util.List;
import java.util.Queue;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    // posts?userId=1

    @GET("posts")
    Flowable<List<Post>> getPostFromUser(
            @Query("userId") int id
    );
}
