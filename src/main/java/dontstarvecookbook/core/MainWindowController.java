package dontstarvecookbook.core;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import dontstarvecookbook.core.enums.DishType;
import dontstarvecookbook.core.enums.IngredientType;
import dontstarvecookbook.core.utils.FileUtilities;
import dontstarvecookbook.core.utils.StringUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Map;
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

    @FXML
    private JFXDrawer foodValuesJfxDrawer;
    @FXML
    private JFXHamburger showFoodValuesHamburger;

    private HamburgerBasicCloseTransition hamburgerButtonTransition;

    //TODO: make a hashmap containing dish and ingredient images, so I dont need to instantiate a new one every time
    //TODO: maybe use a filtered list as a source of items for the list view
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeApplicationResources();
        initializeButtonEvents();
        initializeListViewCellFactory();
        initializeListViewEvents();
        initializeListViewContents();
        initializeJFXControls();
    }

    private void initializeApplicationResources() {
        CookingIngredientsStorage.initialize();
        CrockPotDishesStorage.initialize();
    }

    private void initializeJFXControls() {
        //initialization code for jfxdrawer
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/dontstarvecookbook/fxml/foodvaluespopupmenu.fxml"));
            foodValuesJfxDrawer.setSidePane(p);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        foodValuesJfxDrawer.close();

        //initialization code for jfxhamburger

        hamburgerButtonTransition = new HamburgerBasicCloseTransition(showFoodValuesHamburger);
        hamburgerButtonTransition.setRate(-1);

        showFoodValuesHamburger.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            hamburgerButtonTransition.setRate(hamburgerButtonTransition.getRate() * -1);
            hamburgerButtonTransition.play();

            if (foodValuesJfxDrawer.isShown()) {
                foodValuesJfxDrawer.close();
                foodValuesJfxDrawer.setMouseTransparent(true);
            } else {
                foodValuesJfxDrawer.open();
                foodValuesJfxDrawer.setMouseTransparent(false);
            }
        });
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
        dishesListView.setCellFactory(callback -> new ListCell<CrockPotDish>() {
            @Override
            protected void updateItem(CrockPotDish item, boolean empty) {
                super.updateItem(item, empty);
                if (!isEmpty()) {
                    ImageView view = new ImageView();
                    view.setImage(CrockPotDishesStorage.getInstance().getDishIcons().get(item.getName()));
                    view.setFitHeight(64);
                    view.setFitWidth(64);
                    view.setSmooth(true);
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
            CrockPotDish item = dishesListView.getSelectionModel().getSelectedItem();
            if (item != null) displayDishInformation(item);
        });
    }

    private void displayDishInformation(CrockPotDish dish) {
        infoVBox.getChildren().clear();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        dishNameLabel.setText(dish.getName());
        healthRestoredLabel.setText(Integer.toString(dish.getHealthRecovered()));
        hungerRestoredLabel.setText(df.format(dish.getHungerRecovered()));
        sanityRestoredLabel.setText(Integer.toString(dish.getSanityRecovered()));

//        String path = FileUtilities.formatImagePath(dish, "png");
        dishIconImageView.setImage(CrockPotDishesStorage.getInstance().getDishIcons().get(dish.getName()));

        String textStyle = "-fx-font-size: 16px;";
        String titleStyle = "-fx-font-weight: bold; -fx-font-size: 16px;";

        Insets textInsets = new Insets(5, 0, 0, 45);
        Insets titleInsets = new Insets(10, 0, 0, 25);

        Label neededFoodValuesTitle = createLabel("Needed food values: ", titleStyle, true);
        infoVBox.getChildren().add(neededFoodValuesTitle);
        VBox.setMargin(neededFoodValuesTitle, titleInsets);

        df.setMaximumFractionDigits(2);
        if (dish.getNeededFoodValues().isEmpty()) {
            Label l = createLabel("- None\n", textStyle, true);
            infoVBox.getChildren().add(l);
            VBox.setMargin(l, textInsets);
        } else {
            dish.getNeededFoodValues().forEach((ingredientType, foodValue) -> {
                String foodValueStr = StringUtilities.removeTrailingChars(df.format(foodValue), '0');
                String ingrName = IngredientType.getPrettyName(ingredientType, false);
                String content = String.format("- %sx %s value", foodValueStr, ingrName);
                Label l = createLabel(content, textStyle, true);
                infoVBox.getChildren().add(l);
                VBox.setMargin(l, textInsets);
            });
        }

        Label neededIngredientsTitle = createLabel("Needed specific ingredients: ", titleStyle, true);
        infoVBox.getChildren().add(neededIngredientsTitle);
        VBox.setMargin(neededIngredientsTitle, titleInsets);

        if (dish.getNeededSpecificIngredients().isEmpty()) {
            Label l = createLabel("- None\n", textStyle, true);
            infoVBox.getChildren().add(l);
            VBox.setMargin(l, textInsets);
        } else {
            for (Map.Entry<String, Integer> ingredient : dish.getNeededSpecificIngredients().entrySet()) {
                Label l = createLabel(String.format("- %sx %s", ingredient.getValue(), ingredient.getKey()), textStyle, true);
                infoVBox.getChildren().add(l);
                VBox.setMargin(l, textInsets);
            }
        }

        //TODO: fill the infoVBox with labels describing dish information
        Label excludedIngredientsTitle = createLabel("Excluded ingredients: ", titleStyle, true);
        infoVBox.getChildren().add(excludedIngredientsTitle);
        VBox.setMargin(excludedIngredientsTitle, titleInsets);

        if (dish.getExcludedSpecificIngredients().isEmpty()) {
            Label l = createLabel("- None\n", textStyle, true);
            infoVBox.getChildren().add(l);
            VBox.setMargin(l, textInsets);
        } else {
            for (String ingredient : dish.getExcludedSpecificIngredients()) {
                Label l = createLabel("- No " + ingredient, textStyle, true);
                infoVBox.getChildren().add(l);
                VBox.setMargin(l, textInsets);
            }
        }

        Label excludedFoodTypesTitle = createLabel("Excluded food types: ", titleStyle, true);
        infoVBox.getChildren().add(excludedFoodTypesTitle);
        VBox.setMargin(excludedFoodTypesTitle, titleInsets);

        if (dish.getExcludedIngredientTypes().isEmpty()) {
            Label l = createLabel("- None\n", textStyle, true);
            infoVBox.getChildren().add(l);
            VBox.setMargin(l, textInsets);
        } else {
            for (IngredientType ingredientType : dish.getExcludedIngredientTypes()) {
                Label l = createLabel("- No " + IngredientType.getPluralForm(ingredientType, true), textStyle, true);
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
        Label l = new Label(text);
        l.setStyle(style);
        l.setWrapText(wrap);
        return l;
    }

    private List<CrockPotDish> gatherDishesByType(DishType type) {
        return CrockPotDishesStorage.getInstance().getDishes().
                stream().filter(dish -> dish.getDishType().equals(type)).collect(Collectors.toList());
    }
}
