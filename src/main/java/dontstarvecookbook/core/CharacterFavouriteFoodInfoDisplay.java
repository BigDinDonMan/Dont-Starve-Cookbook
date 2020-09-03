package dontstarvecookbook.core;

import dontstarvecookbook.core.utils.FXUtilities;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

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

    private void displayInfo() {
        this.characterIconImageView.setImage(
                new Image(getClass().getResource(String.format("/images/characters/%s.png", info.getCharacterName().toLowerCase())).toExternalForm())
        );
        try {
            if (info.getCharacterName().equals("Warly")) {
                this.foodIconImageView.setImage(new Image("/images/x.png"));
            } else {
                if (info.isCrockPotDish()) {
                    this.foodIconImageView.setImage(new Image(String.format("/images/crockpot-dish-icons/%s.png", info.getFoodName().toLowerCase().replace(' ', '-').
                            replace("'", "").replace("-(dst)", "").trim())));
                } else {
                    this.foodIconImageView.setImage(new Image(String.format("/images/ingredient-icons/%s.png", info.getFoodName().toLowerCase().replace(' ', '-').
                            replace("'", "").replace("-(dst)", "").trim())));
                }
            }
            Tooltip characterTooltip, foodTooltip;
            characterTooltip = new Tooltip(info.getCharacterName());
            foodTooltip = new Tooltip(info.getFoodName());
            Duration dur = new Duration(50);
            foodTooltip.setShowDelay(dur);
            characterTooltip.setShowDelay(dur);
            Tooltip.install(characterIconImageView, characterTooltip);
            Tooltip.install(foodIconImageView, foodTooltip);
        } catch (Exception ignored) {}
    }

    public void setInfo(CharacterFavouriteFoodInfo info) {
        this.info = info;
        Platform.runLater(this::displayInfo);
    }
}
