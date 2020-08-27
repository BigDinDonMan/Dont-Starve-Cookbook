package dontstarvecookbook.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dontstarvecookbook.core.utils.FileUtilities;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: load everything with threads
public class CookingIngredientsStorage {

    private static CookingIngredientsStorage instance;
    public static CookingIngredientsStorage getInstance() {
        return instance;
    }

    public static void initialize() {
        if (instance == null) instance = new CookingIngredientsStorage();
    }

    private List<CookingIngredient> ingredients;
    private Map<CookingIngredient, Image> ingredientImages;

    private CookingIngredientsStorage() {
        try {
            this.ingredients = this.loadIngredients();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        this.ingredientImages = this.loadIngredientIcons();
    }

    private List<CookingIngredient> loadIngredients() throws URISyntaxException, IOException {
        String s = "";
        try (InputStream is = getClass().getResourceAsStream("/dontstarvecookbook/core/ingredients.json")) {
            byte[] bytes = new byte[is.available()];
            is.read(bytes, 0, bytes.length);
            s = new String(bytes);
        }
        return new Gson().fromJson(s, new TypeToken<List<CookingIngredient>>() {}.getType());
    }

    private Map<CookingIngredient, Image> loadIngredientIcons() {
        Map<CookingIngredient, Image> images = new HashMap<>();
        for (CookingIngredient ingredient : this.ingredients) {
            String imagePath = FileUtilities.formatImagePath(ingredient, "png");
            images.put(ingredient, new Image(getClass().getResource(imagePath).toExternalForm()));
        }
        return images;
    }


    public List<CookingIngredient> getIngredients() {
        return this.ingredients;
    }

    public Map<CookingIngredient, Image> getIngredientImages() {
        return ingredientImages;
    }
}
