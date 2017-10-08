package se.chalmers.bookreview.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import se.chalmers.bookreview.R;
import se.chalmers.bookreview.adapter.BookAdapter;
import se.chalmers.bookreview.model.Book;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvBooks;
    private BookAdapter mAdapter;
    private ArrayList<Book> mBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvBooks = (RecyclerView) findViewById(R.id.rv_books);

        mBooks = new ArrayList<>();
        Book b1 = new Book();
        b1.setTitle("book 1");
        b1.setRating(3.2f);
        b1.setCoverImageUrl("https://about.canva.com/wp-content/uploads/sites/3/2015/01/children_bookcover.png");
        mBooks.add(b1);

        Book b2 = new Book();
        b2.setTitle("book 2");
        b2.setRating(2f);
        b2.setCoverImageUrl("https://creativesessions.s3.amazonaws.com/content/2010/cs3_digital_illustration/article_children_book_covers/32-digital-illustrated-childrens-book-cover.jpg");
        mBooks.add(b2);

        mAdapter = new BookAdapter(this, mBooks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvBooks.setAdapter(mAdapter);
        mRvBooks.setLayoutManager(layoutManager);
    }
}
