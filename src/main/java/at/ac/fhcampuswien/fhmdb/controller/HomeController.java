package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.client.MovieRestClient;
import at.ac.fhcampuswien.fhmdb.database.DatabaseException;
import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.repository.MovieRepository;
import at.ac.fhcampuswien.fhmdb.repository.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.ui.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.UserDialog;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class HomeController implements Initializable {

    private final MovieRestClient movieRestClient = new MovieRestClient();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    private WatchlistRepository watchlistRepo;

    @FXML
    private JFXButton searchBtn;
    @FXML
    private TextField searchField;
    @FXML
    private JFXListView<Movie> movieListView;
    @FXML
    private JFXComboBox<String> genreComboBox;
    @FXML
    private JFXComboBox<Integer> releaseYearBox;
    @FXML
    private JFXComboBox<Double> ratingBox;
    @FXML
    private JFXButton sortBtn;
    @FXML
    private JFXButton clearBtn;

    private boolean isAscending = false;

    private final ClickEventHandler<Movie> onAddToWatchlistClicked = movie -> {
        try {
            MovieEntity entity = new MovieRepository().get(movie.getId());
            watchlistRepo.add(entity);
            new UserDialog("Erfolg", "'" + movie.getTitle() + "' wurde zur Watchlist hinzugefügt")
                    .show();
        } catch (DatabaseException e) {
            new UserDialog("Datenbankfehler", "Konnte Film nicht zur Watchlist hinzufügen")
                    .show();
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            watchlistRepo = new WatchlistRepository();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        List<Movie> movies = readDB();
        if (movies.isEmpty()) {
            movies = movieRestClient.getAllMovies();
            writeDB(movies);
        }

        observableMovies.setAll(movies);
        SortedList<Movie> sorted = new SortedList<>(observableMovies);

        movieListView.setItems(sorted);
        movieListView.setCellFactory(list -> new MovieCell(onAddToWatchlistClicked));

        genreComboBox.getItems().addAll(Arrays.stream(Genre.values()).map(Enum::name).toArray(String[]::new));
        genreComboBox.setPromptText("Filter by Genre");

        releaseYearBox.getItems().addAll(getReleaseYearsFromMovies(movies));
        releaseYearBox.setPromptText("Filter by Release Year");

        ratingBox.getItems().addAll(getRatingsFromMovies(movies));
        ratingBox.setPromptText("Filter from Rating");

        searchBtn.setOnAction(e -> applySearch());
        clearBtn.setOnAction(e -> resetFilters(sorted));
        sortBtn.setOnAction(e -> toggleSort(sorted));
    }

    private void applySearch() {
        String q = Optional.ofNullable(searchField.getText()).orElse("");
        Genre g = Optional.ofNullable(genreComboBox.getValue()).map(Genre::valueOf).orElse(null);
        int y = Optional.ofNullable(releaseYearBox.getValue()).orElse(0);
        double r = Optional.ofNullable(ratingBox.getValue()).orElse(0.0);

        List<Movie> res = movieRestClient.getByQuery(q, g, y, r);
        observableMovies.setAll(res);
    }

    private void resetFilters(SortedList<Movie> sorted) {
        searchField.clear();
        genreComboBox.getSelectionModel().clearSelection();
        releaseYearBox.getSelectionModel().clearSelection();
        ratingBox.getSelectionModel().clearSelection();
        observableMovies.setAll(readDB());
        sorted.setComparator(Comparator.comparing(Movie::getTitle));
        sortBtn.setText("Sort (asc)");
        isAscending = false;
    }

    private void toggleSort(SortedList<Movie> sorted) {
        Comparator<Movie> comp = Comparator.comparing(Movie::getTitle);
        if (!isAscending) {
            sorted.setComparator(comp.reversed());
            sortBtn.setText("Sort (desc)");
        } else {
            sorted.setComparator(comp);
            sortBtn.setText("Sort (asc)");
        }
        isAscending = !isAscending;
    }

    private List<Movie> readDB() {
        try {
            MovieRepository repo = new MovieRepository();
            return MovieEntity.toMovies(repo.getAll());
        } catch (DatabaseException e) {
            new UserDialog("DB Error", "Konnte Filme nicht laden").show();
            return new ArrayList<>();
        }
    }

    private void writeDB(List<Movie> movies) {
        try {
            MovieRepository repo = new MovieRepository();
            repo.clear();
            repo.add(movies);
        } catch (DatabaseException e) {
            new UserDialog("DB Error", "Konnte Filme nicht speichern").show();
        }
    }

    public Set<Integer> getReleaseYearsFromMovies(List<Movie> movies) {
        return movies.stream().map(Movie::getReleaseYear).collect(Collectors.toSet());
    }

    public Set<Double> getRatingsFromMovies(List<Movie> movies) {
        return movies.stream().map(Movie::getRating).collect(Collectors.toSet());
    }
}