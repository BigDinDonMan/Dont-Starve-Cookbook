package dontstarvecookbook.core;

import dontstarvecookbook.core.enums.IngredientType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof CookingIngredient) {
            CookingIngredient other = (CookingIngredient)obj;
            return this.name.equals(other.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.ingredientValues);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
