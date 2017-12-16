package wkwkw.asek.cataloguemovie.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wkwkw.asek.cataloguemovie.Model.Trailer;
import wkwkw.asek.cataloguemovie.R;

/**
 * Created by ASUS on 16/12/2017.
 */

public class ListTrailerAdapter extends RecyclerView.Adapter<ListTrailerAdapter.ListTrailerViewHolder> {


    private Context context;
    private List<Trailer> TrailerList;

    public ListTrailerAdapter(Context context, List<Trailer> orderList) {
        this.context = context;
        this.TrailerList = orderList;
    }

    public ListTrailerAdapter() {

    }
    public void setData(List<Trailer> items){
        TrailerList = items;
        notifyDataSetChanged();
    }

    @Override
    public ListTrailerAdapter.ListTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_trailer, parent, false);
        return new ListTrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListTrailerViewHolder holder, final int position) {
        holder.judulTrailer.setText("Trailer "+(position+1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + TrailerList.get(position).getKeyTrailer()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + TrailerList.get(position).getKeyTrailer()));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }
            }
        });

    }


    @Override
    public boolean onFailedToRecycleView(ListTrailerViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if (TrailerList.size()>0){
            return TrailerList.size();
        }
        return  0;
    }

    public class ListTrailerViewHolder extends RecyclerView.ViewHolder{
        TextView judulTrailer;


        public ListTrailerViewHolder(final View itemView) {
            super(itemView);
            judulTrailer = (TextView) itemView.findViewById(R.id.tv_trailer);

        }
    }
}