package dontstarvecookbook.core;

import dontstarvecookbook.core.enums.IngredientType;
import dontstarvecookbook.core.utils.StringUtilities;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//TODO: download the rest of ingredient images (veggies are already done)
public class FoodValuesPopUpMenuController implements Initializable {

    @FXML
    private ListView<CookingIngredient> foodValuesListView;
    @FXML
    private ComboBox<IngredientType> foodCategoryComboBox;

    private FilteredList<CookingIngredient> ingredientsFilteredList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListViewCellFactory();
        initializeListViewItems();
        initializeComboBox();
    }

    private void initializeListViewItems() {
        this.ingredientsFilteredList = new FilteredList<>(
                FXCollections.observableArrayList(CookingIngredientsStorage.getInstance().getIngredients()), p -> false);
        this.foodValuesListView.setItems(this.ingredientsFilteredList);
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
        Predicate<IngredientType> typePredicate = i -> !i.equals(IngredientType.BUG) && !i.equals(IngredientType.ICE);
        foodCategoryComboBox.getItems().addAll(
                Arrays.stream(IngredientType.values()).filter(typePredicate).collect(Collectors.toList())
        );
        foodCategoryComboBox.setButtonCell(new IngredientTypeListCell());
        foodCategoryComboBox.setCellFactory(callback -> new IngredientTypeListCell());
        foodCategoryComboBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                ingredientsFilteredList.setPredicate(p -> p.getIngredientValues().containsKey(newValue));
            }
        }));
    }
}
