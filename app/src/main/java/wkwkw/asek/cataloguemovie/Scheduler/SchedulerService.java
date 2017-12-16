package wkwkw.asek.cataloguemovie.Scheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;
import wkwkw.asek.cataloguemovie.DetailFilmActivity;
import wkwkw.asek.cataloguemovie.R;


public class SchedulerService extends GcmTaskService {
    public static final String TAG = "GetFilm";


    public static String TAG_GET_FILM_LOG = "GetFilmTask";

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_GET_FILM_LOG)){
            getUpcomingFilm();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    private void getUpcomingFilm(){
        Log.d("GetFilm", "Running");
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=f2b2a982528076cddeafc7b7f49e83b3&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    int id = list.getJSONObject(0).getInt("id");
                    String judulFilm = list.getJSONObject(0).getString("title");

                    String title = judulFilm;
                    String message = judulFilm + " release hari ini";
                    int notifId = 102;

                    showNotification(getApplicationContext(), title, message, notifId, id);
                    Date d = new Date();
                    CharSequence s = DateFormat.format("yyyy-MM-dd", d.getTime());

                    for(int i=0; i<list.length();i++){
                        notifId++;
                        int id1 = list.getJSONObject(i).getInt("id");
                        String judulFilm1 = list.getJSONObject(i).getString("title");
                        String tanggalRilis1 = list.getJSONObject(i).getString("release_date");

                        if(s.equals(tanggalRilis1)){
                            System.out.println(notifId);
                            showNotification(getApplicationContext(), judulFilm1, judulFilm1+" release hari ini", notifId, id1);

                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("GetWeather", "Failed");
            }
        });
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        SchedulerTask mSchedulerTask = new SchedulerTask(this);
        mSchedulerTask.createPeriodicTask();
    }

    private void showNotification(Context context, String title, String message, int notifId, int id){
        Intent notifDetailIntent = new Intent(this, DetailFilmActivity.class);
        notifDetailIntent.putExtra(DetailFilmActivity.ID_FILM, id);

        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addParentStack(DetailFilmActivity.class)
                .addNextIntent(notifDetailIntent)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_access_alarm_black_36dp)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
