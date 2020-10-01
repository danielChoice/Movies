package com.example.mymoviess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviess.Adapters.ReviewsAdapter;
import com.example.mymoviess.Adapters.TrailersAdapter;
import com.example.mymoviess.Data.MovieViewModel;
import com.example.mymoviess.Network.JsonUtils;
import com.example.mymoviess.Network.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewNameDesc;
    private TextView textViewOriginalNameDesc;
    private TextView textViewVoteAverageDesc;
    private TextView textViewOverviewDesc;
    private int id;
    private MovieViewModel viewModel;
    private Movie movie;
    private FavouriteMovie favouriteMovie;
    private ImageView imageViewAddToFavourite;

    private RecyclerView recyclerViewReviews;
    private ReviewsAdapter reviewsAdapter;
    private ArrayList<Reviews> reviewsArrayList;

    private RecyclerView recyclerViewTrailers;
    private TrailersAdapter trailersAdapter;
    private ArrayList<Trailer> trailerArrayList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intentFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewNameDesc = findViewById(R.id.textViewNameDesc);
        textViewOriginalNameDesc = findViewById(R.id.textViewOriginalNameDesc);
        textViewVoteAverageDesc = findViewById(R.id.textViewVoteAverageDesc);
        textViewOverviewDesc = findViewById(R.id.textViewOverviewDesc);
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);


        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", 0);
            movie = viewModel.getMovieById(id);
        } else {
            finish();
        }

        Picasso.get().load(movie.getBigPosterPath()).placeholder(R.drawable.placeholder_large).into(imageViewBigPoster);
        textViewNameDesc.setText(movie.getTitle());
        textViewOriginalNameDesc.setText(movie.getOriginalTitle());
        textViewVoteAverageDesc.setText(Double.toString(movie.getVoteAverage()));
        textViewOverviewDesc.setText(movie.getOverView());


        recyclerViewReviews = findViewById(R.id.recyclerReviews);
        reviewsAdapter = new ReviewsAdapter();
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviews.setAdapter(reviewsAdapter);
        JSONObject jsonObjectReviews = NetworkUtils.createReviewJsonObject(movie.getMovieId());
        reviewsArrayList = JsonUtils.getReviewsFromJson(jsonObjectReviews);
        reviewsAdapter.setReviews(reviewsArrayList);

        recyclerViewReviews = findViewById(R.id.recyclerTrailers);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        trailersAdapter = new TrailersAdapter();
        recyclerViewReviews.setAdapter(trailersAdapter);
        JSONObject jsonObject = NetworkUtils.createVideoJsonObject(movie.getMovieId());
        trailerArrayList = JsonUtils.getTrailersFromJson(jsonObject);
        trailersAdapter.setTrailers(trailerArrayList);
        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentToTrailer);
            }
        });

        setFavouriteMovie();



    }

    public void setFavouriteMovie() {
        //написать поиск по id фильма
        favouriteMovie = viewModel.getFavouriteMovieById(id);
        if (favouriteMovie != null) {
            Picasso.get().load(R.drawable.delete_from_favourite).into(imageViewAddToFavourite);
        } else {
            Picasso.get().load(R.drawable.add_to_favourite).into(imageViewAddToFavourite);
        }

    }

    public void onClickChangeFavourite(View view) {
        if (favouriteMovie == null) {
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, "Добавлен в израбнное", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, "Удален из избранного", Toast.LENGTH_SHORT).show();
        }
        setFavouriteMovie();

    }


}

