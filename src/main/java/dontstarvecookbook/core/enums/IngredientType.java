package dontstarvecookbook.core.enums;

import java.util.HashMap;

public enum IngredientType {
    FRUIT, MEAT, MONSTER_FOOD, VEGGIE, FISH, EGG, SWEETENER, DAIRY, BUG, INEDIBLE, ICE;

    private static HashMap<IngredientType, String> prettyNames;
    private static HashMap<IngredientType, String> pluralNames;

    static {
        prettyNames = new HashMap<>();
        prettyNames.put(IngredientType.FRUIT, "Fruit");
        prettyNames.put(IngredientType.MEAT, "Meat");
        prettyNames.put(IngredientType.MONSTER_FOOD, "Monster food");
        prettyNames.put(IngredientType.VEGGIE, "Veggie");
        prettyNames.put(IngredientType.FISH, "Fish");
        prettyNames.put(IngredientType.EGG, "Egg");
        prettyNames.put(IngredientType.SWEETENER, "Sweetener");
        prettyNames.put(IngredientType.DAIRY, "Dairy");
        prettyNames.put(IngredientType.BUG, "Bug");
        prettyNames.put(IngredientType.INEDIBLE, "Inedible");
        prettyNames.put(IngredientType.ICE, "Ice");

        pluralNames = new HashMap<>();
        pluralNames.put(IngredientType.FRUIT, "Fruit");
        pluralNames.put(IngredientType.VEGGIE, "Vegetables");
        pluralNames.put(IngredientType.INEDIBLE, "Inedibles");
        pluralNames.put(IngredientType.ICE, "Ice");
        pluralNames.put(IngredientType.MONSTER_FOOD, "Monster Foods");
        pluralNames.put(IngredientType.DAIRY, "Dairy Products");
        pluralNames.put(IngredientType.SWEETENER, "Sweeteners");
        pluralNames.put(IngredientType.FISH, "Fish");
        pluralNames.put(IngredientType.MEAT, "Meat");
        pluralNames.put(IngredientType.BUG, "Bugs");
        pluralNames.put(IngredientType.EGG, "Eggs");
    }

    public static String getPrettyName(IngredientType t, boolean lowercase) {
        return lowercase ? prettyNames.get(t).toLowerCase() : prettyNames.get(t);
    }

    public static String getPluralForm(IngredientType t, boolean lowercase) {
        return lowercase ? pluralNames.get(t).toLowerCase() : pluralNames.get(t);
    }
}
