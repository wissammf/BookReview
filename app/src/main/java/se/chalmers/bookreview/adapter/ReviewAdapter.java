package se.chalmers.bookreview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import se.chalmers.bookreview.R;
import se.chalmers.bookreview.model.BookReview;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private ArrayList<BookReview> reviews;
    private View.OnClickListener onClickListener;

    public ReviewAdapter(ArrayList<BookReview> reviews, View.OnClickListener onClickListener) {
        this.reviews = reviews;
        this.onClickListener = onClickListener;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_review, parent, false);

        itemView.setOnClickListener(onClickListener);

        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        BookReview review = reviews.get(position);
        holder.setupView(review.getRating(), review.getLanguage().getName(), review.getText());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private RatingBar rbRating;
        private TextView tvLanguage;
        private TextView tvText;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            rbRating = itemView.findViewById(R.id.rb_rating);
            tvLanguage = itemView.findViewById(R.id.tv_language);
            tvText = itemView.findViewById(R.id.tv_text);
        }

        public void setupView(float rating, String language, String text) {
            rbRating.setRating(rating);
            tvLanguage.setText(language);
            tvText.setText(text);
        }
    }
}
