package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final Label rating = new Label();
    private final Label releseYear = new Label();
    private final Label length = new Label();
    private final Label director = new Label();
    private final Label mainCast = new Label();
    private final Label writers = new Label();
    private static final String WHITE_TEXT = "text-white";
    private static final String YELLOW_TEXT = "text-yellow";


    private final JFXButton button = new JFXButton("Show Details");
    private final VBox layout = new VBox(title, detail, genre, button);
    private boolean noDetails = true;


    public MovieCell() {
        //super();
        title.getStyleClass().add(YELLOW_TEXT);
        detail.getStyleClass().add(WHITE_TEXT);
        genre.getStyleClass().add(WHITE_TEXT);
        rating.getStyleClass().add(WHITE_TEXT);
        releseYear.getStyleClass().add(WHITE_TEXT);
        length.getStyleClass().add(WHITE_TEXT);
        director.getStyleClass().add(WHITE_TEXT);
        mainCast.getStyleClass().add(WHITE_TEXT);
        writers.getStyleClass().add(WHITE_TEXT);


        genre.setStyle("-fx-font-style: italic");
        layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

        //title.fontProperty().set(title.getFont().font(20));
        title.fontProperty().set(Font.font(20));
        detail.setMaxWidth(this.getScene().getWidth() - 30);
        detail.setWrapText(true);
        layout.setPadding(new Insets(10));
        layout.spacingProperty().set(10);
        layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
        releseYear.setText("Release Year: " + getItem().getReleaseYear());
        //length.setText("Length: " + getItem().getLength());
        //director.setText("Director: " + getItem().getDirector());
        mainCast.setText("Main Cast: " + getItem().getMainCast());
        writers.setText("Writers: " + getItem().getWriters());
        rating.setText("Rating: " + getItem().getRating());


        button.setOnMouseClicked(event -> {
            if(noDetails){
                layout.getChildren().addAll(rating, releseYear, length, director, mainCast, writers);
                button.setText("Hide Details");
                noDetails = false;
            } else {
                layout.getChildren().removeAll(rating, releseYear, length, director, mainCast, writers);
                button.setText("Show Details");
                noDetails = true;
            }
            setGraphic(layout);
        });
    }

    //public


    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setGraphic(null);
            setText(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );

            String genres = movie.getGenres()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
            genre.setText(genres);
            setGraphic(layout);
        }
    }
}

