package wkwkw.asek.cataloguemovie;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import wkwkw.asek.cataloguemovie.Adapter.ListFilmAdapter;
import wkwkw.asek.cataloguemovie.Model.Film;
import wkwkw.asek.cataloguemovie.helper.FavoritSQLite;

public class FilmFavoritActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Film>>{

    private FavoritSQLite favoritSQLite;



    private RecyclerView listFilmFavorit;
    private ListFilmAdapter adapter;
    private TextView emptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_favorit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");
        favoritSQLite = new FavoritSQLite(this);
        listFilmFavorit = (RecyclerView) findViewById(R.id.rv_film);
        emptyState = (TextView) findViewById(R.id.empty_view);

        if(favoritSQLite.getFilmFavorit().isEmpty()){
            emptyState.setText(getResources().getString(R.string.no_favourite));
        }
        ListFilmAdapter listFilmAdapter = new ListFilmAdapter(this, favoritSQLite.getFilmFavorit());
        adapter= new ListFilmAdapter();
        adapter.setData(favoritSQLite.getFilmFavorit());
        listFilmFavorit.setAdapter(listFilmAdapter);
        listFilmFavorit.setVisibility(View.VISIBLE);
        listFilmFavorit.setLayoutManager(new LinearLayoutManager(this));
        listFilmFavorit.setHasFixedSize(true);
        listFilmFavorit.setVisibility(View.VISIBLE);


    }
    @Override
    protected void onPostResume() {
        listFilmFavorit.setVisibility(View.GONE);
        if(favoritSQLite.getFilmFavorit().isEmpty()){
            emptyState.setText(getResources().getString(R.string.no_favourite));
        }
        ListFilmAdapter listFilmAdapter = new ListFilmAdapter(this, favoritSQLite.getFilmFavorit());
        adapter= new ListFilmAdapter();
        adapter.setData(favoritSQLite.getFilmFavorit());
        listFilmFavorit.setAdapter(listFilmAdapter);
        listFilmFavorit.setVisibility(View.VISIBLE);
        listFilmFavorit.setLayoutManager(new LinearLayoutManager(this));
        listFilmFavorit.setHasFixedSize(true);
        listFilmFavorit.setVisibility(View.VISIBLE);
        super.onPostResume();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<Film>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Film>> loader, ArrayList<Film> films) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Film>> loader) {

    }
}
