package at.ac.fhcampuswien.fhmdb.repository;

import at.ac.fhcampuswien.fhmdb.database.DatabaseException;
import at.ac.fhcampuswien.fhmdb.database.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    private final Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository() throws DatabaseException {
        try {
            this.dao = DatabaseManager.getInstance().getWatchlistDao();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<WatchlistMovieEntity> getAll() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching watchlist", e);
        }
    }

    public void add(MovieEntity movieEntity) throws DatabaseException {
        try {
            if (dao.queryForEq("movie_id", movieEntity.getId()).isEmpty()) {
                dao.create(new WatchlistMovieEntity(movieEntity));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error adding to watchlist", e);
        }
    }

    public void remove(MovieEntity movieEntity) throws DatabaseException {
        try {
            List<WatchlistMovieEntity> list = dao.queryForEq("movie_id", movieEntity.getId());
            for (WatchlistMovieEntity ent : list) {
                dao.delete(ent);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error removing from watchlist", e);
        }
    }
}
