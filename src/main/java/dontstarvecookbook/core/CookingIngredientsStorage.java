package dontstarvecookbook.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
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
        Path filePath = Paths.get(getClass().getResource("/dontstarvecookbook/core/ingredients.json").toURI());
        String fileContents = Files.lines(filePath).reduce("", String::concat);
        return new Gson().fromJson(fileContents, new TypeToken<List<CookingIngredient>>() {}.getType());
    }


    public List<CookingIngredient> getIngredients() {
        return this.ingredients;
    }
}
