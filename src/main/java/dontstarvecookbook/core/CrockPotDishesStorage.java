package dontstarvecookbook.core;

import java.io.IOException;
import java.net.URISyntaxException;
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
//        try {
//
//        } catch (IOException | URISyntaxException e) {
//
//        }
    }

    private List<CrockPotDish> loadDishes() throws IOException, URISyntaxException {
        return null;
    }

    public List<CrockPotDish> getDishes() {
        return dishes;
    }
}
