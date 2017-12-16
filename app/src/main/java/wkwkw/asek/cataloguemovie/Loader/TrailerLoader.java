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
import wkwkw.asek.cataloguemovie.Model.Trailer;

/**
 * Created by ASUS on 16/12/2017.
 */

public class TrailerLoader extends AsyncTaskLoader<ArrayList<Trailer>> {
    private ArrayList<Trailer> mData;
    public boolean hasResult = false;
    public String idFilm;

    public TrailerLoader(final Context context, String idFilm) {
        super(context);
        onContentChanged();
        this.idFilm= idFilm;
    }



    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<Trailer> data) {
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
    public ArrayList<Trailer> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        String url = "http://api.themoviedb.org/3/movie/"+idFilm+"/videos?api_key=f2b2a982528076cddeafc7b7f49e83b3";


        final ArrayList<Trailer> trailerArrayList = new ArrayList<>();
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
                        JSONObject trailerObject= list.getJSONObject(i);
                        Trailer trailer = new Trailer(trailerObject);
                        trailerArrayList.add(trailer);
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


        return trailerArrayList;
    }

    protected void onReleaseResources(ArrayList<Trailer> data) {
        //nothing to do.
    }

    public ArrayList<Trailer> getResult() {
        return mData;
    }

}
