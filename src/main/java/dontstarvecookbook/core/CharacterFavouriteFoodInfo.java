package dontstarvecookbook.core;

public class CharacterFavouriteFoodInfo {
    private String characterName;
    private String foodName;
    private boolean crockPotDish;

    public CharacterFavouriteFoodInfo() { }
    public CharacterFavouriteFoodInfo(String charName, String fname, boolean crockPotDish) {
        this.crockPotDish = crockPotDish;
        this.characterName = charName;
        this.foodName = fname;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getFoodName() {
        return foodName;
    }

    public boolean isCrockPotDish() {
        return crockPotDish;
    }
}
