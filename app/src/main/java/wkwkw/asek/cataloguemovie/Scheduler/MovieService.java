package wkwkw.asek.cataloguemovie.Scheduler;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class MovieService extends IntentService {
    public MovieService(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("bejalan", "OriginService dijalankan");
        Intent notifyFinishIntent = new Intent("test");
        sendBroadcast(notifyFinishIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("dihancurkan", "onDestroy()");
    }
}
