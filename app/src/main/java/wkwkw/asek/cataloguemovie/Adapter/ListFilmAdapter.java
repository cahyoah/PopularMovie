package wkwkw.asek.cataloguemovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import wkwkw.asek.cataloguemovie.DetailFilmActivity;
import wkwkw.asek.cataloguemovie.Model.Film;
import wkwkw.asek.cataloguemovie.R;


public class ListFilmAdapter extends RecyclerView.Adapter<ListFilmAdapter.ListFilmViewHolder> {


    private Context context;
    private List<Film> filmList;

    public ListFilmAdapter(Context context, List<Film> orderList) {
        this.context = context;
        this.filmList = orderList;
    }

    public ListFilmAdapter() {

    }
    public void setData(List<Film> items){
        filmList = items;
        notifyDataSetChanged();
    }

    @Override
    public ListFilmAdapter.ListFilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_card_view, parent, false);
        return new ListFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListFilmViewHolder holder, int position) {
        holder.judulFilm.setText(filmList.get(position).getJudulFilm());
        holder.deskripsiFilm.setText(filmList.get(position).getDeskripsiFilm());
        holder.tanggalRilis.setText(filmList.get(position).getTanggalRilis());

        Glide.with(context).load(filmList.get(position).getUrlPosterFilm())
                .into(holder.gambarFilm);

    }


    @Override
    public boolean onFailedToRecycleView(ListFilmViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if (filmList.size()>0){
            return filmList.size();
        }
        return  0;
    }

    public class ListFilmViewHolder extends RecyclerView.ViewHolder{
        TextView judulFilm;
        TextView deskripsiFilm;
        TextView tanggalRilis;
        ImageView gambarFilm;
        Button btnDetail, btnShare;

        public ListFilmViewHolder(final View itemView) {
            super(itemView);
             judulFilm = (TextView) itemView.findViewById(R.id.txt_judul_film);
            deskripsiFilm = (TextView) itemView.findViewById(R.id.txt_dekripsi_film);
            tanggalRilis = (TextView) itemView.findViewById(R.id.txt_tanggal_rilis);
            gambarFilm = (ImageView) itemView.findViewById(R.id.gambar_film);
            btnDetail = (Button) itemView.findViewById(R.id.btn_detail);
            btnShare = (Button) itemView.findViewById(R.id.btn_share);
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent notifDetailIntent = new Intent(context, DetailFilmActivity.class);
                    notifDetailIntent.putExtra(DetailFilmActivity.ID_FILM, filmList.get(getAdapterPosition()).getIdFilm());
                    notifDetailIntent.putExtra(DetailFilmActivity.JUDUL_FILM, filmList.get(getAdapterPosition()).getJudulFilm());
                    context.startActivity(notifDetailIntent);
                }
            });
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, filmList.get(getAdapterPosition()).getJudulFilm()+"Tersedia Sekarang");
                    sendIntent.setType("text/plain");
                    context.startActivity(sendIntent);
                }
            });
        }
    }
}

