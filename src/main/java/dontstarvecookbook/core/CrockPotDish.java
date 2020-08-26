package dontstarvecookbook.core;

import dontstarvecookbook.core.enums.DishType;
import dontstarvecookbook.core.enums.IngredientType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrockPotDish {
    private String name;
    private DishType dishType;
    private Map<IngredientType, Double> neededFoodValues;
    private List<String> neededSpecificIngredients;
    private List<IngredientType> excludedIngredientTypes;
    private List<String> excludedSpecificIngredients;
    private String additionalNotes;
    private float hungerRecovered;
    private int healthRecovered;
    private int sanityRecovered;

    public CrockPotDish() {}

    public CrockPotDish(String name, DishType type) {
        this.name = name;
        this.dishType = type;
        this.neededFoodValues = new HashMap<>();
        this.neededSpecificIngredients = new ArrayList<>();
        this.excludedIngredientTypes = new ArrayList<>();
        this.excludedSpecificIngredients = new ArrayList<>();
        this.additionalNotes = "";
    }

    public String getName() {
        return name;
    }

    public DishType getDishType() {
        return dishType;
    }

    public Map<IngredientType, Double> getNeededFoodValues() {
        return neededFoodValues;
    }

    public List<String> getNeededSpecificIngredients() {
        return neededSpecificIngredients;
    }

    public List<IngredientType> getExcludedIngredientTypes() {
        return excludedIngredientTypes;
    }

    public List<String> getExcludedSpecificIngredients() {
        return excludedSpecificIngredients;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public float getHungerRecovered() {
        return hungerRecovered;
    }

    public int getHealthRecovered() {
        return healthRecovered;
    }

    public int getSanityRecovered() {
        return sanityRecovered;
    }
}
