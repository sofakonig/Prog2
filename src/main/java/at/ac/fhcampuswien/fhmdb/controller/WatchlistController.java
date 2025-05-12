package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.database.DatabaseException;
import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.repository.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.ui.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.ui.UserDialog;
import at.ac.fhcampuswien.fhmdb.ui.WatchlistCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WatchlistController implements Initializable {

    @FXML
    private JFXListView<MovieEntity> watchlistView;

    private WatchlistRepository watchlistRepo;
    private final ObservableList<MovieEntity> observableWatchlist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            watchlistRepo = new WatchlistRepository();
            watchlistView.setPlaceholder(new Label("Watchlist is empty"));
            watchlistView.setItems(observableWatchlist);
            watchlistView.setCellFactory(list -> new WatchlistCell(onRemoveFromWatchlistClicked));
            List<WatchlistMovieEntity> movies = watchlistRepo.getAll();
            // observableWatchlist.setAll(movies);
        } catch (DatabaseException e) {
            new UserDialog("Datenbankfehler", "Konnte Watchlist nicht laden").show();
        }
    }

    private final ClickEventHandler<MovieEntity> onRemoveFromWatchlistClicked = movie -> {
        try {
            watchlistRepo.remove(movie.getApiId());
            observableWatchlist.remove(movie);
            new UserDialog("Erfolg", "'" + movie.getTitle() + "' wurde aus der Watchlist entfernt").show();
        } catch (DatabaseException e) {
            new UserDialog("Datenbankfehler", "Konnte Film nicht entfernen").show();
        }
    };


}
