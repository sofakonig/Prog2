package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;

public class Movie {

    private String id;
    private String title;
    private int releaseYear;
    private String description;
    private String imgUrl;
    private double rating;
    private List<Genre> genres;
    private List<String> directors;
    private List<String> mainCast;
    private List<String> writers;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public double getRating() {
        return rating;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public List<String> getWriters() {
        return writers;
    }
}
