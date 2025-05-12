package at.ac.fhcampuswien.fhmdb.repository;

import at.ac.fhcampuswien.fhmdb.database.DatabaseException;
import at.ac.fhcampuswien.fhmdb.database.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {

    private final Dao<MovieEntity, Long> dao;
    private static final WatchlistRepository instance;


    public MovieRepository() throws DatabaseException {
        try {
            this.dao = DatabaseManager.getInstance().getMovieDao();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static WatchlistRepository getInstance() {
        return instance;
    }

    public List<MovieEntity> getAll() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching movies", e);
        }
    }

    public void add(List<Movie> movies) throws DatabaseException {
        try {
            List<MovieEntity> ents = MovieEntity.fromMovies(movies);
            for (var e : ents) dao.createOrUpdate(e);
        } catch (SQLException ex) {
            throw new DatabaseException("Error saving movies", ex);
        }
    }

    public void clear() throws DatabaseException {
        try {
            dao.deleteBuilder().delete();
        } catch (Exception e) {
            throw new DatabaseException("Error while deleting movies", e);
        }
    }

    public MovieEntity get(String apiId) throws DatabaseException {
        try {
            return dao.queryBuilder().where().eq("apiId", apiId).queryForFirst();
        } catch (Exception e) {
            throw new DatabaseException("Error while reading movie", e);
        }
    }
}
