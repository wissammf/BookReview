package se.chalmers.bookreview.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import se.chalmers.bookreview.R;
import se.chalmers.bookreview.adapter.ReviewAdapter;
import se.chalmers.bookreview.net.WebRequestManager;
import se.chalmers.bookreview.model.Book;
import se.chalmers.bookreview.model.BookReview;

public class BookDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String ADD_REVIEW_DIALOG_FRAGMENT_TAG = "BookDetailsActivity_ADD_REVIEW_DIALOG_FRAGMENT_TAG";

    private RecyclerView mRvReviews;
    private ReviewAdapter mAdapter;
    private Book mBook;
    private ArrayList<BookReview> mReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Get references to views
        ImageView ivCover = (ImageView) findViewById(R.id.iv_cover);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvAuthor = (TextView) findViewById(R.id.tv_author);
        TextView tvDescription = (TextView) findViewById(R.id.tv_description);
        RatingBar rbAverageRating = (RatingBar) findViewById(R.id.rb_average_rating);
        mRvReviews = (RecyclerView) findViewById(R.id.rv_reviews);

        // Get book from intent
        Intent intent = getIntent();
        mBook = (Book) intent.getSerializableExtra(getString(R.string.key_book));

        // Fill data
        Picasso.with(this)
                .load(mBook.getCoverImageUrl())
                .placeholder(R.drawable.book_cover_placeholder)
                .error(R.drawable.book_cover_unavailable)
                .into(ivCover);
        tvTitle.setText(mBook.getTitle());
        tvAuthor.setText(mBook.getAuthor());
        tvDescription.setText(mBook.getDescription());
        rbAverageRating.setRating(mBook.getRating());

        mReviews = new ArrayList<>();

        mAdapter = new ReviewAdapter(mReviews, this);
        mRvReviews.setAdapter(mAdapter);
        mRvReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Get reviews from server
        WebRequestManager.getInstance().getBookReviews(mBook.getId(), new WebRequestManager.WebRequestHandler() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();

            return true;
        }

        if (id == R.id.action_add_review) {
            FragmentManager fm = getSupportFragmentManager();
            AddReviewDialogFragment dialogFragment = AddReviewDialogFragment.newInstance(mBook.getId());
            dialogFragment.show(fm, "testing");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        BookReview clickedBookReview = mReviews.get(mRvReviews.indexOfChild(view));

        Toast.makeText(this, clickedBookReview.getLanguage().getName() + " " + clickedBookReview.getText().substring(0, 10), Toast.LENGTH_SHORT).show();
    }
}
