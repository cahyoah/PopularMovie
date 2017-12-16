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

import wkwkw.asek.cataloguemovie.Adapter.ListReviewAdapter;
import wkwkw.asek.cataloguemovie.Loader.ReviewLoader;
import wkwkw.asek.cataloguemovie.Model.Review;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentReviews extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Review>> {

    public RecyclerView rvReviews;
    public static String idFilm;
    ListReviewAdapter adapter;
    public FragmentReviews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_reviews, container, false);
        rvReviews = (RecyclerView) view.findViewById(R.id.rv_reviews);
        getLoaderManager().initLoader(1, null, this);
        return  view;
    }

    @Override
    public Loader<ArrayList<Review>> onCreateLoader(int id, Bundle args) {
        return new ReviewLoader(getContext(), idFilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> data) {
        System.out.println("ukuran" + data.size());
        if (data != null && !data.isEmpty()) {

            ListReviewAdapter listReviewAdapter = new ListReviewAdapter(getContext(), data);
            adapter= new ListReviewAdapter();
            adapter.setData(data);
            // btnReload.setVisibility(View.GONE);
            rvReviews.setAdapter(listReviewAdapter);
            rvReviews.setVisibility(View.VISIBLE);
            rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
            rvReviews.setHasFixedSize(true);
            rvReviews.setVisibility(View.VISIBLE);
            //mEmptyStateTextView.setText("");
        }else{
//            btnReload.setVisibility(View.VISIBLE);
//            mEmptyStateTextView.setText(R.string.no_earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Review>> loader) {

    }
}
