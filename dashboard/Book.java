package dashboard;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private String author;
    private List<Double> ratings;
    private List<String> reviews;

    public Book(String title, String author) {
        this.title = (title.isEmpty() ? "Unknown" : title);
        this.author = (author.isEmpty() ? "Unknown" : author);
        this.ratings = new ArrayList<>();
        this.reviews = new ArrayList<>();
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

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public Book(String title, String author, List<Double> ratings, List<String> reviews) {
        this.title = title;
        this.author = author;
        this.ratings = ratings;
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", ratings=" + ratings + ", reviews=" + reviews + "]";
    }
}