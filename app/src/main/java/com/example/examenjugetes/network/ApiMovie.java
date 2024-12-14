package com.example.examenjugetes.network;

import com.example.examenjugetes.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiMovie {
    @GET("movies/list.php")
    Call<List<Movie>> getMovies();
}