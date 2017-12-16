package wkwkw.asek.cataloguemovie;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.Switch;
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

import wkwkw.asek.cataloguemovie.Adapter.TabFragmentDetailFIlmAdapter;
import wkwkw.asek.cataloguemovie.helper.FavoritSQLite;
import wkwkw.asek.cataloguemovie.helper.FilmSession;

public class DetailFilmActivity extends AppCompatActivity {
    private ImageView posterFilm;
    private TextView tanggalRilis;
    private TextView deskripsiFilm;
    private Switch switchFavorit;
    private RatingBar rbRatingFilm;
    private FilmSession filmSession;
    private int idFilm;
    private MediaController mediaController;
    private ViewPager pager;
    // Declare some variables
    private ProgressDialog pDialog;
    private Button reload;
    public static String ID_FILM = "film_id";
    public static String JUDUL_FILM ="judul_film";
    private FavoritSQLite favoritSQLite;
    private TextView mEmptyStateTextView;
    private TabLayout tabs;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getIntent().getStringExtra(JUDUL_FILM));
        posterFilm = (ImageView) findViewById(R.id.poster_film);
        tanggalRilis = (TextView) findViewById(R.id.tanggal_rilis_film);
        deskripsiFilm = (TextView) findViewById(R.id.deskripsi_film);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        switchFavorit = (Switch) findViewById(R.id.switchFavorit);
        rbRatingFilm = (RatingBar) findViewById(R.id.rb_rating_film);
        reload = (Button) findViewById(R.id.reload);
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        FragmentTrailer.idFilm = Integer.toString(getIntent().getIntExtra(ID_FILM, 0));
        FragmentReviews.idFilm = Integer.toString(getIntent().getIntExtra(ID_FILM, 0));
        //set object adapter kedalam ViewPager

        pager.setAdapter(new TabFragmentDetailFIlmAdapter(getSupportFragmentManager()));

        //Manimpilasi sedikit untuk set TextColor pada Tab
        tabs.setTabTextColors(getResources().getColor(R.color.colorGray),
                getResources().getColor(android.R.color.background_dark));

        //set tab ke ViewPager
        tabs.setupWithViewPager(pager);

        //konfigurasi Gravity Fill untuk Tab berada di posisi yang proposional
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        idFilm = getIntent().getIntExtra(ID_FILM, 0);
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        filmSession = new FilmSession(this);
        favoritSQLite = new FavoritSQLite(this);
        if(filmSession.getIsFilmFavorit(Integer.toString(idFilm))){
            switchFavorit.setChecked(true);
        }
        switchFavorit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    filmSession.setFilmFavorit(Integer.toString(idFilm), true);
                    favoritSQLite.addFilm(idFilm,
                            getIntent().getStringExtra(JUDUL_FILM), url
                            ,tanggalRilis.getText().toString().trim(), deskripsiFilm.getText().toString().trim());
                    System.out.println(favoritSQLite.getFilmFavorit().size());

                }else{
                    filmSession.setFilmFavorit(Integer.toString(idFilm), false);
                    favoritSQLite.delete(idFilm);
                    System.out.println(favoritSQLite.getFilmFavorit().size());
                }
            }
        });

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

                            deskripsiFilm.setText(responseObject.getString("overview"));
                            tanggalRilis.setText(responseObject.getString("release_date"));
                            rbRatingFilm.setNumStars(10);
                            rbRatingFilm.setRating((float) responseObject.getDouble("vote_average"));
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
                        mEmptyStateTextView.setText(R.string.no_data);
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
