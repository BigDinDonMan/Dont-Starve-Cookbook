package dontstarvecookbook.core;

import dontstarvecookbook.core.enums.DishType;
import dontstarvecookbook.core.enums.IngredientType;
import dontstarvecookbook.core.utils.StringUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
import java.util.function.Function;
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

    //TODO: make a hashmap containing dish and ingredient images, so I dont need to instantiate a new one every time
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtonEvents();
        initializeListViewCellFactory();
        initializeListViewEvents();
        initializeListViewContents();
    }

    private void initializeButtonEvents() {
        baseGameSpecificToggleButton.setOnAction(e -> fillListViewWithDishType(DishType.ROG_SPECIFIC));
        hamletSpecificToggleButton.setOnAction(e -> fillListViewWithDishType(DishType.HAMLET_SPECIFIC));
        shipwreckedSpecificToggleButton.setOnAction(e -> fillListViewWithDishType(DishType.SHIPWRECKED_SPECIFIC));
        warlySpecificToggleButton.setOnAction(e -> fillListViewWithDishType(DishType.WARLY_SPECIFIC));
        togetherSpecificToggleButton.setOnAction(e -> fillListViewWithDishType(DishType.TOGETHER_SPECIFIC));
        showAllRecipesButton.setOnAction(e -> {
            dishesListView.getItems().clear();
            dishesListView.getItems().addAll(CrockPotDishesStorage.getInstance().getDishes());
        });
    }

    private void fillListViewWithDishType(DishType t) {
        dishesListView.getItems().clear();
        dishesListView.getItems().addAll(gatherDishesByType(t));
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
        infoVBox.getChildren().clear();
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

        var textStyle = "-fx-font-size: 16px;";
        var titleStyle = "-fx-font-weight: bold; -fx-font-size: 16px;";

        var textInsets = new Insets(5, 0, 0, 45);
        var titleInsets = new Insets(10, 0, 0, 25);

        Label neededFoodValuesTitle = createLabel("Needed food values: ", titleStyle, true);
        infoVBox.getChildren().add(neededFoodValuesTitle);
        VBox.setMargin(neededFoodValuesTitle, titleInsets);

        df.setMaximumFractionDigits(2);
        dish.getNeededFoodValues().forEach((ingredientType, foodValue) -> {
            var foodValueStr = StringUtilities.removeTrailingChars(df.format(foodValue), '0');
            var ingrName = IngredientType.getPrettyName(ingredientType, false);
            var content = String.format("- %sx %s value", foodValueStr, ingrName);
            var l = createLabel(content, textStyle, true);
            infoVBox.getChildren().add(l);
            VBox.setMargin(l, textInsets);
        });

        Label neededIngredientsTitle = createLabel("Needed specific ingredients: ", titleStyle, true);
        infoVBox.getChildren().add(neededIngredientsTitle);
        VBox.setMargin(neededIngredientsTitle, titleInsets);

        if (dish.getNeededSpecificIngredients().isEmpty()) {
            var l = createLabel("- None\n", textStyle, true);
            infoVBox.getChildren().add(l);
            VBox.setMargin(l, textInsets);
        } else {
            for (var ingredient : dish.getNeededSpecificIngredients()) {
                var l = createLabel("- No " + ingredient, textStyle, true);
                infoVBox.getChildren().add(l);
                VBox.setMargin(l, textInsets);
            }
        }

        //TODO: fill the infoVBox with labels describing dish information
        Label excludedIngredientsTitle = createLabel("Excluded ingredients: ", titleStyle, true);
        infoVBox.getChildren().add(excludedIngredientsTitle);
        VBox.setMargin(excludedIngredientsTitle, titleInsets);

        if (dish.getExcludedSpecificIngredients().isEmpty()) {
            var l = createLabel("- None\n", textStyle, true);
            infoVBox.getChildren().add(l);
            VBox.setMargin(l, textInsets);
        } else {
            for (var ingredient : dish.getExcludedSpecificIngredients()) {
                var l = createLabel("- No " + ingredient, textStyle, true);
                infoVBox.getChildren().add(l);
                VBox.setMargin(l, textInsets);
            }
        }

        Label excludedFoodTypesTitle = createLabel("Excluded food types: ", titleStyle, true);
        infoVBox.getChildren().add(excludedFoodTypesTitle);
        VBox.setMargin(excludedFoodTypesTitle, titleInsets);

        if (dish.getExcludedIngredientTypes().isEmpty()) {
            var l = createLabel("- None\n", textStyle, true);
            infoVBox.getChildren().add(l);
            VBox.setMargin(l, textInsets);
        } else {
            for (var ingredientType : dish.getExcludedIngredientTypes()) {
                var l = createLabel("- No " + IngredientType.getPluralForm(ingredientType, true), textStyle, true);
                infoVBox.getChildren().add(l);
                VBox.setMargin(l, textInsets);
            }
        }

        Label additionalNotesTitle = createLabel("Additional notes: ", titleStyle, true);
        infoVBox.getChildren().add(additionalNotesTitle);
        VBox.setMargin(additionalNotesTitle, titleInsets);
        Label text = createLabel(dish.getAdditionalNotes().isEmpty() ? "None" : dish.getAdditionalNotes(), textStyle, true);
        infoVBox.getChildren().add(text);
        VBox.setMargin(text, new Insets(5, 0, 0, 30));
    }

    private Label createLabel(String text, String style, boolean wrap) {
        var l = new Label(text);
        l.setStyle(style);
        l.setWrapText(wrap);
        return l;
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
