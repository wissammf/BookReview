package se.chalmers.bookreview.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import se.chalmers.bookreview.R;
import se.chalmers.bookreview.adapter.BookAdapter;
import se.chalmers.bookreview.model.Book;
import se.chalmers.bookreview.model.SortOption;
import se.chalmers.bookreview.net.Consts;
import se.chalmers.bookreview.net.WebRequestManager;

import android.support.v7.widget.SearchView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRvBooks;
    private BookAdapter mAdapter;
    private ArrayList<Book> mBooks;
    private SearchView mSearchView;

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
        getBooksFromServer();
    }

    private void getBooksFromServer() {
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

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) myActionMenuItem.getActionView();
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.applySearch(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.sort);
            builder.setTitle(R.string.dialog_title_sort_option);

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
            arrayAdapter.add(getString(R.string.sort_option_default));
            arrayAdapter.add(getString(R.string.sort_order_title));
            arrayAdapter.add(getString(R.string.sort_order_rating));

            builder.setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            mAdapter.applySort(SortOption.Default);
                            break;
                        case 1:
                            mAdapter.applySort(SortOption.Title);
                            break;
                        case 2:
                            mAdapter.applySort(SortOption.Rating);
                            break;
                    }
                }
            });
            builder.show();

            return true;
        }

        if (id == R.id.action_refresh) {
            getBooksFromServer();

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
