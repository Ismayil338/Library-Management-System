package dashboard;

import java.util.Arrays;

public class Book {
    public String title;
    public String author;
    public String[] review;
    public int[] rating;

    public Book(String title, String author, String[] review, int[] rating) {
        this.title = title;
        this.author = author;
        this.review = review;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String[] getReview() {
        return review;
    }

    public void setReview(String[] review) {
        this.review = review;
    }

    public int[] getRating() {
        return rating;
    }

    public void setRating(int[] rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", review=" + Arrays.toString(review) +
                ", rating=" + Arrays.toString(rating) +
                '}';
    }
}
