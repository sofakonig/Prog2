package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Optional;

public class WatchlistCell extends ListCell<MovieEntity> {
    private final Label title = new Label();
    private final Label description = new Label();
    private final Label genre = new Label();

    private final JFXButton detailBtn = new JFXButton("Show Details");
    private final JFXButton removeBtn = new JFXButton("Remove");

    private final HBox header = new HBox(10, title, detailBtn, removeBtn);
    private final VBox layout = new VBox(10, header, description, genre);

    private final VBox detailsPane = new VBox(5);
    private final Label releaseYearLabel = new Label();
    private final Label lengthLabel = new Label();
    private final Label ratingLabel = new Label();

    private boolean collapsedDetails = true;

    public WatchlistCell(ClickEventHandler<MovieEntity> removeFromWatchlistClick) {
        super();
        detailBtn.getStyleClass().add("text-yellow");
        removeBtn.getStyleClass().add("text-yellow");
        detailBtn.setStyle("-fx-background-color: #f5c518;");
        removeBtn.setStyle("-fx-background-color: #f5c518;");
        HBox.setMargin(detailBtn, new Insets(0, 10, 0, 10));

        title.getStyleClass().add("text-yellow");
        description.getStyleClass().add("text-white");
        genre.getStyleClass().add("text-white");
        releaseYearLabel.getStyleClass().add("text-white");
        lengthLabel.getStyleClass().add("text-white");
        ratingLabel.getStyleClass().add("text-white");
        genre.setStyle("-fx-font-style: italic");

        title.setFont(Font.font(20));
        description.setWrapText(true);
        description.maxWidthProperty().bind(widthProperty().subtract(30));
        header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(title, Priority.ALWAYS);
        layout.setPadding(new Insets(10));
        layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

        detailsPane.getChildren().addAll(releaseYearLabel, ratingLabel, lengthLabel);

        detailBtn.setOnMouseClicked(event -> toggleDetails());
        removeBtn.setOnMouseClicked(event -> removeFromWatchlistClick.onClick(getItem()));
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
    protected void updateItem(MovieEntity movie, boolean empty) {
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
                    movie.getDescription() != null ? movie.getDescription() : "No description available"
            );
            /*
            genre.setText(
                    Optional.ofNullable(movie.getGenres())
                            .map(genList -> String.join(", ", genList.stream().map(Object::toString).toArray(String[]::new)))
                            .orElse("Unknown genre")
            );

             */

            releaseYearLabel.setText("Release Year: " + movie.getReleaseYear());
            ratingLabel.setText("Rating: " + movie.getRating());
            lengthLabel.setText("Length: " + movie.getLengthInMinutes() + " minutes");

            setGraphic(layout);
        }
    }
}