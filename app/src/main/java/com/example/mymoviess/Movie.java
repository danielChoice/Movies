package com.example.mymoviess;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int elementId;
    private int movieId;
    private int voteCount;
    private String posterPath;
    private String backDropPath;
    private String title;
    private String originalTitle;
    private String overView;
    private double voteAverage;
    private String releaseDate;
    private String bigPosterPath;


    public Movie(int elementId, int movieId, int voteCount, String posterPath, String backDropPath, String title, String originalTitle, String overView, double voteAverage, String releaseDate, String bigPosterPath) {
        this.elementId = elementId;
        this.movieId = movieId;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overView = overView;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.bigPosterPath = bigPosterPath;
    }

    @Ignore
    public Movie(int id, int voteCount, String posterPath, String backDropPath, String title, String originalTitle, String overView, double voteAverage, String dateOfRelease, String bigPosterPath) {
        this.movieId = id;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overView = overView;
        this.voteAverage = voteAverage;
        this.releaseDate = dateOfRelease;
        this.bigPosterPath = bigPosterPath;
    }

    public int getElementId() {
        return elementId;
    }

    public void setElementId(int elementId) {
        this.elementId = elementId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBigPosterPath() {
        return bigPosterPath;
    }

    public void setBigPosterPath(String bigPosterPath) {
        this.bigPosterPath = bigPosterPath;
    }
}
