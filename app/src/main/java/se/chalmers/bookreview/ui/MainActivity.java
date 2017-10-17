package se.chalmers.bookreview.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import se.chalmers.bookreview.R;
import se.chalmers.bookreview.adapter.BookAdapter;
import se.chalmers.bookreview.net.Consts;
import se.chalmers.bookreview.net.WebRequestManager;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            Toast.makeText(this, "Not implemented yet!", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Book clickedBook = mBooks.get(mRvBooks.indexOfChild(view));

        Intent intent = new Intent(this, BookDetailsActivity.class);
        intent.putExtra(getString(R.string.key_book), clickedBook);
        startActivity(intent);
    }
}
