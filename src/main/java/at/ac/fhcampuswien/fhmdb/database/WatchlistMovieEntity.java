package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(foreign = true,
            columnName = "movie_id",
            foreignAutoRefresh = true,
            unique = true,
            canBeNull = false)
    private MovieEntity movie;

    public WatchlistMovieEntity() {
    }

    public WatchlistMovieEntity(MovieEntity movie) {
        this.movie = movie;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }
}
