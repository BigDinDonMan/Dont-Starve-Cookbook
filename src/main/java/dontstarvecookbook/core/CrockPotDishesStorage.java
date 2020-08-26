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

public class CrockPotDishesStorage {

    private static CrockPotDishesStorage instance;
    public static CrockPotDishesStorage getInstance() {
        return instance;
    }

    public static void initialize() {
        if (instance == null) instance = new CrockPotDishesStorage();
    }

    private List<CrockPotDish> dishes;

    private CrockPotDishesStorage() {
        try {
            this.dishes = this.loadDishes();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
