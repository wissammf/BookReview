package se.chalmers.bookreview.model;

import java.io.Serializable;

public class Book implements Serializable {

    /**
     * Attributes for book Class
     */
    private String author;
    private String title;
    private float rating;
    private String isbn;
    private String course;
    private String coverImageUrl;

    /**
     * The constructor of Book class
     * The default constructor
     */
    public Book() {
    }

    /**
            * The constructor of Book class
     * The Parameterized constructor
     */

    public Book(Book book) {
        author  = book.getAuthor();
        title   = book.getTitle();
        rating = book.getRating();
        isbn    = book.getIsbn();
        course  = book.getCourse();
    }

    /**
     * methods which are setter and getter for the all attributes
     */
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

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

}

