package dontstarvecookbook.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CookingIngredientsStorage {

    private static CookingIngredientsStorage instance;
    public static CookingIngredientsStorage getInstance() {
        return instance;
    }

    public static void initialize() {
        if (instance == null) instance = new CookingIngredientsStorage();
    }

    private List<CookingIngredient> ingredients;

    private CookingIngredientsStorage() {
        try {
            this.ingredients = this.loadIngredients();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
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


    public List<CookingIngredient> getIngredients() {
        return this.ingredients;
    }
}
