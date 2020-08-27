package dontstarvecookbook.core;

import dontstarvecookbook.core.enums.IngredientType;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class IngredientTypeListCell extends ListCell<IngredientType> {

    public IngredientTypeListCell() {
        super();
    }

    @Override
    protected void updateItem(IngredientType item, boolean empty) {
        super.updateItem(item, empty);
        if (!isEmpty()) {
            ImageView view = new ImageView();
            view.setPreserveRatio(false);
            view.setFitHeight(32);
            view.setFitWidth(32);
            String path = String.format("/images/button-icons/%s-icon.png", item.name().toLowerCase().replace('_', '-'));
            view.setImage(new Image(getClass().getResource(path).toExternalForm()));
            String ingredientTypeName = IngredientType.getPrettyName(item, false);
            setGraphic(view);
            setText(ingredientTypeName);
        } else {
            setGraphic(null);
            setText(null);
        }
    }
}
