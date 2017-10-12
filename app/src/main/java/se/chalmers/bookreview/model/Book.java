package se.chalmers.bookreview.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String author;
    private String title;
    private float rating;
    private String isbn;
    private String description;
    private String coverImageUrl;

    public Book() {
    }

    public Book(Book book) {
        author  = book.getAuthor();
        title   = book.getTitle();
        rating = book.getRating();
        isbn    = book.getIsbn();
        description = book.getDescription();
        description = book.getDescription();
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }


    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

}

