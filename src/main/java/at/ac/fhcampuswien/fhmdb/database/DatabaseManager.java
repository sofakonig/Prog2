package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:h2:file:./fhmdb;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    private static ConnectionSource conn;
    private static DatabaseManager instance;
    private final Dao<MovieEntity, Long> movieDao;
    private final Dao<WatchlistMovieEntity, Long> watchlistDao;

    private DatabaseManager() throws DatabaseException {
        try {
            conn = new JdbcConnectionSource(DB_URL, USER, PASS);
            watchlistDao = DaoManager.createDao(conn, WatchlistMovieEntity.class);
            movieDao = DaoManager.createDao(conn, MovieEntity.class);
            createTables();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(conn, MovieEntity.class);
        TableUtils.createTableIfNotExists(conn, WatchlistMovieEntity.class);
    }

    public Dao<MovieEntity, Long> getMovieDao() {
        return movieDao;
    }

    public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return watchlistDao;
    }

    public void close() {
        if (conn != null) {
            conn.closeQuietly();
        }
    }

    public static DatabaseManager getInstance() throws DatabaseException {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
}
