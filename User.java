//import java.util.Random;

/**
 * Created by Tanuj Tushar Shah 13409858
 * https://github.com/Tanujshahh/CP2406PROGRAMMING-III-a2.git
 */
public class User extends Player {
    public User() {
    }

    public User(String name) {
        super(name);
    }

    @Override
    public String runTurn(Category category, CardDeck field) {
        return askInput("Hello!Please Select a card to play or pass\nCategory: " +
                category.toString() + "\n(1)Pass" +
                displayHand() + "\n Field: " + field.display());
    }

    @Override
    public Category askCategory() {
        String message = name + "\nPlease select a Category:\n(1) Hardness\n(2) Specific Gravity\n(3) Cleavage" +
                "\n(4) Crustal Abundance\n(5) Economic Value\n Your hand:" + displayHand();
        String input = askInput(message);
        switch (input) {
            case "1": return Category.HARDNESS;
            case "2": return Category.SPECIFICGRAVITY;
            case "3": return Category.CLEAVAGE;
            case "4": return Category.CRUSTALABUNDANCE;
            case "5": return Category.ECONOMICVALUE;
            default: displayMessage("Error!Invalid Selection");
                return null;
        }
    }
}
