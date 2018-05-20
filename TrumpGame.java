/**
 * Created by Tanuj Tushar Shah 13409858
 * https://github.com/Tanujshahh/CP2406PROGRAMMING-III-a2.git
 */

//import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;



class TrumpGame extends JFrame implements ActionListener {
    private static final int EMPTY_HAND = 8;
    private ArrayList<Player> players,wonPlayerYay;
    private static CardDeck deck;
    private Boolean endOfRound,confirm;
    private CardDeck storedCards,field;
    private Card currentCard,lastCard;
    private Player playerWonRound,currentPlayer;
    private Category currentCategory;
    private int helpCount = 0, turnNo = 0;
    private JMenuItem startMenu;
    private JMenuItem howMenu;
    private JMenuItem quitMenu;
    private JLabel infoLabel;
    private JLabel helpLabel;
    private JLabel cardDisplayLabel;
    private JLabel categoryLabel;
    private JPanel gamePanel;
    private JPanel fieldPanel;
    private JPanel selectionPanel;
    private JPanel logPanel;
    private ImageIcon[] helpCards;
    private JCheckBox[] aiCheck;
    private JTextField[] inputTexts;
    private JComboBox<Category> selectionBox;
    private JComboBox<String> playerHandBox;
    private ArrayList<JLabel> playerLabels,loglist;
    private JButton selectionButton,playCardButton,passButton,confirmInputButton,nextHelp;
    private Container con = getContentPane();

    TrumpGame() {
        wonPlayerYay = new ArrayList<>();
        storedCards = new CardDeck();
        field = new CardDeck();
        getHelpCards();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        initialiseMenuGui();
        setResizable(false);
        setTitle("SuperTrumpGame");
    }

    private void initialiseSetupGui() {

        setSize(500,500);
        gamePanel = new JPanel();
        infoLabel = new JLabel("");
        confirmInputButton = new JButton("Please Confirm Input");
        inputTexts = new JTextField[5];
        aiCheck = new JCheckBox[5];
        JLabel inputLabel = new JLabel("Please Input:");
        JLabel nameLabel = new JLabel("Name");
        JLabel aiLabel = new JLabel("Need Artifical Intillengence?");
        JLabel errorLabel = new JLabel("");
        // input panel
        gamePanel.setLayout(new GridLayout(8,2));
        infoLabel.setText("Blank fields means nil.");
        inputLabel.setText("<html>Please enter GAMER information.<br>" +
                "3 Players minimum.</html>");
        gamePanel.add(inputLabel);
        gamePanel.add(confirmInputButton);
        gamePanel.add(nameLabel);
        gamePanel.add(aiLabel);
        for (int i=0;i<5;i++) {
            inputTexts[i] = new JTextField();
            gamePanel.add(inputTexts[i]);
            aiCheck[i] = new JCheckBox();
            gamePanel.add(aiCheck[i]);
        }
        gamePanel.add(infoLabel);
        gamePanel.add(errorLabel);
        confirmInputButton.addActionListener(this);
        con.add(gamePanel);
        invalidate();
        validate();
        repaint();
    }

    private void initialiseMenuGui() {
        /* initialise the menubar */
        JMenu helpMenu = new JMenu("Instructions?");
        startMenu = new JMenuItem("Start New Game :)");
        quitMenu = new JMenuItem("quit :(");
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");
        howMenu = new JMenuItem("How to play");
        setSize(700,700);
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        fileMenu.add(startMenu);
        fileMenu.add(quitMenu);
        helpMenu.add(howMenu);
        startMenu.addActionListener(this);
        howMenu.addActionListener(this);
        quitMenu.addActionListener(this);
        initialiseSetupGui();
    }

    private void initialiseGameGui() {
        /* Initialise the game gui */
        cardDisplayLabel = new JLabel();
        selectionButton = new JButton("Gamer Please Confirm Input");
        playCardButton = new JButton("Play Selected Card");
        passButton = new JButton("Pass eh?");
        selectionBox = new JComboBox<>();
        playerHandBox = new JComboBox<>();
        logPanel = new JPanel();
        loglist = new ArrayList<>();
        categoryLabel = new JLabel("");
        fieldPanel = new JPanel();
        selectionPanel = new JPanel();
        JPanel gamePanel2 = new JPanel();
        JPanel gameInfoPanel = new JPanel();
        JPanel handPanel = new JPanel();
        JPanel cardViewPanel = new JPanel();
        JPanel playerPanel = new JPanel();


        gamePanel.removeAll();
        gamePanel.setLayout(new GridLayout(1,3));
        setSize(960,500);
        gamePanel.add(gamePanel2);
        gamePanel.add(cardViewPanel);
        gamePanel.add(logPanel);

        gamePanel2.setLayout(new GridLayout(4,1));
        gamePanel2.add(gameInfoPanel);
        gamePanel2.add(fieldPanel);
        gamePanel2.add(selectionPanel);
        gamePanel2.add(handPanel);

        gameInfoPanel.setLayout(new GridLayout(3,1));
        gameInfoPanel.setBorder(BorderFactory.createTitledBorder("Game Information"));
        gameInfoPanel.add(playerPanel);
        playerPanel.setLayout(new GridLayout(1,playerLabels.size()));
        for (JLabel playerLabel : playerLabels) {
            playerPanel.add(playerLabel);
        }
        gameInfoPanel.add(infoLabel);
        gameInfoPanel.add(categoryLabel);
        infoLabel.setText(" Please select a card");
        infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        categoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        passButton.addActionListener(this);

        handPanel.setLayout(new BorderLayout());
        handPanel.setBorder(BorderFactory.createTitledBorder("Turn Options"));
        handPanel.add(playerHandBox, BorderLayout.CENTER);
        handPanel.add(playCardButton, BorderLayout.SOUTH);
        handPanel.add(passButton, BorderLayout.NORTH);
        playCardButton.addActionListener(this);

        fieldPanel.setBorder(BorderFactory.createTitledBorder("Field Information"));


        cardViewPanel.setLayout(new BorderLayout());
        cardViewPanel.add(cardDisplayLabel);

        selectionPanel.setLayout(new BorderLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Category Selection"));

        logPanel.setLayout(new GridLayout(30,1));
        logPanel.setBorder(BorderFactory.createTitledBorder("Event Log"));

        currentCategory = null;
        startRound();
        invalidate();
        validate();
        repaint();

    }

    private void addLog(String message) {
        /* build arraylist of label messages */
        System.out.println(message);
        logPanel.removeAll();
        JLabel label = new JLabel(message);
        if (loglist.size() == 30) {

            loglist.remove(0);
        }
        loglist.add(label);

        for (JLabel label1 : loglist) {
            logPanel.add(label1);
        }
        invalidate();
        validate();
        repaint();
    }

    private void startRound() {
        categoryLabel.setText("Current category: ");
        setPlayersPass(false);
        storeCards();
        currentPlayer = players.get(turnNo);
        playerLabels.get(turnNo).setBorder(BorderFactory.createLineBorder(Color.RED));
        addLog("New Round!YAYYY");
        addLog("----------");
        endOfRound = false;
        if (lastCard instanceof PlayCard) {
            lastCard = null;
        }
        if (currentCategory == null) {
            askCategory();
        }
        if (currentPlayer instanceof User) {
            displayUserData();
        } else {
            runAutoTurns();
        }
    }

    private void initialiseSelectionGui() {

        selectionButton.setEnabled(true);
        selectionBox.removeAllItems();
        selectionBox.addItem(Category.HARDNESS);
        selectionBox.addItem(Category.CLEAVAGE);
        selectionBox.addItem(Category.SPECIFICGRAVITY);
        selectionBox.addItem(Category.CRUSTALABUNDANCE);
        selectionBox.addItem(Category.ECONOMICVALUE);
        selectionPanel.add(selectionBox, BorderLayout.NORTH);
        selectionPanel.add(selectionButton, BorderLayout.SOUTH);
        selectionButton.addActionListener(this);
        infoLabel.setText(" Please select a category.");
        invalidate();
        validate();
        repaint();
    }

    private void displayUserData() {

        playerHandBox.removeAllItems();
        fieldPanel.removeAll();
        for (JLabel playerLabel : playerLabels) {
            playerLabel.setBorder(BorderFactory.createEmptyBorder());
        }

        playerLabels.get(turnNo).setBorder(BorderFactory.createLineBorder(Color.RED));
        fieldPanel.setLayout(new GridLayout(field.getCards().size(),1));
        for (Card card : field.getCards()) {
            JLabel label = new JLabel(declareCard(card));
            fieldPanel.add(label);
        }
        for (Card card : currentPlayer.getCardsHand()) {
            playerHandBox.addItem(declareCard(card));
        }
        playerHandBox.addActionListener(this);
        invalidate();
        validate();
        repaint();
    }

    private void runTurnArtificalIntillengence() {
        confirm = false;

        String input;
        if (!currentPlayer.getPass() && !endOfRound && !currentPlayer.getWon()) {
            while (!confirm) {
                input = currentPlayer.runTurn(currentCategory, field);
                switch (input) {
                    case "1": {

                        confirm = true;
                        // pass player, check if winner
                        pass();
                        break;
                    }
                    default: {
                        String index;
                        for (int i = 2; i <= (currentPlayer.getCardsHand().size() + 2); i++) {

                            index = Integer.toString(i);
                            if (input.equals(index)) {

                                currentCard = currentPlayer.getCardsHand().get(i - 2);
                            }
                            confirm = checkMagnetite();

                        }
                        confirm = processCard();
                    }
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == confirmInputButton) {
            createPlayers();
        } else if (source == startMenu) {
            con.removeAll();
            gamePanel.removeAll();
            initialiseSetupGui();
        } else if (source == howMenu) {
            displayHelp();
        } else if (source == quitMenu) {
            dispose();
        } else if (source == nextHelp) {
            ++helpCount;
            if (helpCount > 3) {
                helpCount = 0;
            }
            helpLabel.setIcon(helpCards[helpCount]);
        } else if (source == playCardButton) {
            runTurnHuman();
            if (currentPlayer instanceof User) {
                displayUserData();
                invalidate();
                validate();
                repaint();
            } else {
                runAutoTurns();
            }
        } else if (source == passButton) {
            currentPlayer = players.get(turnNo);
            pass();
            runAutoTurns();
        } else if (source == selectionButton) {
            currentCategory = (Category) selectionBox.getSelectedItem();
            categoryLabel.setText("Current category: " + currentCategory);
            playCardButton.setEnabled(true);
            selectionButton.setEnabled(false);
            passButton.setEnabled(true);
            infoLabel.setText(" Please select a card.");
            displayUserData();
            selectionPanel.removeAll();
            invalidate();
            validate();
            repaint();
        }

        if (source == playerHandBox) {
            int index = playerHandBox.getSelectedIndex();
            if (index >= 0) {
                currentCard = players.get(turnNo).getCardsHand().get(index);
                cardDisplayLabel.setIcon(currentCard.getImage());
            }
        }
        invalidate();
        validate();
        repaint();
    }

    private void nextTurn() {
        ++turnNo;
        if (turnNo == (players.size())) {
            turnNo = 0;
        }
        currentPlayer = players.get(turnNo);
    }

    private void findNextTurn() {
        Boolean found =false;
        nextTurn();
        while (!found) {
            if (!currentPlayer.getPass() && !currentPlayer.getWon()) {
                found = true;
            } else {
                nextTurn();
            }
        }
    }

    private void runAutoTurns() {
        Boolean confirm=false;
        while (!confirm) {
            System.out.println("");
            currentPlayer = players.get(turnNo);
            if (!currentPlayer.getPass() && !currentPlayer.getWon()) {

                if (currentPlayer instanceof ArtificalIntillengence) {

                    runTurnArtificalIntillengence();
                } else  {

                    confirm = true;
                    displayUserData();
                }
            } else {

                findNextTurn();
            }
            if (!confirm) {

                confirm = checkPassed();
            }
            invalidate();
            validate();
            repaint();
        }

        if (endOfRound) {
            startRound();
        } else {
            displayUserData();
        }
    }

    private void endGame() {
        playCardButton.setEnabled(false);
        selectionButton.setEnabled(false);
        confirmInputButton.setEnabled(false);
        passButton.setEnabled(false);
        displayWinners();
        wonPlayerYay.clear();
    }

    private void runTurnHuman() {
        confirm = false;

        if (!currentPlayer.getPass() && !endOfRound && !currentPlayer.getWon()) {

            confirm = checkMagnetite();
            if (!confirm) {
                processCard();
            }
        }
    }

    private Boolean checkMagnetite() {

        if ((turnNo == 0) && (lastCard instanceof TrumpCard)) {
            if (lastCard.getTitle().equals("The Geophysicist")) {
                if (currentCard.getTitle().equals("Magnetite")) {
                    addLog("<html>" + currentPlayer.getName() + " has won!" +
                            "<br>They played the trump card: 'The Geophysicist' " +
                            "<br>with the play card 'Magnetite'</html>");
                    wonPlayerYay.add(currentPlayer);
                    players.remove(currentPlayer);
                    return true;
                }
            }
        }
        return false;
    }

    private void createPlayers() {
        String[] playerNames = new String[5];
        Boolean[] playerArtificalIntillengenceCheck = new Boolean[5];

        for (int i=0;i<5;i++) {
            playerNames[i] = inputTexts[i].getText();
            playerArtificalIntillengenceCheck[i] = aiCheck[i].isSelected();
        }
        players = new ArrayList<>();
        for (int i=0;i<5;i++) {
            if (playerArtificalIntillengenceCheck[i] && !playerNames[i].equals("")) {
                ArtificalIntillengence ai = new ArtificalIntillengence(playerNames[i]);
                players.add(ai);
            } else if (!playerNames[i].equals("")) {
                User player = new User(playerNames[i]);
                players.add(player);
            }
        }
        Collections.shuffle(players);
        playerLabels = new ArrayList<>();
        for (Player player : players) {
            playerLabels.add(new JLabel(player.getName(), SwingConstants.CENTER));
        }
        if (players.size() > 2) {
            createCardDeck();
            dealCards();
            initialiseGameGui();
        } else {
            infoLabel.setText("Invalid number of players.");
            invalidate();
            validate();
            repaint();
        }
    }

    private void displayHelp() {

        helpLabel = new JLabel();
        nextHelp = new JButton("Next Page.");
        JFrame helpFrame = new JFrame();
        helpFrame.setTitle("How to Play");
        helpFrame.setVisible(true);
        helpFrame.setLayout(new BorderLayout());
        helpFrame.setSize(700,700);
        helpFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        helpLabel.setIcon(helpCards[0]);
        helpFrame.add(helpLabel);
        helpFrame.add(nextHelp, BorderLayout.EAST);
        nextHelp.addActionListener(this);
        invalidate();
        validate();
        repaint();
    }

    private void getHelpCards() {
        int NO_HELP_CARDS = 4;
        helpCards = new ImageIcon[NO_HELP_CARDS];
        for (int i = 0; i < 4; i++) {
            try {
                URL imgURL = getClass().getResource("Slide6" + (i + 1) + ".jpg");
                helpCards[i] = new ImageIcon(imgURL);
                Image image = helpCards[i].getImage(); // transform it
                Image newimg = image.getScaledInstance(600, 700,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                helpCards[i] = new ImageIcon(newimg);  // transform it back
            } catch (Exception e) {
                System.out.println("Couldn't find help file");
            }
        }
    }

    private boolean checkGameEnd() {
        int count=0;
        for (Player player : players) {

            if (player.getWon()) {
                ++count;
            }
        }

        return count == (players.size()-1);
    }

    private void displayWinners() {

        addLog("The winning players in order are:");
        int x=0;
        for (Player player : wonPlayerYay) {
            ++x;
           addLog("(" + x + ") " + player.getName());
        }
    }

    private void createCardDeck() {

        deck = new CardDeck();
        deck.buildCardDeck();
    }

    private Boolean processCard() {

        if (currentCard instanceof TrumpCard) {

            playCard(currentCard);
            playerWonRound = currentPlayer;
            return true;
        } else {

            if (lastCard instanceof PlayCard) {
                Boolean indicator = ((PlayCard) currentCard).compareCards(
                        (PlayCard) lastCard, currentCategory);
                if (indicator) {
                    playCard(currentCard);
                    return true;
                } else {
                    if (currentPlayer instanceof User) {
                        addLog("Error, card has lower value than the previous card.");
                    }
                    return false;
                }
            } else {

                playCard(currentCard);
                return true;
            }
        }
    }

    private void playCard(Card card) {

        currentPlayer.removeCard(card);
        cardDisplayLabel.setIcon(new ImageIcon());
        invalidate();
        validate();
        repaint();
        lastCard = currentCard;

        if (currentCard instanceof PlayCard) {
            field.addCard(currentCard);
            addLog(currentPlayer.getName() + " played: " +
                    declareCard(currentCard));
        } else if (currentCard instanceof TrumpCard) {
            field.addCard(currentCard);
            Category category1 = ((TrumpCard) currentCard).getCategory();

            if (category1 != Category.GEOLOGIST) {
                currentCategory = category1;
            } else {

                askCategory();
            }
            addLog(currentPlayer.getName() + " played trump: " + currentCard.getTitle());
            addLog("Category has been changed to: " + currentCategory.toString());
            categoryLabel.setText("Category is: " + currentCategory);

        }
        currentCard = null;
        checkIfGameWinner();
        if (checkGameEnd()) {
            addLog("Game has Ended");
            endGame();
            invalidate();
            validate();
            repaint();
        } else {
            if (lastCard instanceof TrumpCard) {
                startRound();
            } else if (lastCard instanceof PlayCard) {
                findNextTurn();
            }
        }

    }

    private void askCategory() {
        if (currentPlayer instanceof ArtificalIntillengence) {
            currentCategory = currentPlayer.askCategory();
            while (currentCategory == null) {
                currentCategory = currentPlayer.askCategory();
            }
            categoryLabel.setText("Current Category: " + currentCategory);
            addLog(currentPlayer.getName() + " changed the category to: " + currentCategory);
        } else {
            selectionButton.setEnabled(true);
            playCardButton.setEnabled(false);
            passButton.setEnabled(false);
            initialiseSelectionGui();
        }
        invalidate();
        validate();
        repaint();
    }


    private void storeCards() {
        if (!field.getCards().isEmpty()) {
            storedCards.addCards(field);
            field.getCards().clear();
        }
    }

    private void checkIfGameWinner() {
        if (currentPlayer.getCardsHand().size() == 0) {
            //if player has not already won
            if (!currentPlayer.getWon()) {
                addLog(currentPlayer.getName() + " has won!");
                wonPlayerYay.add(currentPlayer);
                currentPlayer.setWon(true);
            }
        }
    }

    private void checkRoundWinner() {
        //check for winner
        if (checkPassed()) {
            playerWonRound = getRoundWinner();
            addLog(playerWonRound.getName() + " Won the round!");
            // select winning player as starting player
            turnNo = players.indexOf(playerWonRound);
            endOfRound = true;
            currentCategory = null;
        }
    }

    private String declareCard(Card currentCard) {
        if (currentCategory == null) {

            return currentCard.getTitle();
        } else {
            if (currentCard instanceof PlayCard) {
                return currentCard.getTitle() + ", " + currentCategory.toString().toLowerCase() +
                        ", (" + ((PlayCard) currentCard).getCategoryValue(currentCategory) + ")";
            } else {
                return currentCard.getTitle() + ", category: " + ((TrumpCard) currentCard).getCategory().toString();
            }
        }
    }

    private Boolean checkPassed() {

        ArrayList<Player> playersLeft = new ArrayList<>();
        for (Player player : players) {

            if (!player.getWon()) {
                playersLeft.add(player);
            }
        }
        int count = 0;
        for (Player player : playersLeft) {
            if (player.getPass()) {
                ++count;
            }
        }
        return count == (playersLeft.size() - 1);
    }

    private void pass() {
        currentPlayer.setPass(true);

        addLog(currentPlayer.getName() + " has decided to pass");
        invalidate();
        validate();
        repaint();

        if (deck.getCards().isEmpty()) {
            deck.getCards().addAll(storedCards.getCards());
            storedCards.getCards().clear();
        }
        currentPlayer.getCardsHand().addAll(deck.dealCards(1));
        findNextTurn();
        checkRoundWinner();
    }

    private void dealCards() {
        for (Player player : players) {
            ArrayList<Card> cards = deck.dealCards(EMPTY_HAND);               //get 8 cards
            player.setCardsHand(cards);
        }
    }

    private void setPlayersPass(boolean playersPass) {
        for (Player player : players) {
            player.setPass(playersPass);
        }
    }

    private Player getRoundWinner() {

        ArrayList<Player> playersLeft = new ArrayList<>();

        for (Player player : players) {
            if (!player.getWon()) {
                playersLeft.add(player);
            }
        }
        for (Player player : playersLeft) {
            if (!player.getPass()) {
                return player;
            }
        }

        return null;
    }

}
