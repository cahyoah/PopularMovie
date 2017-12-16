package wkwkw.asek.cataloguemovie.Loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import wkwkw.asek.cataloguemovie.Model.Film;
import wkwkw.asek.cataloguemovie.PencarianActivity;


public class FilmLoader extends AsyncTaskLoader<ArrayList<Film>> {
    private ArrayList<Film> mData;
    public boolean hasResult = false;

    public FilmLoader(final Context context) {
        super(context);
        onContentChanged();
    }



    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<Film> data) {
        mData = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            onReleaseResources(mData);
            mData = null;
            hasResult = false;
        }
    }

    @Override
    public ArrayList<Film> loadInBackground() {
         SyncHttpClient client = new SyncHttpClient();
        String url = null;
        if(getId()==1){
            url = "https://api.themoviedb.org/3/movie/popular?api_key=f2b2a982528076cddeafc7b7f49e83b3&language=en-US";

        }else if(getId()==2){
            url = "https://api.themoviedb.org/3/movie/top_rated?api_key=f2b2a982528076cddeafc7b7f49e83b3&language=en-US";

        }else if(getId() ==3){

           url = "https://api.themoviedb.org/3/search/movie?api_key=f2b2a982528076cddeafc7b7f49e83b3&language=en-US&query="+ PencarianActivity.pencarian;

        }

        final ArrayList<Film> filmItemses = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject film= list.getJSONObject(i);
                        Film weatherItems = new Film(film);
                        filmItemses.add(weatherItems);
                    }


                    Log.d("REQUEST SUCCESS","1");

                }catch (Exception e){

                    e.printStackTrace();

                    Log.d("REQUEST FAILED","1");

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


        return filmItemses;
    }

    protected void onReleaseResources(ArrayList<Film> data) {
        //nothing to do.
    }

    public ArrayList<Film> getResult() {
        return mData;
    }

}
