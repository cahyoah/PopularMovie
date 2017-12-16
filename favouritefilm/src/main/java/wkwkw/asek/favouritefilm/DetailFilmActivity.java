package wkwkw.asek.favouritefilm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailFilmActivity extends AppCompatActivity  {
    private ImageView posterFilm;
    private TextView  tanggalRilis;
    private TextView deskripsiFilm;
    private TextView judulFilm;
    private int idFilm;
    private Button reload;
    public static String ID_FILM = "film_id";
    private TextView mEmptyStateTextView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("");
        posterFilm = (ImageView) findViewById(R.id.poster_film);
        tanggalRilis = (TextView) findViewById(R.id.tanggal_rilis_film);
        deskripsiFilm = (TextView) findViewById(R.id.deskripsi_film);
        judulFilm = (TextView) findViewById(R.id.judul_film);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        reload = (Button) findViewById(R.id.reload);

        idFilm = getIntent().getIntExtra(ID_FILM, 0);
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();



       if (activeNetwork != null && activeNetwork.isConnected()) {


            getFilmDetail(idFilm);
        } else {
           View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            reload.setVisibility(View.VISIBLE);

           mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View loadingIndicator1 = findViewById(R.id.loading_indicator);
                loadingIndicator1.setVisibility(View.VISIBLE);
                ConnectivityManager cm =
                        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {


                    getFilmDetail(idFilm);
                } else {
                   View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);
                    reload.setVisibility(View.VISIBLE);

                   mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
            }
        });

    }

    public void getFilmDetail (int idFilm){
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest newsReq = new JsonObjectRequest(Request.Method.GET,
                "https://api.themoviedb.org/3/movie/"+idFilm+"?api_key=f2b2a982528076cddeafc7b7f49e83b3&language=en-US",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject responseObject) {
                        try {

                            judulFilm.setText(responseObject.getString("original_title"));
                            deskripsiFilm.setText(responseObject.getString("overview"));
                            tanggalRilis.setText(responseObject.getString("release_date"));
                            url = "http://image.tmdb.org/t/p/original"+responseObject.getString("backdrop_path");
                            Glide.with(DetailFilmActivity.this)
                                    .load("http://image.tmdb.org/t/p/original"+responseObject.getString("backdrop_path"))

                                    .listener(new RequestListener<String, GlideDrawable>() {
                                        @Override
                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                            return false;
                                        }
                                    })
                                    .into(posterFilm);
                             View loadingIndicator = findViewById(R.id.loading_indicator);
                            loadingIndicator.setVisibility(View.GONE);
                            reload.setVisibility(View.GONE);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        mEmptyStateTextView.setText(R.string.no_earthquakes);
                        reload.setVisibility(View.VISIBLE);
                    }
                });

        queue.add(newsReq);


    }


    @Override
    public void onBackPressed() {
      finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }

        return super.onOptionsItemSelected(item);
    }




}
