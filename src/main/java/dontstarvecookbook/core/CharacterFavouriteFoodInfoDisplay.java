package dontstarvecookbook.core;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class CharacterFavouriteFoodInfoDisplay extends BorderPane {

    @FXML
    private ImageView characterIconImageView;
    @FXML
    private ImageView foodIconImageView;
    @FXML
    private Label characterNameLabel;
    @FXML
    private Label foodNameLabel;

    private CharacterFavouriteFoodInfo info;

    public CharacterFavouriteFoodInfoDisplay() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dontstarvecookbook/fxml/characterfavouritefoodinfocontrol.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CharacterFavouriteFoodInfoDisplay(CharacterFavouriteFoodInfo info) {
        this();
        this.info = info;
        displayInfo();
    }

    //TODO: download 'x' image and set is as warly's favourite
    private void displayInfo() {
        this.characterIconImageView.setImage(
                new Image(getClass().getResource(String.format("/images/characters/%s.png", info.getCharacterName().toLowerCase())).toExternalForm())
        );
        try {
            if (info.isCrockPotDish()) {
                this.foodIconImageView.setImage(new Image(String.format("/images/crockpot-dish-icons/%s.png", info.getFoodName().toLowerCase().replace(' ', '-').
                        replace("'", "").replace("-(dst)", "").trim())));
            } else {
                this.foodIconImageView.setImage(new Image(String.format("/images/ingredient-icons/%s.png", info.getFoodName().toLowerCase().replace(' ', '-').
                        replace("'", "").replace("-(dst)", "").trim())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Tooltip.install(this.characterIconImageView, new Tooltip(info.getCharacterName()));
        Tooltip.install(this.foodIconImageView, new Tooltip(info.getFoodName()));
    }

    public void setInfo(CharacterFavouriteFoodInfo info) {
        this.info = info;
        Platform.runLater(this::displayInfo);
    }
}
