package wkwkw.asek.cataloguemovie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wkwkw.asek.cataloguemovie.Model.Review;
import wkwkw.asek.cataloguemovie.R;

/**
 * Created by ASUS on 16/12/2017.
 */

public class ListReviewAdapter extends RecyclerView.Adapter<ListReviewAdapter.ListReviewViewHolder> {


    private Context context;
    private List<Review> ReviewList;

    public ListReviewAdapter(Context context, List<Review> orderList) {
        this.context = context;
        this.ReviewList = orderList;
    }

    public ListReviewAdapter() {

    }
    public void setData(List<Review> items){
        ReviewList = items;
        notifyDataSetChanged();
    }

    @Override
    public ListReviewAdapter.ListReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_reviews, parent, false);
        return new ListReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListReviewViewHolder holder, final int position) {
        holder.tvAuthor.setText(ReviewList.get(position).getAuthor());
        holder.tvContent.setText(ReviewList.get(position).getContent());

    }


    @Override
    public boolean onFailedToRecycleView(ListReviewViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if (ReviewList.size()>0){
            return ReviewList.size();
        }
        return  0;
    }

    public class ListReviewViewHolder extends RecyclerView.ViewHolder{
        TextView tvAuthor;
        TextView tvContent;


        public ListReviewViewHolder(final View itemView) {
            super(itemView);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);

        }
    }
}