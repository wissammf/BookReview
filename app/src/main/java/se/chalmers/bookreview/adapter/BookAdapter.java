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
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    private Context context;
    private ArrayList<Book> books;
    private ArrayList<Book> filteredBooks;
    private OnItemClickListener onItemClickListener;
    private SortOption sortOption;
    private String query;

    public BookAdapter(Context context, ArrayList<Book> books, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.books = new ArrayList<>(books);
        this.filteredBooks = new ArrayList<>(books);
        this.onItemClickListener = onItemClickListener;

        this.sortOption = SortOption.Default;
    }

    public void updateData(ArrayList<Book> books) {
        if (books == null) return;

        this.books.clear();
        this.books.addAll(books);

        applySearch(this.query);
        applySort(this.sortOption);

        notifyDataSetChanged();
    }

    public void applySort(SortOption sortOption) {
        if (sortOption == this.sortOption) return;

        this.sortOption = sortOption;

        switch (sortOption) {
            case Default:
                Collections.sort(filteredBooks, new Comparator<Book>() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        return Integer.valueOf(o1.getId()).compareTo(o2.getId());
                    }
                });
                break;
            case Title:
                Collections.sort(filteredBooks, new Comparator<Book>() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                });
                break;
            case Rating:
                Collections.sort(filteredBooks, new Comparator<Book>() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        return Float.valueOf(o2.getRating()).compareTo(o1.getRating());
                    }
                });
                break;
        }

        notifyDataSetChanged();
    }

    public void applySearch(String query) {
        this.query = query;

        this.filteredBooks.clear();

        if (query == null || query.isEmpty()) {
            this.filteredBooks.addAll(books);
        } else {
            query = query.toLowerCase();
            for (Book book : books) {
                if (book.getTitle() != null && book.getTitle().toLowerCase().contains(query)) {
                    this.filteredBooks.add(book);
                    continue;
                }
                if (book.getDescription() != null && book.getDescription().toLowerCase().contains(query)) {
                    this.filteredBooks.add(book);
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = filteredBooks.get(position);
        holder.setupView(book, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return filteredBooks.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCover;
        private TextView tvTitle;
        private RatingBar rbAverageRating;

        BookViewHolder(View itemView) {
            super(itemView);

            ivCover = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rbAverageRating = itemView.findViewById(R.id.rb_average_rating);
        }

        void setupView(final Book book, final OnItemClickListener onItemClickListener) {
            Picasso.with(context)
                    .load(book.getCoverImageUrl())
                    .placeholder(R.drawable.book_cover_placeholder)
                    .error(R.drawable.book_cover_unavailable)
                    .into(ivCover);
            tvTitle.setText(book.getTitle());
            rbAverageRating.setRating(book.getRating());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(book);
                }
            });
        }
    }
}
