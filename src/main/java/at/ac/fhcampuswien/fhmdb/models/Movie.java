package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;

public class Movie {

    private String id;
    private String title;
    private int releaseYear;
    private String description;
    private String imgUrl;
    private int lengthInMinutes;
    private double rating;
    private List<Genre> genres;
    private List<String> directors;
    private List<String> mainCast;
    private List<String> writers;

    public Movie() {
    }

    public Movie(String id, String title, int releaseYear, String description, String imgUrl, int lengthInMinutes,
                 double rating, List<Genre> genres, List<String> directors, List<String> mainCast, List<String> writers) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
        this.genres = genres;
        this.directors = directors;
        this.mainCast = mainCast;
        this.writers = writers;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

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
