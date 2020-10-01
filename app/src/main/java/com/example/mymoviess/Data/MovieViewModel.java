package com.example.mymoviess.Data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviess.FavouriteActivity;
import com.example.mymoviess.FavouriteMovie;
import com.example.mymoviess.Movie;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> movies;
    private static MovieDataBase dataBase;
    private LiveData<List<FavouriteMovie>> favouriteMovies;
    public MovieViewModel(@NonNull Application application) {
        super(application);

        dataBase = MovieDataBase.getInstance(application);
        movies = dataBase.movieDao().getAllMovies();
        favouriteMovies = dataBase.movieDao().getAllFavouriteMovies();

    }

    public LiveData<List<FavouriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public Movie getMovieById(int movieId){
        try {
            return new getMovieByIdTask().execute(movieId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static class getMovieByIdTask extends AsyncTask<Integer, Void, Movie>{
        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return dataBase.movieDao().getMovieByMovieId(integers[0]);
            }
            return null;
        }
    }

  public Movie getMoviesByElementId(int elementId){
        try {
            return new getMovieByElementIdIdTask().execute(elementId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getMovieByElementIdIdTask extends AsyncTask<Integer, Void, Movie>{
        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return dataBase.movieDao().getMovieByElementId(integers[0]);
            }
            return null;
        }
    }

    public void insertMovie(Movie movie){
        new insertMovieTask().execute(movie);

    }

    private static class insertMovieTask extends AsyncTask<Movie, Void, Void>{
        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies != null && movies.length > 0){
                dataBase.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

public void deleteMovie(Movie movie){
        new deleteMovieTask().execute(movie);
    }

    private static class deleteMovieTask extends AsyncTask<Movie, Void, Void>{
        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies != null && movies.length > 0){
                dataBase.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    public void deleteAllMovies(){
        new deleteAllMoviesTask().execute();

    }

    private static class deleteAllMoviesTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            dataBase.movieDao().deleteAllMovies();
            return null;
        }
    }


    //для favouriteMovie

    public FavouriteMovie getFavouriteMovieById(int movieId){
        try {
            return new getFavouriteMovieByIdIdTask().execute(movieId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static class getFavouriteMovieByIdIdTask extends AsyncTask<Integer, Void, FavouriteMovie>{
        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return dataBase.movieDao().getFavouriteMovieByMovieId(integers[0]);
            }
            return null;
        }
    }


    public void insertFavouriteMovie(FavouriteMovie favouriteMovie){
        new insertFavouriteMovieTask().execute(favouriteMovie);

    }

    private static class insertFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void>{
        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if(movies != null && movies.length > 0){
                dataBase.movieDao().insertFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    public void deleteFavouriteMovie(FavouriteMovie favouriteMovie){
        new deleteFavouriteMovieTask().execute(favouriteMovie);

    }

    private static class deleteFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void>{
        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if(movies != null && movies.length > 0){
                dataBase.movieDao().deleteFavouriteMovie(movies[0]);
            }
            return null;
        }
    }






}
