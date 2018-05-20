import javax.swing.*;
//import java.awt.*;

/**
 * Created by Tanuj Tushar Shah 13409858
 * https://github.com/Tanujshahh/CP2406PROGRAMMING-III-a2.git
 */
class TrumpCard extends Card{
    private String subtitle;

    TrumpCard(int index, String fileName, String imageName, String title, ImageIcon image, String subtitle) {
        super(index, fileName, imageName, title, image);
        this.subtitle = subtitle.trim();
    }

    @Override
    public String display(int cardHandNo) {
        return ("(" + cardHandNo + ") TRUMP: Titile: " + getTitle() + " Subtitle: " + subtitle);
    }
    public Category getCategory() {
        switch (getTitle()) {
            case "The Miner": return Category.ECONOMICVALUE;
            case "The Petrologist": return Category.CRUSTALABUNDANCE;
            case "The Gemmologist": return Category.HARDNESS;
            case "The Mineralogist": return Category.CLEAVAGE;
            case "The Geophysicist": return Category.SPECIFICGRAVITY;
            case "The Geologist": return Category.GEOLOGIST;
        }
        return null;
    }
}
