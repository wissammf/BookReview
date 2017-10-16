package se.chalmers.bookreview.data;

public class UrlBuilder {
    private static final String SERVER_URL = "http://185.47.129.233:3000";

    public static String getAllBooksUrl(int sectionId) {
        return SERVER_URL + "/getBooks?section=" + sectionId;
    }

    public static String getBookReviewsUrl(int bookId) {
        return SERVER_URL + "/getReviews?book=" + bookId;
    }
}
