package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@DatabaseTable(tableName = "movies")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(unique = true, canBeNull = false)
    private String apiId;

    @DatabaseField(canBeNull = false)
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String genres;

    @DatabaseField
    private int releaseYear;

    @DatabaseField
    private String imgUrl;

    @DatabaseField
    private int lengthInMinutes;

    @DatabaseField
    private double rating;

    public MovieEntity() {
    }

    public MovieEntity(String apiId,
                       String title,
                       String description,
                       String genres,
                       int releaseYear,
                       String imgUrl,
                       int lengthInMinutes,
                       double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public static List<MovieEntity> fromMovies(List<Movie> movies) {
        return movies.stream().map(m ->
                new MovieEntity(
                        m.getId(),
                        m.getTitle(),
                        m.getDescription(),
                        genresToString(m.getGenres()),
                        m.getReleaseYear(),
                        m.getImgUrl(),
                        m.getLengthInMinutes(),
                        m.getRating()
                )
        ).collect(Collectors.toList());
    }

    public static String genresToString(List<Genre> genres) {
        return genres.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    public static List<Movie> toMovies(List<MovieEntity> movieEntities) {
        if (movieEntities == null || movieEntities.isEmpty()) {
            return Collections.emptyList();
        }
        List<Movie> movies = new ArrayList<>(movieEntities.size());
        for (MovieEntity e : movieEntities) {
            movies.add(new Movie(
                    e.getApiId(),
                    e.getTitle(),
                    e.getReleaseYear(),
                    e.getDescription(),
                    e.getImgUrl(),
                    e.getLengthInMinutes(),
                    e.getRating(),
                    Genre.mapGenres(e.getGenres())
            ));
        }
        return movies;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
