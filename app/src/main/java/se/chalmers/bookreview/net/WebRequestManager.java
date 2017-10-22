package se.chalmers.bookreview.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import se.chalmers.bookreview.model.Book;
import se.chalmers.bookreview.model.BookReview;

public class WebRequestManager {
    private static WebRequestManager instance;

    private RequestQueue requestQueue;

    private WebRequestManager() {

    }

    public static WebRequestManager getInstance() {
        if (instance == null) {
            instance = new WebRequestManager();
        }

        return instance;
    }

    public void initialize(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getAllBooks(int sectionId, final WebRequestHandler requestHandler) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, UrlBuilder.getAllBooksUrl(sectionId), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Book> books = new ArrayList<>();

                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject bookObject = response.getJSONObject(i);

                                // Get book data
                                Book book = new Book();
                                book.setId(bookObject.getInt("book_id"));
                                book.setTitle(bookObject.getString("title"));
                                book.setAuthor(bookObject.getString("fname") + " " + bookObject.getString("lname"));
                                book.setCoverImageUrl(bookObject.getString("cover_src"));
                                book.setRating((float) bookObject.getDouble("rate"));
                                book.setIsbn(bookObject.getString("isbn"));
                                //book.setDescription(bookObject.getString("description"));

                                books.add(book);
                            }

                            if (requestHandler != null) {
                                requestHandler.onSuccess(books);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (requestHandler != null) {
                    requestHandler.onFailure();
                }
            }
        });
        requestQueue.add(request);
    }

    public void getBookReviews(int bookId, final WebRequestHandler requestHandler) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, UrlBuilder.getBookReviewsUrl(bookId), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<BookReview> bookReviews = new ArrayList<>();

                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject bookReviewObject = response.getJSONObject(i);

                                // Get review data
                                BookReview bookReview = new BookReview();
                                bookReview.setId(bookReviewObject.getInt("book_review_id"));
                                bookReview.setRating((float) bookReviewObject.getDouble("rate"));
                                bookReview.setText(bookReviewObject.getString("comment"));
                                bookReview.setReviewDate(bookReviewObject.getString("date_of_review"));

                                bookReviews.add(bookReview);
                            }

                            if (requestHandler != null) {
                                requestHandler.onSuccess(bookReviews);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (requestHandler != null) {
                    requestHandler.onFailure();
                }
            }
        });
        requestQueue.add(request);
    }

    public interface WebRequestHandler {
        void onSuccess(Object data);

        void onFailure();
    }
}
