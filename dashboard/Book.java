package dashboard;

import java.util.ArrayList;
import java.util.Collections;
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

    public String getAuthor() {
        return author;
    }

    public List<Double> getRatings() {
        return Collections.unmodifiableList(ratings);
    }

    public List<String> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    public void addRating(double rating) {
        ratings.add(rating);
    }

    public void addReview(String review) {
        reviews.add(review);
    }

    public String calculateAverageRating() {
        if (ratings.isEmpty()) {
            return "No Rating";
        }

        double totalRating = 0.0;

        for (double rating : ratings) {
            totalRating += rating;
        }

        double averageRating = totalRating / ratings.size();
        return String.format("%.2f", averageRating);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ratings=" + ratings +
                ", reviews=" + reviews +
                '}';
    }

    public String toCSVString() {
        String reviewsStr;
        if (reviews.isEmpty() || (reviews.size() == 1 && reviews.get(0).equals("No Review"))) {
            reviewsStr = "No Review";
        } else {
            // Join the reviews using "|"
            reviewsStr = String.join("|", reviews);
        }

        String ratingStr;
        if (ratings.isEmpty() || ratings.get(0).equals("No Rating")) {
            ratingStr = "No Rating";
        } else {
            ratingStr = calculateAverageRating();
        }

        return String.format("%s,%s,\"%s\",%s", title, author, reviewsStr, ratingStr);
    }

    public <T> void setRatings(List<T> ratings) {
        this.ratings = new ArrayList<>();
        for (T rating : ratings) {
            // Convert T to Double (assuming T is a numeric type)
            if (rating instanceof Number) {
                this.ratings.add(((Number) rating).doubleValue());
            }
            // Handle other types of ratings if necessary
        }
    }

    public void setReviews(List<String> reviews) {
        // Check if the reviews list is empty or contains "No Review"
        if (!reviews.isEmpty() || (reviews.size() == 1 && reviews.get(0).equalsIgnoreCase("No Review"))) {
            // If the reviews list is empty or contains only "No Review", set it to a list
            // containing "No Review"
            this.reviews = Collections.singletonList("No Review");
        } else {
            // Otherwise, set the reviews list as it is
            this.reviews = reviews;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}