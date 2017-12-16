package wkwkw.asek.favouritefilm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import wkwkw.asek.favouritefilm.DetailFilmActivity;
import wkwkw.asek.favouritefilm.R;


public class ListFilmAdapter extends CursorAdapter {



    // Login Table Columns names
    public static final String KEY_ID_FILM = "id_film";
    private static final String KEY_JUDUL_FILM = "judul_film";
    private static final String KEY_POSTER_FILM = "poster_film";
    private static final String KEY_TANGGALRILIS_FILM = "tanggalrilis_film";
    private static final String KEY_DESKRIPSI_FILM = "deskripsi_film";


    public ListFilmAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.film_card_view, viewGroup, false);
        return view;
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View itemView, final Context context, final Cursor cursor) {
        if (cursor != null){
            TextView judulFilm = (TextView) itemView.findViewById(R.id.txt_judul_film);
            TextView deskripsiFilm = (TextView) itemView.findViewById(R.id.txt_dekripsi_film);
            TextView tanggalRilis = (TextView) itemView.findViewById(R.id.txt_tanggal_rilis);
            ImageView gambarFilm = (ImageView) itemView.findViewById(R.id.gambar_film);
            Button btnDetail = (Button) itemView.findViewById(R.id.btn_detail);
            Button btnShare = (Button) itemView.findViewById(R.id.btn_share);
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent notifDetailIntent = new Intent(context, DetailFilmActivity.class);
                    notifDetailIntent.putExtra(DetailFilmActivity.ID_FILM,cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID_FILM)));
                    context.startActivity(notifDetailIntent);
                }
            });
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, cursor.getString(cursor.getColumnIndexOrThrow(KEY_JUDUL_FILM))+"Tersedia Sekarang");
                    sendIntent.setType("text/plain");
                    context.startActivity(sendIntent);
                }
            });
            System.out.println("akses");
            judulFilm.setText(cursor.getString(cursor.getColumnIndexOrThrow(KEY_JUDUL_FILM)));
            deskripsiFilm.setText(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESKRIPSI_FILM)));
            tanggalRilis.setText(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TANGGALRILIS_FILM)));
            Glide.with(context).load(cursor.getString(cursor.getColumnIndexOrThrow(KEY_POSTER_FILM)))
                    .into(gambarFilm);
        }
    }
}
