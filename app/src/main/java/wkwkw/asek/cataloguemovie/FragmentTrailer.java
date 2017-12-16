package wkwkw.asek.cataloguemovie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import wkwkw.asek.cataloguemovie.Adapter.ListTrailerAdapter;
import wkwkw.asek.cataloguemovie.Loader.TrailerLoader;
import wkwkw.asek.cataloguemovie.Model.Trailer;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTrailer extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Trailer>> {
    public RecyclerView rvTrailer;
    public static String idFilm;
    ListTrailerAdapter adapter;
    public FragmentTrailer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_fragment_trailer, container, false);
        rvTrailer = (RecyclerView) view.findViewById(R.id.rv_trailer);
        getLoaderManager().initLoader(1, null, this);
        return view;
    }

    @Override
    public Loader<ArrayList<Trailer>> onCreateLoader(int id, Bundle args) {

        return new TrailerLoader(getContext(), idFilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Trailer>> loader, ArrayList<Trailer> data) {
       // loadingIndicator.setVisibility(View.GONE);
        System.out.println("ukuran" + data.size());
        if (data != null && !data.isEmpty()) {

            ListTrailerAdapter listTrailerAdapter = new ListTrailerAdapter(getContext(), data);
            adapter= new ListTrailerAdapter();
            adapter.setData(data);
           // btnReload.setVisibility(View.GONE);
            rvTrailer.setAdapter(listTrailerAdapter);
            rvTrailer.setVisibility(View.VISIBLE);
            rvTrailer.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTrailer.setHasFixedSize(true);
            rvTrailer.setVisibility(View.VISIBLE);
            //mEmptyStateTextView.setText("");
        }else{
//            btnReload.setVisibility(View.VISIBLE);
//            mEmptyStateTextView.setText(R.string.no_earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Trailer>> loader) {

    }
}
