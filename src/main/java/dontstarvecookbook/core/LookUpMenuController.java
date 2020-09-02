package dontstarvecookbook.core;

import dontstarvecookbook.core.enums.IngredientType;
import dontstarvecookbook.core.utils.StringUtilities;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.ResourceBundle;

public class LookUpMenuController implements Initializable {

    @FXML
    private ListView<CookingIngredient> foodValuesListView;
    @FXML
    private ComboBox<IngredientType> foodCategoryComboBox;
    @FXML
    private VBox favouriteFoodsVBox;

    private FilteredList<CookingIngredient> ingredientsFilteredList;

    private List<CharacterFavouriteFoodInfo> characterFavouriteFoodInfos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListViewCellFactory();
        initializeListViewItems();
        initializeComboBox();
        initializeFavouriteFoodsView();
    }


    private void initializeListViewItems() {
        Thread t = new Thread(() -> {
            CookingIngredientsStorage.initialize();
            this.ingredientsFilteredList = new FilteredList<>(
                    FXCollections.observableArrayList(CookingIngredientsStorage.getInstance().getIngredients()), p -> false);
            Platform.runLater(() -> this.foodValuesListView.setItems(this.ingredientsFilteredList));
        });
        t.start();
    }

    private void initializeListViewCellFactory() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        DecimalFormat df = new DecimalFormat();
        dfs.setDecimalSeparator('.');
        df.setMaximumFractionDigits(2);
        df.setDecimalFormatSymbols(dfs);
        foodValuesListView.setCellFactory(callback -> new ListCell<CookingIngredient>() {
            @Override
            protected void updateItem(CookingIngredient item, boolean empty) {
                super.updateItem(item, empty);
                setAlignment(Pos.CENTER_LEFT);
                if (!isEmpty()) {
                    ImageView view = new ImageView();
                    view.setFitWidth(48);
                    view.setFitHeight(48);
                    view.setPreserveRatio(false);
                    view.setImage(CookingIngredientsStorage.getInstance().getIngredientImages().get(item));

                    String foodValue = StringUtilities.removeTrailingChars(
                            df.format(item.getIngredientValues().get(foodCategoryComboBox.getValue())),
                            '0'
                    );
                    Label l = new Label("Food value: " + foodValue);

                    BorderPane base = new BorderPane();
                    base.setLeft(view);
                    base.setRight(l);
                    BorderPane.setAlignment(l, Pos.CENTER_RIGHT);

                    setGraphic(base);
                    setText(null);
                    Tooltip itemTooltip = new Tooltip(item.getName());
                    //TODO: upgrade project Java version to 9, so we can control the show delay in the tooltip
                    setTooltip(itemTooltip);
                } else {
                    setGraphic(null);
                    setText(null);
                }
            }
        });
    }

    private void initializeComboBox() {
        foodCategoryComboBox.getItems().addAll(IngredientType.values());
        foodCategoryComboBox.setButtonCell(new IngredientTypeListCell());
        foodCategoryComboBox.setCellFactory(callback -> new IngredientTypeListCell());
        foodCategoryComboBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                ingredientsFilteredList.setPredicate(p -> p.getIngredientValues().containsKey(newValue));
                foodValuesListView.scrollTo(0);
            }
        }));
    }

    private void initializeFavouriteFoodsView() {
        loadFavouriteFoods();
        displayFavouriteFoods();
    }

    private void loadFavouriteFoods() {

    }

    private void displayFavouriteFoods() {

    }
}
