package dontstarvecookbook.core;

import dontstarvecookbook.core.enums.DishType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
}
