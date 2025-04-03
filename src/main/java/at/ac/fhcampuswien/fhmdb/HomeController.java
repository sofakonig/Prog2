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
import java.util.*;
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


    public Set<Integer> extractReleaseYearsFromMovies(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getReleaseYear)
                .collect(Collectors.toSet());
    }

    public Set<Double> extractRatingsFromMovies(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getRating)
                .collect(Collectors.toSet());
    }

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
        releaseYearBox.getItems().addAll(extractReleaseYearsFromMovies(observableMovies));
        releaseYearBox.getItems().sort(Comparator.naturalOrder());

        ratingBox.setPromptText("Filter by Rating");
        ratingBox.getItems().addAll(extractRatingsFromMovies(observableMovies));
        ratingBox.getItems().sort(Comparator.naturalOrder());

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
        // Stream über die Liste von Filmen starten
        return movies.stream()
                // Jedes Movie-Objekt wird in seinen 'mainCast' (Hauptdarsteller) umgewandelt
                .flatMap(movie -> movie.getMainCast().stream())
                // Gruppiere alle Schauspieler nach ihrem Namen und zähle, wie oft sie auftreten
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                // Konvertiere das Ergebnis in einen Stream von Entry-Sets (Key-Value Paare)
                .entrySet().stream()
                // Finde das Entry (Schlüssel-Wert Paar) mit dem höchsten Wert (d.h. die häufigste Häufigkeit)
                .max(Comparator.comparingLong(entry -> entry.getValue()))
                // Wenn ein solcher Eintrag gefunden wurde, gibt der Stream den Schauspieler (Key) zurück
                .map(entry -> entry.getKey())
                // Falls kein Schauspieler gefunden wurde (z.B. bei einer leeren Liste), gibt null zurück
                .orElse(null);
    }

    int getLongestMovieTitle(List<Movie> movies) {
        // Stream über die Liste der Filme starten
        return movies.stream()
                // Verwandle jedes Movie-Objekt in die Länge des Titels
                .mapToInt(movie -> movie.getTitle().length())
                // Finde den maximalen Wert (also die längste Titel-Länge)
                .max()
                // Falls ein Titel gefunden wurde, gib die maximale Länge zurück
                .orElse(0); // Falls keine Filme vorhanden sind oder alle Titel eine Länge von 0 haben, gebe 0 zurück
    }

    long countMoviesFrom(List<Movie> movies, String director) {
        // Stream über die Liste der Filme starten
        return movies.stream()
                // Filtere alle Filme, deren Regisseur den angegebenen Namen enthält
                .filter(movie -> movie.getDirectors().contains(director))
                // Zähle, wie viele Filme den angegebenen Regisseur enthalten
                .count();
    }

    List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        // Stream über die Liste der Filme starten
        return movies.stream()
                // Filtere alle Filme, deren Erscheinungsjahr zwischen startYear und endYear liegt (einschließlich)
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                // Sammle die gefilterten Filme in einer neuen Liste und gebe diese zurück
                .collect(Collectors.toList());
    }
}
