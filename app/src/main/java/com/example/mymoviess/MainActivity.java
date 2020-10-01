package com.example.mymoviess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviess.Adapters.MoviesAdapter;
import com.example.mymoviess.Data.MovieViewModel;
import com.example.mymoviess.Network.JsonUtils;
import com.example.mymoviess.Network.NetworkUtils;

import org.json.JSONObject;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchSortType;
    private MoviesAdapter adapter;
    private TextView twPopularity;
    private TextView twTopRated;
    private MovieViewModel movieViewModel;
    private LoaderManager loaderManager;
    private static int page = 1;
    private static int methodOfSort;
    private boolean isLoading = false;
    private ProgressBar progressBar;

    private static final int LOADER_ID = 320;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);


        switchSortType = findViewById(R.id.switchSortType);
        RecyclerView recyclerView = findViewById(R.id.recyclerMovies);

        adapter = new MoviesAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, getColumnsCount()));
        recyclerView.setAdapter(adapter);

        switchSortType.setChecked(false);

        //получение данных и изменнение метода сортировки в зависимости от положения switch
        switchSortType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                page = 1;
                methodOfSort(b);
            }
        });

        twPopularity = findViewById(R.id.textViewPopularity);
        twTopRated = findViewById(R.id.textViewTopRated);
        progressBar = findViewById(R.id.progressBar);


        //получение индекса нажатого элемента ресайклера
        adapter.setOnPosterClickListener(new MoviesAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Movie movie = adapter.getMovies().get(position);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("id", movie.getMovieId());
                startActivity(intent);
            }
        });

        adapter.setOnReachEndListener(new MoviesAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                if (!isLoading) {
                    downloadData(methodOfSort, page);
                    Toast.makeText(MainActivity.this, "конец спискка", Toast.LENGTH_SHORT).show();
                }


            }
        });

        LiveData<List<Movie>> moviesFromLiveData = movieViewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (page == 1) {
                    adapter.setMovies(movies);
                }


            }
        });

        loaderManager = LoaderManager.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        downloadData(methodOfSort, page - 1);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("methodOfSort", methodOfSort);
        outState.putInt("methodOfSort", page);
    }


    //загрузка данных по методу сортировки и переданной странице
    public void methodOfSort(boolean sortType) {

        if (sortType) {
            methodOfSort = NetworkUtils.TOP_RATED;
            twTopRated.setTextColor(getResources().getColor(R.color.colorPink));
            twPopularity.setTextColor(getResources().getColor(R.color.colorWhite));
        } else {
            methodOfSort = NetworkUtils.POPULARITY;
            twPopularity.setTextColor(getResources().getColor(R.color.colorPink));
            twTopRated.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        downloadData(methodOfSort, page);


    }


    public void downloadData(int methodOfSort, int page) {
        URL url = NetworkUtils.buildUrl(methodOfSort, page);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(LOADER_ID, bundle, this);
    }

    //изменнение метода сортировки по нажатию на текст
    public void onClickTopRated(View view) {
        switchSortType.setChecked(true);
        methodOfSort(true);

    }

    //изменнение метода сортировки по нажатию на текст
    public void onClickPopularity(View view) {
        switchSortType.setChecked(false);
        methodOfSort(false);
    }

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


    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle bundle) {
        NetworkUtils.JsonLoader jsonLoader = new NetworkUtils.JsonLoader(this, bundle);
        jsonLoader.setOnStartLoadingListener(new NetworkUtils.JsonLoader.onStartLoadingListener() {
            @Override
            public void onStartLoading() {
                isLoading = true;
                progressBar.setVisibility(View.VISIBLE);

            }
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject jsonObject) {
        List<Movie> movies = JsonUtils.getMoviesFromJson(jsonObject);
        if (movies != null && !movies.isEmpty()) {
            if (page == 1) {
                movieViewModel.deleteAllMovies();
                adapter.clear();
            }
            for (int i = 0; i < movies.size(); i++) {
                movieViewModel.insertMovie(movies.get(i));
            }
            adapter.addMovies(movies);
            page++;

        }
        isLoading = false;
        progressBar.setVisibility(View.INVISIBLE);
        loaderManager.destroyLoader(LOADER_ID);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }

    private int getColumnsCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        if (width / 185 > 2) {
            return width / 185;
        } else {
            return 2;
        }

    }
}



