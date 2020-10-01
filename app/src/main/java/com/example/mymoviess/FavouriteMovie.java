package com.example.mymoviess;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_movies")
public class FavouriteMovie extends Movie {


    public FavouriteMovie(int elementId, int movieId, int voteCount, String posterPath, String backDropPath, String title, String originalTitle, String overView, double voteAverage, String releaseDate, String bigPosterPath) {
        super(elementId, movieId, voteCount, posterPath, backDropPath, title, originalTitle, overView, voteAverage, releaseDate, bigPosterPath);
    }


    @Ignore
    public FavouriteMovie(Movie movie) {
        super(movie.getElementId(), movie.getMovieId(), movie.getVoteCount(), movie.getPosterPath(), movie.getBackDropPath(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverView(), movie.getVoteAverage(), movie.getReleaseDate(), movie.getBigPosterPath());

    }
}




