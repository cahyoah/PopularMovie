package wkwkw.asek.favouritefilm;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import wkwkw.asek.favouritefilm.Adapter.ListFilmAdapter;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private ListFilmAdapter listFilmAdapter;
    private ListView filmFavorit;

    private final int LOAD_FILM_ID = 110;
    private static final String AUTHORITY = "wkwkw.asek.cataloguemovie";
    private static final String BASE_PATH = "favouritemovie";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        filmFavorit = (ListView) findViewById(R.id.rv_film);
        listFilmAdapter = new ListFilmAdapter(this, null, true);
        filmFavorit.setAdapter(listFilmAdapter);
        filmFavorit.setOnItemClickListener(this);
        getSupportLoaderManager().initLoader(LOAD_FILM_ID, null, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_FILM_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listFilmAdapter.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listFilmAdapter.swapCursor(null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FILM_ID);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = (Cursor) listFilmAdapter.getItem(i);
        Intent intent = new Intent(MainActivity.this, DetailFilmActivity.class);
        intent.putExtra(DetailFilmActivity.ID_FILM, cursor.getInt(cursor.getColumnIndexOrThrow("id_film")));
        startActivity(intent);
    }
}
