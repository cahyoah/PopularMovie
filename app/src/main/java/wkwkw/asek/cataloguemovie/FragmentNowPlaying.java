package wkwkw.asek.cataloguemovie;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import wkwkw.asek.cataloguemovie.Adapter.ListFilmAdapter;
import wkwkw.asek.cataloguemovie.Loader.FilmLoader;
import wkwkw.asek.cataloguemovie.Model.Film;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNowPlaying extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Film>> {

    private RecyclerView listFilm;
    private Button btnReload;
    public static String judulCari;
    ListFilmAdapter adapter;
    private TextView mEmptyStateTextView;
    View loadingIndicator;
    LoaderManager loaderManager;
    public FragmentNowPlaying() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_now_playing, container, false);
        btnReload = (Button) view.findViewById(R.id.btn_reload);
        mEmptyStateTextView = (TextView) view.findViewById(R.id.empty_view);
        listFilm = (RecyclerView) view.findViewById(R.id.rv_film);
        btnReload.setVisibility(View.GONE);
        loadingIndicator =  view.findViewById(R.id.loading_indicator);
        reloadData();
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnReload.setVisibility(View.GONE);
                reloadData();
            }
        });
        loaderManager = getLoaderManager();
        return view;
    }

    public void reloadData(){
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            getLoaderManager().initLoader(1, null, this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            btnReload.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<ArrayList<Film>> onCreateLoader(int i, Bundle bundle) {
        return new FilmLoader(this.getContext().getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Film>> loader, ArrayList<Film> films) {
        Log.d("Load Finish","1");
        loadingIndicator.setVisibility(View.GONE);
        if (films != null && !films.isEmpty()) {
            ListFilmAdapter listFilmAdapter = new ListFilmAdapter(getContext(), films);
            adapter= new ListFilmAdapter();
            adapter.setData(films);
            btnReload.setVisibility(View.GONE);
            listFilm.setAdapter(listFilmAdapter);
            listFilm.setVisibility(View.VISIBLE);
            listFilm.setLayoutManager(new LinearLayoutManager(getContext()));
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

}
