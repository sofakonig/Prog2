package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
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
    @FXML
    public JFXButton searchBtn;
    @FXML
    public TextField searchField;
    @FXML
    public JFXListView<Movie> movieListView;
    @FXML
    public JFXComboBox<String> genreComboBox;
    @FXML
    public JFXComboBox<Integer> releaseYearBox;
    @FXML
    public JFXComboBox<Double> ratingBox;
    @FXML
    public JFXButton sortBtn;
    @FXML
    public JFXButton clearBtn;
    private boolean isAscending = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(movieRestClient.getAllMovies());
        SortedList<Movie> sortedMovies = new SortedList<>(observableMovies);

        movieListView.setItems(sortedMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.getItems().addAll(
                Arrays.stream(Genre.values())
                        .map(Enum::name)
                        .toArray(String[]::new)
        );
        genreComboBox.setPromptText("Filter by Genre");

        releaseYearBox.setPromptText("Filter by Release Year");
        releaseYearBox.getItems().addAll(getReleaseYearsFromMovies(observableMovies));
        releaseYearBox.getItems().sort(Comparator.naturalOrder());

        ratingBox.setPromptText("Filter from Rating");
        ratingBox.getItems().addAll(getRatingsFromMovies(observableMovies));
        ratingBox.getItems().sort(Comparator.naturalOrder());

        searchBtn.setText("Search");
        searchBtn.setOnAction(event -> {
            String query = searchField.getText() != null ? searchField.getText() : "";
            Genre selectedGenre = genreComboBox.getSelectionModel().getSelectedItem() == null ? null : Genre.valueOf(genreComboBox.getSelectionModel().getSelectedItem());
            int releaseYear = releaseYearBox.getSelectionModel().getSelectedItem() == null ? 0 : releaseYearBox.getSelectionModel().getSelectedItem();
            double rating = ratingBox.getSelectionModel().getSelectedItem() == null ? 0.0 : ratingBox.getSelectionModel().getSelectedItem();

            List<Movie> movies = movieRestClient.getByQuery(query, selectedGenre, releaseYear, rating);
            observableMovies.setAll(movies);
        });
        clearBtn.setText("Clear");
        clearBtn.setOnAction(event -> clearFilters(observableMovies, sortedMovies));

        sortBtn.setOnAction(actionEvent -> {
            if (isAscending) {
                sortedMovies.setComparator(Comparator.comparing(Movie::getTitle));
                sortBtn.setText("Sort (desc)");
            } else {
                sortedMovies.setComparator(Comparator.comparing(Movie::getTitle).reversed());
                sortBtn.setText("Sort (asc)");
            }
            isAscending = !isAscending;
        });
    }

    private void clearFilters(ObservableList<Movie> observableMovies, SortedList<Movie> sortedList) {
        searchField.clear();
        genreComboBox.getSelectionModel().clearSelection();
        releaseYearBox.getSelectionModel().clearSelection();
        ratingBox.getSelectionModel().clearSelection();
        observableMovies.setAll(movieRestClient.getAllMovies());
        isAscending = true;
        sortBtn.setText("Sort (asc)");
        sortedList.setComparator(Comparator.comparing(Movie::getTitle));
    }

    public Set<Integer> getReleaseYearsFromMovies(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getReleaseYear)
                .collect(Collectors.toSet());
    }

    public Set<Double> getRatingsFromMovies(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getRating)
                .collect(Collectors.toSet());
    }

    public String getMostPopularActor(List<Movie> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .mapToInt(movie -> movie.getTitle().length())
                .max()
                .orElse(0);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirectors().contains(director))
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }
}
