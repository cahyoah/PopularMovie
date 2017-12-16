package wkwkw.asek.cataloguemovie.helper;


/**
 * Created by ASUS on 16/08/2017.
 */
import android.content.Context;
import android.content.SharedPreferences;

public class FilmSession {
    // LogCat tag
    private static String TAG = FilmSession.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "CatalogueMovie";

    public FilmSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFilmFavorit(String idFilm, boolean isFavorit) {

        editor.putBoolean(idFilm, isFavorit);

        // commit changes
        editor.commit();
    }

    public boolean getIsFilmFavorit(String idFilm){
        return pref.getBoolean(idFilm, false);
    }
}