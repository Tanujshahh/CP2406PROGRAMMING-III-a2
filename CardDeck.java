/**
 * /**
 * Created by Tanuj Tushar shah 13409858
 * https://github.com/Tanujshahh/CP2406PROGRAMMING-III-a2.git
 */

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


class CardDeck {
    private ArrayList<Card> cards;
    CardDeck() {
        cards = new ArrayList<>();
    }


    void buildCardDeck() {
        String subtitle,chemistry,classification,cleavage,crustalAbundance,crystalSystem,economicValue,fileName,hardness,imageName,occurrence,specificGravity,title;
        ImageIcon image;

        InputStream input;
        try
        {

            input = getClass().getResourceAsStream("carddata.txt");

            BufferedReader reader = new
                    BufferedReader(new InputStreamReader(input));
            for (int x=0; x < 54 ; x++) {                                       //Read cards from file
                reader.readLine();
                chemistry = reader.readLine();
                classification = reader.readLine();
                cleavage = reader.readLine();
                crustalAbundance = reader.readLine();
                crystalSystem = reader.readLine();
                economicValue = reader.readLine();
                fileName = reader.readLine();
                hardness = reader.readLine();
                imageName = reader.readLine();
                occurrence = reader.readLine();
                specificGravity = reader.readLine();
                title = reader.readLine();
                URL imgURL = getClass().getResource(fileName);
                image = new ImageIcon(imgURL);
                cards.add(new PlayCard(x,fileName,imageName,title,image,chemistry,classification,cleavage,crustalAbundance,crystalSystem,economicValue,hardness,occurrence,specificGravity));

            }
            for (int x = 54; x < 60; x++) {
                reader.readLine();
                fileName = reader.readLine();
                imageName = reader.readLine();
                subtitle = reader.readLine();
                title = reader.readLine();
                URL imgURL = getClass().getResource(fileName);
                image = new ImageIcon(imgURL);
                cards.add(new TrumpCard(x,fileName,imageName,title,image,subtitle));
            }

            input.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }


    ArrayList<Card> dealCards(int noCardsDealt) {
        ArrayList<Card> hand = new ArrayList<>();
        for (int i = 0; i < noCardsDealt; i++) {
            Random rand = new Random();
            int randomNum = rand.nextInt(cards.size());
            Card card = cards.remove(randomNum);
            hand.add(card);
        }
    return hand;
    }

    String display() {
        String ret = "";
        int x=0;
        for (Card card : cards) {
            ++x;
            ret += "\n" + card.display(x);
        }
        return ret;
    }

    void addCard(Card card) {
        cards.add(card);
    }

    ArrayList<Card> getCards() {
        return cards;
    }

    void addCards(CardDeck field) {
        cards.addAll(field.getCards());
    }
}
