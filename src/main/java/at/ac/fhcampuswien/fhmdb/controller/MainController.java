package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class MainController {
    @FXML
    public JFXHamburger hamburgerMenu;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private BorderPane mainPane;

    private boolean isMenuCollapsed = true;

    private HamburgerBasicCloseTransition transition;

    public void initialize() {
        transition = new HamburgerBasicCloseTransition(hamburgerMenu);
        transition.setRate(-1);
        drawer.toBack();

        hamburgerMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            toggleMenuDrawer();
        });
        navigateToHome();
    }

    private void toggleHamburgerTransitionState(){
        transition.setRate(transition.getRate() * -1);
        transition.play();
    }

    private void toggleMenuDrawer(){
        toggleHamburgerTransitionState();

        if(isMenuCollapsed) {
            TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5), drawer);
            translateTransition.setByX(130);
            translateTransition.play();
            isMenuCollapsed = false;
            drawer.toFront();
        } else {
            TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5), drawer);
            translateTransition.setByX(-130);
            translateTransition.play();
            isMenuCollapsed = true;
            drawer.toBack();
        }
    }

    public void setContent(String fxmlPath){
        FXMLLoader loader = new FXMLLoader(FhmdbApplication.class.getResource(fxmlPath));
        try {
            mainPane.setCenter(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!isMenuCollapsed){
            toggleMenuDrawer();
        }
    }

    @FXML
    public void navigateToWatchlist() {
        setContent("watchlist-view.fxml");
    }

    @FXML
    public void navigateToHome() {
        setContent("home-view.fxml");
    }
}