package wkwkw.asek.cataloguemovie.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import wkwkw.asek.cataloguemovie.helper.FavoritSQLite;

/**
 * Created by ASUS on 21/10/2017.
 */
public class FilmProvider extends ContentProvider {


    private static final String AUTHORITY = "wkwkw.asek.cataloguemovie";
    private static final String BASE_PATH = "favouritemovie";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );


    private static final int FILM = 1;
    private static final int FILM_ID = 2;


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH, FILM);
        uriMatcher.addURI(AUTHORITY,BASE_PATH + "/#", FILM_ID);
    }


    private SQLiteDatabase sqLiteDatabase;




    @Override
    public boolean onCreate() {
        FavoritSQLite databaseHelper = new FavoritSQLite(getContext());
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return true;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor = null;


        if (uriMatcher.match(uri) == FILM){
            cursor = sqLiteDatabase.query(FavoritSQLite.TABLE_FILM, null, null, null, null, null, FavoritSQLite.KEY_ID_FILM+" DESC");
        }


        return cursor;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long id = sqLiteDatabase.insert(FavoritSQLite.TABLE_FILM, null, contentValues);


        if (id > 0) {
            Uri mUri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(uri, null);
            return mUri;
        }
        throw new SQLException("Insertion Failed for URI :" + uri);
    }


    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int delCount = 0;
        switch (uriMatcher.match(uri)) {
            case FILM:
                delCount =  sqLiteDatabase.delete(FavoritSQLite.TABLE_FILM, s , strings);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return delCount;
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updCount = 0;
        switch (uriMatcher.match(uri)) {
            case FILM:
                updCount =  sqLiteDatabase.update(FavoritSQLite.TABLE_FILM, contentValues, s , strings);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updCount;
    }


}
