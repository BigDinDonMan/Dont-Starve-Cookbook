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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
        dishesListView.setCellFactory(callback -> new ListCell<>() {
            private ImageView view = new ImageView();
            @Override
            protected void updateItem(CrockPotDish item, boolean empty) {
                super.updateItem(item, empty);
                if (!isEmpty()) {
                    var filename = item.getName().toLowerCase().replace(' ', '-').replace("'", "");
                    String path = String.format("/images/crockpot-dish-icons/%s.png", filename);
                    view.setImage(new Image(getClass().getResource(path).toExternalForm()));
                    setGraphic(view);
                    setText(item.getName());
                } else {
                    setGraphic(null);
                    setText(null);
                }
            }
        });
    }

    private void initializeListViewContents() {
        dishesListView.getItems().addAll(CrockPotDishesStorage.getInstance().getDishes());
    }

    private void initializeListViewEvents() {
        dishesListView.setOnMouseClicked(e -> {
            var item = dishesListView.getSelectionModel().getSelectedItem();
            if (item != null) displayDishInformation(item);
        });
    }

    private void displayDishInformation(CrockPotDish dish) {
        var df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        var dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        dishNameLabel.setText(dish.getName());
        healthRestoredLabel.setText(Integer.toString(dish.getHealthRecovered()));
        hungerRestoredLabel.setText(df.format(dish.getHungerRecovered()));
        sanityRestoredLabel.setText(Integer.toString(dish.getSanityRecovered()));

        var filename = dish.getName().toLowerCase().replace(' ', '-').replace("'", "");
        var path = String.format("/images/crockpot-dish-icons/%s.png", filename);
        dishIconImageView.setImage(new Image(getClass().getResource(path).toExternalForm()));

        //TODO: fill the infoVBox with labels describing dish information
    }

    private List<CrockPotDish> gatherDishesByType(DishType type) {
        return CrockPotDishesStorage.getInstance().getDishes().
                stream().filter(dish -> dish.getDishType().equals(type)).collect(Collectors.toList());
    }

    @FXML
    public void showFoodValuesWindow(ActionEvent e) {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/dontstarvecookbook/fxml/foodvalueswindow.fxml"));
            Scene s = new Scene(p);
            Stage stage = new Stage();
            stage.initModality(Modality.NONE);
            stage.setTitle("Food values");
            stage.setScene(s);
            stage.getIcons().add(new Image(getClass().getResource("/images/appicon.png").toExternalForm()));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
