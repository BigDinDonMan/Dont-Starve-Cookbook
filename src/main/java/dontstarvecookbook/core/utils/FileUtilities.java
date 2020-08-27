package dontstarvecookbook.core.utils;

import dontstarvecookbook.core.CookingIngredient;
import dontstarvecookbook.core.CrockPotDish;

public class FileUtilities {

    private FileUtilities() {}

    public static String formatImagePath(CrockPotDish dish, String ext) {
        String filename = dish.getName().toLowerCase().replace(' ', '-').replace("'", "");
        return String.format("/images/crockpot-dish-icons/%s.%s", filename, ext);
    }

    public static String formatImagePath(CookingIngredient ingredient, String ext) {
        String filename = ingredient.getName().toLowerCase().replace(' ', '-').
                replace("'", "").replace("-(dst)", "").trim();
        return String.format("/images/ingredient-icons/%s.%s", filename, ext);
    }
}
