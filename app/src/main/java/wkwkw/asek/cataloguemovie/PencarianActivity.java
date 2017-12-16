package wkwkw.asek.cataloguemovie;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import wkwkw.asek.cataloguemovie.Adapter.ListFilmAdapter;
import wkwkw.asek.cataloguemovie.Loader.PencarianLoader;
import wkwkw.asek.cataloguemovie.Model.Film;

public class PencarianActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Film>>{
    public  static String pencarian;
    private RecyclerView listFilm;
    private Button btnReload;
    public static String judulCari;
    ListFilmAdapter adapter;
    private TextView mEmptyStateTextView;
    View loadingIndicator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pencarian");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        btnReload = (Button) findViewById(R.id.btn_reload);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listFilm = (RecyclerView) findViewById(R.id.rv_film);
        btnReload.setVisibility(View.GONE);
        loadingIndicator =  findViewById(R.id.loading_indicator);
        reloadData();
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnReload.setVisibility(View.GONE);
                reloadData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pencarian, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.hint_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pencarian = newText;
                listFilm.removeAllViews();
                listFilm.setVisibility(View.GONE);
                loadingIndicator.setVisibility(View.VISIBLE);
                reloadData();
                return true;
            }
        });
        return true;
    }

    public void reloadData(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            getLoaderManager().restartLoader(3, null,this);
            getLoaderManager().initLoader(3, null, this);



        } else {

            loadingIndicator.setVisibility(View.GONE);

            btnReload.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public Loader<ArrayList<Film>> onCreateLoader(int i, Bundle bundle) {
        return new PencarianLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Film>> loader, ArrayList<Film> films) {
        Log.d("Load Finish","1");
        loadingIndicator.setVisibility(View.GONE);

        if (films != null && !films.isEmpty()) {
            ListFilmAdapter listFilmAdapter = new ListFilmAdapter(this, films);
            adapter= new ListFilmAdapter();
            adapter.setData(films);
            btnReload.setVisibility(View.GONE);
            listFilm.setAdapter(listFilmAdapter);
            listFilm.setVisibility(View.VISIBLE);
            listFilm.setLayoutManager(new LinearLayoutManager(this));
            listFilm.setHasFixedSize(true);
            listFilm.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText("");
        }else{
            btnReload.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_data);


        }

    }


    @Override
    public void onLoaderReset(Loader<ArrayList<Film>> loader) {
        Log.d("Load Reset","1");

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PencarianActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            Intent intent = new Intent(PencarianActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
