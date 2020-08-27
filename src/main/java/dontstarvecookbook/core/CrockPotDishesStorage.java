package dontstarvecookbook.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dontstarvecookbook.core.utils.FileUtilities;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: load everything with threads
public class CrockPotDishesStorage {

    private static CrockPotDishesStorage instance;
    public static CrockPotDishesStorage getInstance() {
        return instance;
    }

    public static void initialize() {
        if (instance == null) instance = new CrockPotDishesStorage();
    }

    private List<CrockPotDish> dishes;
    private Map<CrockPotDish, Image> dishIcons;

    private CrockPotDishesStorage() {
        try {
            this.dishes = this.loadDishes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.dishIcons = this.loadDishIcons();
    }

    private Map<CrockPotDish, Image> loadDishIcons() {
        Map<CrockPotDish, Image> images = new HashMap<>();
        for (CrockPotDish dish : this.dishes) {
            String path = FileUtilities.formatImagePath(dish, "png");
            images.put(dish, new Image(getClass().getResource(path).toExternalForm()));
        }
        return images;
    }

    private List<CrockPotDish> loadDishes() throws IOException {
        String s = "";
        try (InputStream is = getClass().getResourceAsStream("/dontstarvecookbook/core/dishes.json")) {
            byte[] bytes = new byte[is.available()];
            is.read(bytes, 0, bytes.length);
            s = new String(bytes);
        }
        return new Gson().fromJson(s, new TypeToken<List<CrockPotDish>>() {}.getType());
    }

    public List<CrockPotDish> getDishes() {
        return dishes;
    }

    public Map<CrockPotDish, Image> getDishIcons() {
        return dishIcons;
    }
}
