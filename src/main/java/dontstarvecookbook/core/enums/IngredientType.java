package dontstarvecookbook.core.enums;

import java.util.HashMap;

public enum IngredientType {
    FRUIT, MEAT, MONSTER_FOOD, VEGGIE, FISH, EGG, SWEETENER, DAIRY, BUG, INEDIBLE, ICE;

    public static HashMap<IngredientType, String> prettyNames;

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
    }

    public static String getPrettyName(IngredientType t) {
        return prettyNames.get(t);
    }
}
