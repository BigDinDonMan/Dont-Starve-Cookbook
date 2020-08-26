package dontstarvecookbook.core;

import dontstarvecookbook.core.enums.DishType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private VBox dishDisplayContainer;

    @FXML
    private VBox infoVBox;

    @FXML
    private Button baseGameSpecificToggleButton;
    @FXML
    private Button hamletSpecificToggleButton;
    @FXML
    private Button shipwreckedSpecificToggleButton;
    @FXML
    private Button warlySpecificToggleButton;
    @FXML
    private Button togetherSpecificToggleButton;
    @FXML
    private Button showAllRecipesButton;

    @FXML
    private ListView<CrockPotDish> dishesListView;

    @FXML
    private ImageView dishIconImageView;

    @FXML
    private Label dishNameLabel;

    @FXML
    private Label healthRestoredLabel;
    @FXML
    private Label hungerRestoredLabel;
    @FXML
    private Label sanityRestoredLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtonEvents();
        initializeListViewCellFactory();
        initializeListViewEvents();
        initializeListViewContents();
    }

    private void initializeButtonEvents() {
        baseGameSpecificToggleButton.setOnAction(e -> {

        });
        hamletSpecificToggleButton.setOnAction(e -> {

        });
        shipwreckedSpecificToggleButton.setOnAction(e -> {

        });
        warlySpecificToggleButton.setOnAction(e -> {

        });
        showAllRecipesButton.setOnAction(e -> {

        });
        togetherSpecificToggleButton.setOnAction(e -> {

        });
    }

    private void initializeListViewCellFactory() {

    }

    private void initializeListViewContents() {

    }

    private void initializeListViewEvents() {

    }

    private List<CrockPotDish> gatherDishesByType(DishType type) {
        return null;
    }

    @FXML
    private void showFoodValuesWindow(ActionEvent e) {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/dontstarvecookbook/fxml/foodvalueswindow.fxml"));
            Scene s = new Scene(p);
            Stage stage = new Stage();
            stage.initModality(Modality.NONE);
            stage.setTitle("Food values");
            stage.setScene(s);
            try (InputStream is = getClass().getResourceAsStream("/images/appicon.png")) {
                stage.getIcons().add(new Image(is));
            }
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
