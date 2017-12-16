package wkwkw.asek.cataloguemovie.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import wkwkw.asek.cataloguemovie.Model.Film;
import wkwkw.asek.cataloguemovie.R;
import wkwkw.asek.cataloguemovie.helper.FavoritSQLite;

/**
 * Created by dicoding on 1/9/2017.
 */


class StackRemoteViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private List<Film> filmList = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    private FavoritSQLite favoritSQLite;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {



    }

    @Override
    public void onDataSetChanged() {
        getFavoriteMovies(mContext);
    }

    public void getFavoriteMovies(Context context){
        favoritSQLite = new FavoritSQLite(mContext);
        filmList.addAll(favoritSQLite.getFilmFavorit());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        System.out.println("panjang widget "+ filmList.size());
        return filmList.size();

    }

    @Override
    public RemoteViews getViewAt(int position) {
        favoritSQLite = new FavoritSQLite(mContext);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        try {
            rv.setImageViewBitmap(R.id.imageView,
                    Glide.with(mContext).load(filmList.get(position).getUrlPosterFilm()).asBitmap()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        rv.setTextViewText(R.id.judul_film_widget, filmList.get(position).getJudulFilm());

        Bundle extras = new Bundle();
        extras.putInt(FavouriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



}