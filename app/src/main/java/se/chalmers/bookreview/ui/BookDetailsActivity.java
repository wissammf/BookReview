package se.chalmers.bookreview.ui;

import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import se.chalmers.bookreview.R;
import se.chalmers.bookreview.adapter.ReviewAdapter;
import se.chalmers.bookreview.data.WebRequestManager;
import se.chalmers.bookreview.model.Book;
import se.chalmers.bookreview.model.BookReview;
import se.chalmers.bookreview.model.Language;

public class BookDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRvReviews;
    private ReviewAdapter mAdapter;
    private ArrayList<BookReview> mReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        // Get references to views
        ImageView ivCover = (ImageView) findViewById(R.id.iv_cover);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvAuthor = (TextView) findViewById(R.id.tv_author);
        TextView tvDescription = (TextView) findViewById(R.id.tv_description);
        RatingBar rbAverageRating = (RatingBar) findViewById(R.id.rb_average_rating);
        mRvReviews = (RecyclerView) findViewById(R.id.rv_reviews);

        // Get book from intent
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra(getString(R.string.key_book));

        // Fill data
        Picasso.with(this)
                .load(book.getCoverImageUrl())
                .placeholder(R.drawable.book_cover_placeholder)
                .error(R.drawable.book_cover_unavailable)
                .into(ivCover);
        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvDescription.setText(book.getDescription());
        rbAverageRating.setRating(book.getRating());

        // Show reviews
        mReviews = new ArrayList<>();
        /*mReviews.add(new BookReview(2.5f, new Language(1, "English"), "1 Lorem ipsum dolor sit amet, ad rebum illum splendide per, eu mea accusamus concludaturque. Est novum recusabo philosophia ea, constituam accommodare ullamcorper ea pro, lorem detracto et est. Iriure placerat ei vim, qui suas gubergren adolescens ea. At minim primis mediocrem eos, et novum putent noster vim.\n" +
                "Eu his euismod detracto, at cibo petentium corrumpit pri. Amet putant deserunt ut eum. An vix modus expetenda, oblique epicuri disputationi cum at. An has consul imperdiet democritum, ut vis appetere indoctum. Cu his dicta pertinax vulputate, ex clita aliquip officiis vim, sea hinc numquam et."));
        mReviews.add(new BookReview(4f, new Language(2, "Swedish"), "2 Lorem ipsum dolor sit amet, ad rebum illum splendide per, eu mea accusamus concludaturque. Est novum recusabo philosophia ea, constituam accommodare ullamcorper ea pro, lorem detracto et est. Iriure placerat ei vim, qui suas gubergren adolescens ea. At minim primis mediocrem eos, et novum putent noster vim.\n" +
                "Eu his euismod detracto, at cibo petentium corrumpit pri. Amet putant deserunt ut eum. An vix modus expetenda, oblique epicuri disputationi cum at. An has consul imperdiet democritum, ut vis appetere indoctum. Cu his dicta pertinax vulputate, ex clita aliquip officiis vim, sea hinc numquam et."));
        mReviews.add(new BookReview(1.5f, new Language(1, "English"), "3 Lorem ipsum dolor sit amet, ad rebum illum splendide per, eu mea accusamus concludaturque. Est novum recusabo philosophia ea, constituam accommodare ullamcorper ea pro, lorem detracto et est. Iriure placerat ei vim, qui suas gubergren adolescens ea. At minim primis mediocrem eos, et novum putent noster vim.\n" +
                "Eu his euismod detracto, at cibo petentium corrumpit pri. Amet putant deserunt ut eum. An vix modus expetenda, oblique epicuri disputationi cum at. An has consul imperdiet democritum, ut vis appetere indoctum. Cu his dicta pertinax vulputate, ex clita aliquip officiis vim, sea hinc numquam et."));
        mReviews.add(new BookReview(5f, new Language(3, "French"), "4 Lorem ipsum dolor sit amet, ad rebum illum splendide per, eu mea accusamus concludaturque. Est novum recusabo philosophia ea, constituam accommodare ullamcorper ea pro, lorem detracto et est. Iriure placerat ei vim, qui suas gubergren adolescens ea. At minim primis mediocrem eos, et novum putent noster vim.\n" +
                "Eu his euismod detracto, at cibo petentium corrumpit pri. Amet putant deserunt ut eum. An vix modus expetenda, oblique epicuri disputationi cum at. An has consul imperdiet democritum, ut vis appetere indoctum. Cu his dicta pertinax vulputate, ex clita aliquip officiis vim, sea hinc numquam et."));*/

        mAdapter = new ReviewAdapter(mReviews, this);
        mRvReviews.setAdapter(mAdapter);
        mRvReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Get reviews from server
        WebRequestManager.getInstance().getBookReviews(book.getId(), new WebRequestManager.WebRequestHandler() {
            @Override
            public void onSuccess(Object data) {
                //noinspection unchecked
                mReviews = (ArrayList<BookReview>) data;

                mAdapter.updateData(mReviews);
            }

            @Override
            public void onFailure() {
                Toast.makeText(BookDetailsActivity.this, R.string.error_get_reviews, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        BookReview clickedBookReview = mReviews.get(mRvReviews.indexOfChild(view));

        Toast.makeText(this, clickedBookReview.getLanguage().getName() + " " + clickedBookReview.getText().substring(0, 10), Toast.LENGTH_SHORT).show();
    }
}
