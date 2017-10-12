package se.chalmers.bookreview.model;

public class BookReview {
    private float rating;
    private Language language;
    private String text;

    public BookReview(float rating, Language language, String text) {
        this.rating = rating;
        this.language = language;
        this.text = text;
    }

    public float getRating() {
        return rating;
    }

    public Language getLanguage() {
        return language;
    }

    public String getText() {
        return text;
    }
}
