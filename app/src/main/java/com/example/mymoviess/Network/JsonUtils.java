package com.example.mymoviess.Network;

import com.example.mymoviess.Movie;
import com.example.mymoviess.Reviews;
import com.example.mymoviess.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String KEY_ID = "id";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_RESULTS = "results";

    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";

    private static final String KEY_KEY_OF_TRAILER = "key";
    private static final String KEY_NAME_OF_TRAILER = "name";
    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";


    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private static final String BIG_POSTER_SIZE = "w780";
    private static final String SMALL_POSTER_SIZE = "w185";

    public static ArrayList<Movie> getMoviesFromJson(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
            try {

                JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objectMovie = jsonArray.getJSONObject(i);
                    int id = objectMovie.getInt(KEY_ID);
                    int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                    String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                    String bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                    String backDropPath = objectMovie.getString(KEY_BACKDROP_PATH);
                    String title = objectMovie.getString(KEY_TITLE);
                    String originalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                    String overview = objectMovie.getString(KEY_OVERVIEW);
                    double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                    String dateOfRelease = objectMovie.getString(KEY_RELEASE_DATE);
                    Movie movies = new Movie(id, voteCount, posterPath, backDropPath, title, originalTitle, overview, voteAverage, dateOfRelease, bigPosterPath);
                    result.add(movies);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }return result;

    }

    public static ArrayList<Trailer> getTrailersFromJson(JSONObject jsonObject) {
        ArrayList<Trailer> trailers = new ArrayList<>();
        if (jsonObject == null) {
            return trailers;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject trailer = jsonArray.getJSONObject(i);
                String key = BASE_YOUTUBE_URL + trailer.getString(KEY_KEY_OF_TRAILER);
                String nameOfTrailer = trailer.getString(KEY_NAME_OF_TRAILER);
                Trailer trailer1 = new Trailer(key, nameOfTrailer);
                trailers.add(trailer1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailers;
    }

    public static ArrayList<Reviews> getReviewsFromJson(JSONObject jsonObject){
        ArrayList<Reviews> result = new ArrayList<>();
        if(jsonObject == null){
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject review = jsonArray.getJSONObject(i);
                String author = review.getString(KEY_AUTHOR);
                String content = review.getString(KEY_CONTENT);
                Reviews reviews = new Reviews(author, content);
                result.add(reviews);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}


