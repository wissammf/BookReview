package se.chalmers.bookreview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import se.chalmers.bookreview.R;
import se.chalmers.bookreview.model.Book;
import se.chalmers.bookreview.model.SortOption;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private ArrayList<Book> books;
    private View.OnClickListener onClickListener;
    private SortOption sortOption;

    public BookAdapter(Context context, ArrayList<Book> books, View.OnClickListener onClickListener) {
        this.context = context;
        this.books = new ArrayList<>(books);
        this.onClickListener = onClickListener;
    }

    public void updateData(ArrayList<Book> books) {
        if (books == null) return;

        this.books.clear();
        this.books.addAll(books);

        notifyDataSetChanged();
    }

    public void applySort(SortOption sortOption) {
        if (sortOption == this.sortOption) return;

        switch (sortOption) {
            case Default:
                Collections.sort(books, new Comparator<Book>() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        return Integer.valueOf(o1.getId()).compareTo(o2.getId());
                    }
                });
                break;
            case Title:
                Collections.sort(books, new Comparator<Book>() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                });
                break;
            case Rating:
                Collections.sort(books, new Comparator<Book>() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        return Float.valueOf(o1.getRating()).compareTo(o2.getRating());
                    }
                });
                break;
        }

        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);

        itemView.setOnClickListener(onClickListener);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.setupView(book.getCoverImageUrl(), book.getTitle(), book.getRating());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCover;
        private TextView tvTitle;
        private RatingBar rbAverageRating;

        public BookViewHolder(View itemView) {
            super(itemView);

            ivCover = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rbAverageRating = itemView.findViewById(R.id.rb_average_rating);
        }

        public void setupView(String coverImageUrl, String title, float rating) {
            Picasso.with(context)
                    .load(coverImageUrl)
                    .placeholder(R.drawable.book_cover_placeholder)
                    .error(R.drawable.book_cover_unavailable)
                    .into(ivCover);
            tvTitle.setText(title);
            rbAverageRating.setRating(rating);
        }
    }
}
