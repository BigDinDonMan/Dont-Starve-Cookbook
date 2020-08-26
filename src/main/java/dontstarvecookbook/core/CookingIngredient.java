package dontstarvecookbook.core;

import dontstarvecookbook.core.enums.IngredientType;

import java.util.HashMap;
import java.util.Map;

public class CookingIngredient {
    private String name;
    private Map<IngredientType, Float> ingredientValues;

    public CookingIngredient() {}

    public CookingIngredient(String name) {
        this.name = name;
        this.ingredientValues = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<IngredientType, Float> getIngredientValues() {
        return ingredientValues;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredientValues(Map<IngredientType, Float> values) {
        this.ingredientValues = values;
    }
}
