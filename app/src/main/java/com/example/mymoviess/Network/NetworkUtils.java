package com.example.mymoviess.Network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {


    private static final String VALUE_BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String BASE_URL_VIDEOS = "https://api.themoviedb.org/3/movie/%s/videos";
    private static final String BASE_URL_REVIEWS = "https://api.themoviedb.org/3/movie/%s/reviews";

    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";
    private static final String PARAMS_MIN_VOTE_COUNT = "vote_count.gte";


    private static final String VALUE_MIN_VOTE_COUNT = "500";
    private static final String VALUE_API_KEY = "f7175dbe7757d2b45bacbf7217890622";
    private static final String VALUE_LANGUAGE = "ru-RU";
    private static final String VALUE_LANGUAGE_EN = "en-EN";
    private static final String VALUE_SORT_BY_TOP_RATED = "vote_average.desc";
    private static final String VALUE_SORT_BY_POPULARITY = "popularity.desc";

    public static int POPULARITY = 0;
    public static int TOP_RATED = 1;

    public static URL buildUrl(int sortBy, int page){
        String methodOfSort;
        URL result = null;
        if(sortBy == POPULARITY){
            methodOfSort = VALUE_SORT_BY_POPULARITY;
        }else {
            methodOfSort = VALUE_SORT_BY_TOP_RATED;
        }
        Uri uri = Uri.parse(VALUE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, VALUE_API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, VALUE_LANGUAGE)
                .appendQueryParameter(PARAMS_MIN_VOTE_COUNT, VALUE_MIN_VOTE_COUNT)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static URL buildVideoUrl(int movieId){
        Uri uri = Uri.parse(String.format(BASE_URL_VIDEOS, movieId)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, VALUE_API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, VALUE_LANGUAGE_EN)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL buildCommentsUrl(int movieId){
        Uri uri = Uri.parse(String.format(BASE_URL_REVIEWS, movieId)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, VALUE_API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, VALUE_LANGUAGE_EN)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static JSONObject createJsonObject(int sortBy, int page){
       URL url =  buildUrl(sortBy, page);
       JSONObject result = null;
        try {
            result = new JsonTask().execute(url).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;


    }

    public static JSONObject createVideoJsonObject(int movieId){
        URL url =  buildVideoUrl(movieId);
        JSONObject result = null;
        try {
            result = new JsonTask().execute(url).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;


    }

    public static JSONObject createReviewJsonObject(int movieId){
        URL url = buildCommentsUrl(movieId);
        JSONObject result = null;
        try {
            result = new JsonTask().execute(url).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;


    }

    public static class JsonLoader extends AsyncTaskLoader<JSONObject>{

        private Bundle bundle;
        private onStartLoadingListener onStartLoadingListener;


        public interface onStartLoadingListener{
            void onStartLoading();
        }

        public JsonLoader.onStartLoadingListener getOnStartLoadingListener() {
            return onStartLoadingListener;
        }

        public void setOnStartLoadingListener(JsonLoader.onStartLoadingListener onStartLoadingListener) {
            this.onStartLoadingListener = onStartLoadingListener;
        }


        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if(onStartLoadingListener != null){
                onStartLoadingListener.onStartLoading();
            }
            forceLoad();
        }


        public JsonLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }



        @Nullable
        @Override
            public JSONObject loadInBackground() {
                if (bundle == null){
                    return null;
                }



                String urlAsString = bundle.getString("url");
            URL url = null;
            try {
                url = new URL(urlAsString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = null;
            HttpURLConnection connection = null;
            StringBuilder stringBuilder = new StringBuilder();

            if(url == null){
                return null;
            }
            try {
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null){
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                jsonObject = new JSONObject(stringBuilder.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }finally {
                if(connection != null){
                    connection.disconnect();
                }
            }
            return jsonObject;
        }
    }

    private static class JsonTask extends AsyncTask<URL, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject jsonObject = null;
            HttpURLConnection connection = null;
            StringBuilder stringBuilder = new StringBuilder();

            if(urls == null || urls.length == 0){
                return null;
            }
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null){
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                jsonObject = new JSONObject(stringBuilder.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }finally {
                if(connection != null){
                    connection.disconnect();
                }
            }
            return jsonObject;
        }
    }




}
