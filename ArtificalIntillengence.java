/**
 * Created by Tanuj Tushar shah 13409858
 * https://github.com/Tanujshahh/CP2406PROGRAMMING-III-a2.git
 */
import java.util.Random;

class ArtificalIntillengence extends Player {

    ArtificalIntillengence(String name) {
        super(name);
    }

    @Override
    public String runTurn(Category category, CardDeck field) {

        int i = 0;

        if (field.getCards().isEmpty()) {
            return "2";
        } else {
            while (i < (getCardsHand().size())) {
                ++i;
                Card card = getCardsHand().get(i-1);
                if (card instanceof PlayCard) {

                    if (((PlayCard) (card)).compareCards(
                            (PlayCard) field.getCards().get((field.getCards().size() - 1)), category)) {

                        return Integer.toString(i+1);
                    }
                }
            }
        }
        i=0;

        while (i < (getCardsHand().size()))  {
            ++i;
            if (getCardsHand().get(i-1) instanceof TrumpCard)  {

                return Integer.toString(i+1);
            }
        }

        return Integer.toString(1);
    }

    @Override
    public Category askCategory() {
        Random rand = new Random();
        int input = rand.nextInt(4)+1;
        switch (input) {
            case 1: return Category.HARDNESS;
            case 2: return Category.SPECIFICGRAVITY;
            case 3: return Category.CLEAVAGE;
            case 4: return Category.CRUSTALABUNDANCE;
            case 5: return Category.ECONOMICVALUE;
            default: askCategory();
                return null;
        }
    }
}
