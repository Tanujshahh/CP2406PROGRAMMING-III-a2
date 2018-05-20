import javax.swing.*;
import java.awt.*;

/**
 * Created by Tanuj Tushar Shah 13409858
 * https://github.com/Tanujshahh/CP2406PROGRAMMING-III-a2.git
 */
abstract class Card {

    private String fileName,imageName,title;
    private ImageIcon image;
    private int index;

    Card(int index, String fileName, String imageName, String title, ImageIcon image) {
        this.fileName = fileName;
        this.imageName = imageName;
        this.title = title;
        this.index = index;
        this.image = image;
    }
    public abstract String display(int cardHandNo);

    String getTitle() {
        return title;
    }

    public ImageIcon getImage() {
        return image;
    }
}
