package wkwkw.asek.cataloguemovie.helper;

/**
 * Created by ASUS on 19/10/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import wkwkw.asek.cataloguemovie.Model.Film;

public class FavoritSQLite extends SQLiteOpenHelper {

    private static final String TAG = FavoritSQLite.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "catalogue_film";

    // Login table name
    public static final String TABLE_FILM = "film";

    // Login Table Columns names
    private static final String KEY_ID = "_id";
    public static final String KEY_ID_FILM = "id_film";
    private static final String KEY_JUDUL_FILM = "judul_film";
    private static final String KEY_POSTER_FILM = "poster_film";
    private static final String KEY_TANGGALRILIS_FILM = "tanggalrilis_film";
    private static final String KEY_DESKRIPSI_FILM = "deskripsi_film";

    public FavoritSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_FILM + "("+KEY_ID+" integer primary key autoincrement, "
                + KEY_ID_FILM + " INTEGER," + KEY_JUDUL_FILM + " TEXT," + KEY_POSTER_FILM +" TEXT,"
                + KEY_TANGGALRILIS_FILM + " TEXT," + KEY_DESKRIPSI_FILM + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_FILM);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addFilm(int idFilm, String judulFilm, String posterFilm, String tanggalRilis, String deskripsiFilm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_FILM, idFilm); // Name
        values.put(KEY_JUDUL_FILM, judulFilm);
        values.put(KEY_POSTER_FILM, posterFilm); // Email
        values.put(KEY_TANGGALRILIS_FILM, tanggalRilis); // Email
        values.put(KEY_DESKRIPSI_FILM, deskripsiFilm); // Created At


        // Inserting Row

        long id = db.insert(TABLE_FILM, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New film inserted into sqlite: " + id);
    }


    /**
     * Getting user data from database
     * */
    public ArrayList<Film> getFilmFavorit() {
       ArrayList<Film> filmFavorit = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FILM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for(int i =0; i< cursor.getCount();i++){
                Film film = new Film(cursor.getInt(1), cursor.getString(3), cursor.getString(2) ,cursor.getString(5), cursor.getString(4));
                filmFavorit.add(film);
                cursor.moveToNext();
            }


        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + filmFavorit.toString());

        return filmFavorit;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_FILM, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void delete(int idFilm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_FILM+" where id_film='"+idFilm+"'");
    }

}