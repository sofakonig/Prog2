package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HomeController implements Initializable {

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
    private final MovieRestClient movieRestClient = new MovieRestClient();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

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

        int startYear = 1878;
        int currentYear = LocalDate.now().getYear();
        for (int year = startYear; year <= currentYear; year++) {
            releaseYearBox.getItems().add(year);
        }
        releaseYearBox.setPromptText("Filter by Release Year");

        for (int r = 0; r <= 10; r++) {
            ratingBox.getItems().add(r * 1.0);
        }
        ratingBox.setPromptText("Filter by Rating");

        searchBtn.setText("Search");
        searchBtn.setOnAction(event -> {
            String query = searchField.getText() != null ? searchField.getText() : "";
            String selectedGenreStr = genreComboBox.getSelectionModel().getSelectedItem();
            Genre selectedGenre = selectedGenreStr == null ? null : Genre.valueOf(selectedGenreStr);
            Integer selectedYear = releaseYearBox.getSelectionModel().getSelectedItem();
            int releaseYear = selectedYear == null ? 0 : selectedYear;
            Double selectedRating = ratingBox.getSelectionModel().getSelectedItem();
            double rating = selectedRating == null ? 0.0 : selectedRating;

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

    String getMostPopularActor(List<Movie> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(entry -> entry.getValue()))
                .map(entry -> entry.getKey())
                .orElse(null);
    }

    int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .mapToInt(movie -> movie.getTitle().length())
                .max()
                .orElse(0);
    }

    long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirectors().contains(director))
                .count();
    }

    List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }
}
