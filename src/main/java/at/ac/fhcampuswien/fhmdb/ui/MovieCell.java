package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label description = new Label();
    private final Label genre = new Label();

    private final JFXButton detailBtn = new JFXButton("Show Details");
    private final JFXButton watchlistBtn = new JFXButton("To Watchlist");

    private final HBox header = new HBox(10, title, detailBtn, watchlistBtn);
    private final VBox layout = new VBox(10, header, description, genre);

    private final VBox detailsPane = new VBox(5);
    private final Label releaseYearLabel = new Label();
    private final Label lengthLabel = new Label();
    private final Label ratingLabel = new Label();
    private final Label directorsLabel = new Label();
    private final Label writersLabel = new Label();
    private final Label mainCastLabel = new Label();

    private boolean collapsedDetails = true;

    public MovieCell(ClickEventHandler<Movie> addToWatchlistClicked) {
        super();
        detailBtn.getStyleClass().add("text-yellow");
        watchlistBtn.getStyleClass().add("text-yellow");
        detailBtn.setStyle("-fx-background-color: #f5c518;");
        watchlistBtn.setStyle("-fx-background-color: #f5c518;");

        title.getStyleClass().add("text-yellow");
        description.getStyleClass().add("text-white");
        genre.getStyleClass().add("text-white");
        releaseYearLabel.getStyleClass().add("text-white");
        lengthLabel.getStyleClass().add("text-white");
        ratingLabel.getStyleClass().add("text-white");
        directorsLabel.getStyleClass().add("text-white");
        writersLabel.getStyleClass().add("text-white");
        mainCastLabel.getStyleClass().add("text-white");

        title.setFont(title.getFont().font(20));
        description.setWrapText(true);
        description.maxWidthProperty().bind(widthProperty().subtract(30));
        genre.setStyle("-fx-font-style: italic;");

        header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(title, Priority.ALWAYS);
        layout.setPadding(new Insets(10));
        layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

        detailsPane.getChildren().addAll(
                releaseYearLabel,
                ratingLabel,
                lengthLabel,
                directorsLabel,
                writersLabel,
                mainCastLabel
        );
        detailsPane.getStyleClass().add("details-pane");

        detailBtn.setOnMouseClicked(e -> toggleDetails());
        watchlistBtn.setOnMouseClicked(e -> addToWatchlistClicked.onClick(getItem()));
    }

    private void toggleDetails() {
        if (collapsedDetails) {
            layout.getChildren().add(detailsPane);
            detailBtn.setText("Hide Details");
        } else {
            layout.getChildren().remove(detailsPane);
            detailBtn.setText("Show Details");
        }
        collapsedDetails = !collapsedDetails;
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            collapsedDetails = true;
            detailBtn.setText("Show Details");
            layout.getChildren().remove(detailsPane);

            title.setText(movie.getTitle());
            description.setText(
                    Optional.ofNullable(movie.getDescription()).orElse("No description available")
            );
            String genres = movie.getGenres().stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            genre.setText(genres);

            releaseYearLabel.setText("Release Year: " + movie.getReleaseYear());
            ratingLabel.setText("Rating: " + movie.getRating() + "/10");
            lengthLabel.setText("Length: " + movie.getLengthInMinutes() + " minutes");
            directorsLabel.setText("Directors: " + String.join(", ", movie.getDirectors()));
            writersLabel.setText("Writers: " + String.join(", ", movie.getWriters()));
            mainCastLabel.setText("Main Cast: " + String.join(", ", movie.getMainCast()));

            setGraphic(layout);
        }
    }
}