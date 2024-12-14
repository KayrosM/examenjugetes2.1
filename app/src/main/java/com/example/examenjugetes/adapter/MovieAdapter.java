package com.example.examenjugetes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.examenjugetes.R;
import com.example.examenjugetes.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private Context context;

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    public void updateMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvTitulo.setText(movie.getNombre());
        Glide.with(context).load(movie.getLogo()).into(holder.ivPortada);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivPortada;
        private final TextView tvTitulo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPortada = itemView.findViewById(R.id.iv_portada);
            tvTitulo = itemView.findViewById(R.id.tv_titulo);
        }
    }
}
