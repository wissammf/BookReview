package se.chalmers.bookreview.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import se.chalmers.bookreview.R;
import se.chalmers.bookreview.adapter.BookAdapter;
import se.chalmers.bookreview.data.Consts;
import se.chalmers.bookreview.data.WebRequestManager;
import se.chalmers.bookreview.model.Book;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRvBooks;
    private BookAdapter mAdapter;
    private ArrayList<Book> mBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvBooks = (RecyclerView) findViewById(R.id.rv_books);

        mBooks = new ArrayList<>();
        /*Book b1 = new Book();
        b1.setTitle("book 1");
        b1.setRating(3.2f);
        b1.setCoverImageUrl("https://about.canva.com/wp-content/uploads/sites/3/2015/01/children_bookcover.png");
        b1.setDescription("Lorem ipsum dolor sit amet, ad rebum illum splendide per, eu mea accusamus concludaturque. Est novum recusabo philosophia ea, constituam accommodare ullamcorper ea pro, lorem detracto et est. Iriure placerat ei vim, qui suas gubergren adolescens ea. At minim primis mediocrem eos, et novum putent noster vim.\n" +
                "Eu his euismod detracto, at cibo petentium corrumpit pri. Amet putant deserunt ut eum. An vix modus expetenda, oblique epicuri disputationi cum at. An has consul imperdiet democritum, ut vis appetere indoctum. Cu his dicta pertinax vulputate, ex clita aliquip officiis vim, sea hinc numquam et.");
        mBooks.add(b1);

        Book b2 = new Book();
        b2.setTitle("book 2");
        b2.setRating(2f);
        b2.setCoverImageUrl("https://creativesessions.s3.amazonaws.com/content/2010/cs3_digital_illustration/article_children_book_covers/32-digital-illustrated-childrens-book-cover.jpg");
        b2.setDescription("Lorem ipsum dolor sit amet, ad rebum illum splendide per, eu mea accusamus concludaturque. Est novum recusabo philosophia ea, constituam accommodare ullamcorper ea pro, lorem detracto et est. Iriure placerat ei vim, qui suas gubergren adolescens ea. At minim primis mediocrem eos, et novum putent noster vim.\n" +
                "Eu his euismod detracto, at cibo petentium corrumpit pri. Amet putant deserunt ut eum. An vix modus expetenda, oblique epicuri disputationi cum at. An has consul imperdiet democritum, ut vis appetere indoctum. Cu his dicta pertinax vulputate, ex clita aliquip officiis vim, sea hinc numquam et.");
        mBooks.add(b2);

        Book b3 = new Book();
        b3.setTitle("book 3");
        b3.setRating(3.2f);
        b3.setCoverImageUrl("https://about.canva.com/wp-content/uploads/sites/3/2015/01/children_bookcover.png");
        b3.setDescription("Lorem ipsum dolor sit amet, ad rebum illum splendide per, eu mea accusamus concludaturque. Est novum recusabo philosophia ea, constituam accommodare ullamcorper ea pro, lorem detracto et est. Iriure placerat ei vim, qui suas gubergren adolescens ea. At minim primis mediocrem eos, et novum putent noster vim.\n" +
                "Eu his euismod detracto, at cibo petentium corrumpit pri. Amet putant deserunt ut eum. An vix modus expetenda, oblique epicuri disputationi cum at. An has consul imperdiet democritum, ut vis appetere indoctum. Cu his dicta pertinax vulputate, ex clita aliquip officiis vim, sea hinc numquam et.");
        mBooks.add(b3);

        Book b4 = new Book();
        b4.setTitle("book 4");
        b4.setRating(2f);
        b4.setCoverImageUrl("https://creativesessions.s3.amazonaws.com/content/2010/cs3_digital_illustration/article_children_book_covers/32-digital-illustrated-childrens-book-cover.jpg");
        b4.setDescription("Lorem ipsum dolor sit amet, ad rebum illum splendide per, eu mea accusamus concludaturque. Est novum recusabo philosophia ea, constituam accommodare ullamcorper ea pro, lorem detracto et est. Iriure placerat ei vim, qui suas gubergren adolescens ea. At minim primis mediocrem eos, et novum putent noster vim.\n" +
                "Eu his euismod detracto, at cibo petentium corrumpit pri. Amet putant deserunt ut eum. An vix modus expetenda, oblique epicuri disputationi cum at. An has consul imperdiet democritum, ut vis appetere indoctum. Cu his dicta pertinax vulputate, ex clita aliquip officiis vim, sea hinc numquam et.");
        mBooks.add(b4);

        Book b5 = new Book();
        b5.setTitle("book 5");
        b5.setRating(3.2f);
        b5.setCoverImageUrl("https://about.canva.com/wp-content/uploads/sites/3/2015/01/children_bookcover.png");
        b5.setDescription("Lorem ipsum dolor sit amet, ad rebum illum splendide per, eu mea accusamus concludaturque. Est novum recusabo philosophia ea, constituam accommodare ullamcorper ea pro, lorem detracto et est. Iriure placerat ei vim, qui suas gubergren adolescens ea. At minim primis mediocrem eos, et novum putent noster vim.\n" +
                "Eu his euismod detracto, at cibo petentium corrumpit pri. Amet putant deserunt ut eum. An vix modus expetenda, oblique epicuri disputationi cum at. An has consul imperdiet democritum, ut vis appetere indoctum. Cu his dicta pertinax vulputate, ex clita aliquip officiis vim, sea hinc numquam et.");
        mBooks.add(b5);

        Book b6 = new Book();
        b6.setTitle("book 6");
        b6.setRating(2f);
        b6.setCoverImageUrl("https://creativesessions.s3.amazonaws.com/content/2010/cs3_digital_illustration/article_children_book_covers/32-digital-illustrated-childrens-book-cover.jpg");
        b6.setDescription("Lorem ipsum dolor sit amet, ad rebum illum splendide per, eu mea accusamus concludaturque. Est novum recusabo philosophia ea, constituam accommodare ullamcorper ea pro, lorem detracto et est. Iriure placerat ei vim, qui suas gubergren adolescens ea. At minim primis mediocrem eos, et novum putent noster vim.\n" +
                "Eu his euismod detracto, at cibo petentium corrumpit pri. Amet putant deserunt ut eum. An vix modus expetenda, oblique epicuri disputationi cum at. An has consul imperdiet democritum, ut vis appetere indoctum. Cu his dicta pertinax vulputate, ex clita aliquip officiis vim, sea hinc numquam et.");
        mBooks.add(b6);*/

        mAdapter = new BookAdapter(this, mBooks, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvBooks.setAdapter(mAdapter);
        mRvBooks.setLayoutManager(layoutManager);

        // Get books from server
        WebRequestManager.getInstance().getAllBooks(Consts.SECTION_ID, new WebRequestManager.WebRequestHandler() {
            @Override
            public void onSuccess(Object data) {
                //noinspection unchecked
                mBooks = (ArrayList<Book>) data;

                mAdapter.updateData(mBooks);
            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, R.string.error_get_books, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Book clickedBook = mBooks.get(mRvBooks.indexOfChild(view));

        Intent intent = new Intent(this, BookDetailsActivity.class);
        intent.putExtra(getString(R.string.key_book), clickedBook);
        startActivity(intent);
    }
}
