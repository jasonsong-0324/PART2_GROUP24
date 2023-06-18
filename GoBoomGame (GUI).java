import javax.swing.*;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.util.*;

import java.io.*;

public class GoBoomGame extends JFrame implements Serializable {
    private static List<Player> players;
    private static List<Card> deck;
    private static List<Card> center;
    private static int currentPlayerIndex;
    private static int trickNumber;
    private static boolean isFirstTrickWon;
    private static Map<String, Integer> scores = new HashMap<>();
    private static Card firstLeadCard;

	private JLabel trickLabel;
	private JLabel currentPlayerLabel;
	// private JLabel playerHandLabel;
	private JPanel playerPanel;
	// private JLabel[] playerHandLabel;
	private JLabel centerLabel;
	private JLabel deckLabel;
	private JLabel currentPlayerCard;
	private JLabel[] playerHandLabel = new JLabel[4];
	private JLabel[] playerScoreLabel = new JLabel[4];
	private static String userInput = null;
	private static JTextArea messageTextArea;
	private JButton loadButton;


    public static void main(String[] args) {
        // GoBoomGame game = new GoBoomGame();
		// GoBoomGame gameGUI = new GoBoomGame();

		// gameGUI.menuGUI();

		// gameGUI.initializeGUI();
		startNewGame();






		// game.initializeGUI();
		// startNewGame();
        // String fileName = "game.ser";
        // Scanner scanner = new Scanner(System.in);

        // System.out.println("Welcome to Go Boom Game!");
        // System.out.println("Enter 's' to start a new game or 'x' to exit.");

        // String input = scanner.nextLine();

        // while (!input.equals("x")) {
        //     if (input.equals("s")) {
        //         GoBoomGame.startNewGame();
        //     } else if (input.equals("load")) {
        //         System.out.print("Enter the file name to load the game: ");
        //         fileName = scanner.nextLine();
        //         GoBoomGame.loadGame(fileName);
        //     } else if (input.equals("save")) {
        //         System.out.print("Enter the file name to save the game: ");
        //         fileName = scanner.nextLine();
        //         GoBoomGame.saveGame(fileName);
        //     } else if (input.equals("reset")) {
        //         GoBoomGame.resetgame();
        //     } else if (input.equals("quit")) {
        //         System.out.println("Are you sure you want to quit? (y/n)");
        //         String quitConfirmation = scanner.nextLine();
        //         if (quitConfirmation.equals("y")) {
        //             break; // exit the game loop
        //         } else if (quitConfirmation.equals("n")) {
        //             System.out.println("Continuing the game...");
        //         }
        //     }
        //     System.out.println("Enter 's' to start a new game or 'x' to exit.");
        //     input = scanner.nextLine();
        // }

        // System.out.println("Thanks for playing Go Boom Game!");

        // scanner.close();
    }

	// public void menuGUI() {
	// 	setTitle("GoBoomGame");
    //     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     setLayout(new BorderLayout());
    //     setSize(500, 500);

	// 	// Create container panel
    //     JPanel menuPanel = new JPanel(new BorderLayout());
	// 	menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
	// 	menuPanel.setBackground(Color.decode("#ffffff"));

	// 	// Add container panel to the main frame
    //     add(menuPanel, BorderLayout.CENTER);


	// 	// Title Panel
	// 	JPanel titlePanel = new JPanel();
	// 	JLabel titleLabel = new JLabel("Game Title!");
	// 	// titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    //     titlePanel.setBackground(Color.decode("#000000"));
	// 	titlePanel.add(titleLabel);

	// 	// Button Panel
	// 	JPanel buttonPanel = new JPanel();
    //     JButton startGameButton = new JButton("Start Game");
    //     JButton loadGameButton = new JButton("Load Game");
	// 	buttonPanel.setBackground(Color.decode("#3503530"));
    //     buttonPanel.add(startGameButton);
    //     buttonPanel.add(loadGameButton);

	// 	menuPanel.add(titleLabel, BorderLayout.NORTH);
	// 	menuPanel.add(buttonPanel, BorderLayout.CENTER);
	// 	pack();
	// 	setVisible(true);

	// 	startGameButton.addActionListener(new ActionListener() {
	// 		public void actionPerformed(ActionEvent e) {
	// 			initializeGUI();
	// 			startNewGame();
	// 		}
	// 	});
	// }

	public void initializeGUI(GoBoomGame gameGUI) {
		setTitle("GoBoomGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 500);

		// Create container panel
        JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		gamePanel.setBackground(Color.decode("#ffffff"));

		// Add container panel to the main frame
        add(gamePanel, BorderLayout.CENTER);

		JPanel gameSettingPanel = new JPanel();
		loadButton = new JButton("Load Game");
		JButton saveButton = new JButton("Save Game");
		JButton resetButton = new JButton("Reset Game");
		gameSettingPanel.add(loadButton);
		gameSettingPanel.add(saveButton);
		gameSettingPanel.add(resetButton);


		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = JOptionPane.showInputDialog(this, "Enter the file name to load:");
				if (fileName != null) {
					// GoBoomGame gameGUI = new GoBoomGame();
					loadGame(fileName, gameGUI);
					initializeGUI(gameGUI);
				}
			}
		});

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = JOptionPane.showInputDialog(this, "Enter the file name to load:");
				if (fileName != null) {
					saveGame(fileName);
				}
			}
		});

		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GoBoomGame gameGUI = new GoBoomGame();
				resetgame(gameGUI);
			}
		});


		// Trick Number & Player's Turn
		JPanel trickTurnPanel = new JPanel();
		trickLabel = new JLabel("Trick #1");
		currentPlayerLabel = new JLabel("Current Player");

		trickTurnPanel.add(trickLabel);
		trickTurnPanel.add(currentPlayerLabel);


		// Player Card
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(4, 1, 10, 10));
		playerHandLabel = new JLabel[players.size()];

        for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
            JLabel label = new JLabel();
            label.setText(player.getName() + ": ");
            playerHandLabel[i] = label;

			playerPanel.add(label);
        }


		// Center & Deck
		JPanel centerPanel = new JPanel();
		// centerPanel.setBackground(Color.decode("#123333"));
		centerPanel.setLayout(new GridLayout(3, 1, 10, 10));
		centerLabel = new JLabel("Center");
		deckLabel = new JLabel("Deck");
		currentPlayerCard = new JLabel("Current Player Card");
		centerPanel.add(centerLabel);
		centerPanel.add(deckLabel);
		centerPanel.add(currentPlayerCard);


		// Console
		messageTextArea = new JTextArea(10, 75);
		messageTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(messageTextArea);


		// Input Field
		JTextField inputField = new JTextField();
		inputField.addActionListener(e -> {
			userInput = inputField.getText().trim();
			inputField.setText(""); // Clear the input field after capturing the input
		});

		

		gamePanel.add(gameSettingPanel);
		gamePanel.add(trickTurnPanel);
		gamePanel.add(playerPanel);
		gamePanel.add(centerPanel);
		gamePanel.add(scrollPane);
		gamePanel.add(inputField);

		pack();
		setVisible(true);
	}

	// @Override
    // public void actionPerformed(ActionEvent e) {
    //     if (e.getSource() == loadButton) {
	// 		String fileName = JOptionPane.showInputDialog(this, "Enter the file name to load:");
    //         if (fileName != null) {
    //             loadGame(fileName);
    //         }
	// 	}
    //         // Start new game logic here
    //         // JOptionPane.showMessageDialog(this, "Starting a new game...");
    //     // } else if (e.getSource() == saveButton) {
    //     //     String fileName = JOptionPane.showInputDialog(this, "Enter the file name to load:");
    //     //     if (fileName != null) {
    //     //         loadGame(fileName);
    //     //     }
    //     // }
    // }

	public void updateTrickLabel(String message) {
		trickLabel.setText(message);
	}

	public void updateCurrentPlayerLabel(String message) {
		currentPlayerLabel.setText(message);
	}

	public void updatePlayerHandLabel(String playerName, String hand) {
		// for (Player player : players) {
		// 	playerHandLabel = new JLabel(message);

		// 	playerPanel.add(playerHandLabel);
		// }

		// for (int i = 0; i < playerHandLabel.length; i++) {
        //     JLabel label = playerHandLabel[i];
        //     label.setText(message);
        // }

		// for (int i = 0; i < playerHandLabel.length; i++) {
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getName().equals(playerName)) {
					playerHandLabel[i].setText(hand);
					break;
				}
			}
		// }
	}


	public static void showMessage(String message) {
        // Append the new message to the existing text
        if (messageTextArea != null) {
            messageTextArea.append(message + "\n");

            // Scroll to the bottom of the text area
            messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
        }
    }

	public void updateCenterDeck(String message2, String message3) {
		// currentPlayerLabel.setText(message);

		deckLabel.setText(message2);
		currentPlayerCard.setText(message3);
	}

	public void updateCenter(String message1) {
		// currentPlayerLabel.setText(message);

		centerLabel.setText(message1);
	}

	public void updateScoreboardPanel(String playerName, String score) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals(playerName)) {
				playerScoreLabel[i].setText(score);
				break;
			}
		}
	}


    private static void startNewGame() {
		GoBoomGame gameGUI = new GoBoomGame();
        initializeGame();
		gameGUI.initializeGUI(gameGUI);
        // dealCards();
        trickNumber = 1;

        isFirstTrickWon = false;
        while (!isGameOver()) {
			String message = "Trick #" + Integer.toString(trickNumber);
			gameGUI.updateTrickLabel(message);
            // System.out.println("Trick #" + trickNumber);
            // System.out.println("--------------");

            playTrick(gameGUI);

            System.out.println();
            if (isGameOver()) {
                break; // Exit the loop if the game is over
            }
        }

        displayScores();
    }

    private static void resetgame(GoBoomGame gameGUI) {
		// GoBoomGame gameGUI = new GoBoomGame();

        trickNumber = 1;
        for (Player player : players) {
            player.clearTricks(); // Clear the tricks
        }
        isFirstTrickWon = false;

        while (!isGameOver()) {
			String message = "Trick #" + Integer.toString(trickNumber);
			gameGUI.updateTrickLabel(message);
			System.out.println(trickNumber);
            System.out.println("Trick #" + trickNumber);
            System.out.println("--------------");

            playTrick(gameGUI);
            center.clear();

            System.out.println();

            if (isGameOver()) {
                break; // Exit the loop if the game is over
            }
        }

        for (Player player : players) {
            player.setScore(0); // Reset the score to 0
        }

        displayScores();
    }

    private static void initializeGame() {
        players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        players.add(new Player("Player4"));

        GoBoomGame game = new GoBoomGame();
        deck = game.createDeck();
        shuffleDeck(deck);
		dealCards();

        center = new ArrayList<>();

        currentPlayerIndex = determineFirstPlayer();

        firstLeadCard = deck.get(0);
        // Card centerCard = new Card(firstLeadCard.getSuit(), firstLeadCard.getRank());
        center.add(firstLeadCard);
        deck.remove(0);

        // System.out.println(
        //         "At the beginning of the game, the first lead card " + firstLeadCard + " is placed at the center.");
        // System.out.println("Player" + (currentPlayerIndex + 1) + " is the first player because of first lead card "
        //         + firstLeadCard + ".");
        // System.out.println();
        // System.out.println("Card suits: Club, Diamond, Heart, Spade ");
        // System.out.println("c=club");
        // System.out.println("d=diamond");
        // System.out.println("h=heart");
        // System.out.println("s=spade");
        // System.out.println();
        // System.out.println("Card ranks: A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K");
        // System.out.println();
        // System.out.println("User can press 'd' to draw card");
        // System.out.println("User can press 'save' to save card");
        // System.out.println("User can press 'load' to load card");
        // System.out.println("User can press 'reset' to reset game");
        // System.out.println("User can press 'quit' to quit game");
        // System.out.println();
    }

    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();

        String[] suits = { "c", "d", "h", "s" };
        String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(suit, rank));
            }
        }

        return deck;
    }

    private static void shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
    }

    private static int determineFirstPlayer() {
        Card firstLeadCard = deck.iterator().next();

        String rank = firstLeadCard.getRank();

        Set<String> player1Ranks = new HashSet<>(Arrays.asList("A", "5", "9", "K"));
        Set<String> player2Ranks = new HashSet<>(Arrays.asList("2", "6", "10"));
        Set<String> player3Ranks = new HashSet<>(Arrays.asList("3", "7", "J"));
        Set<String> player4Ranks = new HashSet<>(Arrays.asList("4", "8", "Q"));

        if (player1Ranks.contains(rank)) {
            return 0; // Player1
        } else if (player2Ranks.contains(rank)) {
            return 1; // Player2
        } else if (player3Ranks.contains(rank)) {
            return 2; // Player3
        } else if (player4Ranks.contains(rank)) {
            return 3; // Player4
        }

        return -1;
    }

    private static void dealCards() {
        for (int i = 0; i < 7; i++) {
            for (Player player : players) {
                Card card = deck.get(0);
                player.addCardToHand(card);
                deck.remove(0);
            }
        }
    }

    private static void playTrick(GoBoomGame gameGUI) {
		// GoBoomGame game = new GoBoomGame();
        if (trickNumber > 1 && !center.isEmpty()) {
            center.clear();
        }

        Scanner scanner = new Scanner(System.in);
        int trickWinnerIndex = currentPlayerIndex;
        Card firstCard = null;
        Card highestCard = null;

        for (int i = 0; i < players.size(); i++) {
            Player currentPlayer = players.get(currentPlayerIndex);
			String message = "Turn: " + currentPlayer.getName();
			gameGUI.updateCurrentPlayerLabel(message);
            // System.out.println("Turn: " + currentPlayer.getName());

            for (Player player : players) {
				String message2 = player.getName() + ": " + player.getHand();
				
				gameGUI.updatePlayerHandLabel(player.getName(), message2);
            }


			String message3 = "Center: " + center;
			// System.out.println(message3 + "IDKDK");
			String message4 = "Deck: " + deck;
			String message5 = "Player" + (currentPlayerIndex + 1) + " Cards: " + currentPlayer.getHand();
			gameGUI.updateCenter(message3);
			gameGUI.updateCenterDeck(message4, message5);
            // System.out.println("Center: " + center);
            // System.out.println("Deck: " + deck);
            // System.out.println("Score: " + getScores());

            // System.out.println("Player" + (currentPlayerIndex + 1) + " Cards: " + currentPlayer.getHand());

            Card playedCard = null;
            boolean hasValidCard = false;

            while (!hasValidCard) {

				while (userInput == null) {
					try {
						Thread.sleep(100); // Sleep for a short duration to avoid excessive CPU usage
					} catch (InterruptedException ignored) {
					}
				}

                System.out.print(">");
                // String input = scanner.nextLine();
				String input = userInput;
				userInput = null;

                if (input.equalsIgnoreCase("d")) {
                    Card drawnCard = currentPlayer.drawCardFromDeck(deck);

                    if (drawnCard != null) {
                        currentPlayer.addCardToHand(drawnCard);
						showMessage("Player" + (currentPlayerIndex + 1) + " draws " + drawnCard + ".");
                        // System.out.println("Player" + (currentPlayerIndex + 1) + " draws " + drawnCard + ".");

                        if (firstLeadCard != null && trickNumber == 1 && (drawnCard.getSuit() == firstLeadCard.getSuit()
                                || drawnCard.getRank() == firstLeadCard.getRank())) {
                            System.out.println("You have drawn a playable card.");
							showMessage("You have drawn a playable card.");
                        } else if (trickNumber > 1 && firstCard != null && (drawnCard.getSuit() == firstCard.getSuit()
                                || drawnCard.getRank() == firstCard.getRank())) {
                            System.out.println("You have drawn a playable card.");
							showMessage("You have drawn a playable card.");
                        } else if (trickNumber > 1 && firstCard == null) {
                            System.out.println("Please play a card first before drawing.");
							showMessage("Please play a card first before drawing.");
                        }

                    } else {
						showMessage("No cards left in the deck." + "Player" + (currentPlayerIndex + 1) + " turn has end.Proceed to next player.");
                        System.out.println("No cards left in the deck.");
                        System.out
                                .println("Player" + (currentPlayerIndex + 1) + " turn has end.Proceed to next player.");
                        System.out.println();
                        break;

                    }
                } else if (input.equalsIgnoreCase("save")) {
                    System.out.print("Enter the file name to save the game: ");
                    String fileName = scanner.nextLine();
                    saveGame(fileName);

                    // saveCurrentPlayerIndex(fileName, currentPlayerIndex);
                    continue; // Continue to the next iteration of the loop
                } else if (input.equalsIgnoreCase("load")) {
                    System.out.print("Enter the file name to load the game: ");
                    String fileName = scanner.nextLine();
                    // loadGame(fileName);
					setCurrentPlayerIndex(currentPlayerIndex);
                    // currentPlayerIndex = loadCurrentPlayerIndex(fileName); // Update
                    // currentPlayerIndex

                    break; // Break out of the loop and return to the main menu
                } else if (input.equalsIgnoreCase("reset")) {
                    // resetgame();
                } else if (input.equalsIgnoreCase("quit")) {
					showMessage("");
					showMessage("Are you sure you want to quit? (y/n)");
                    System.out.println("Are you sure you want to quit? (y/n)");
                    String quitConfirmation = scanner.nextLine();
                    if (quitConfirmation.equalsIgnoreCase("y") || input.equalsIgnoreCase("y")) {
                        System.exit(0); // exit the game loop
                    } else if (quitConfirmation.equalsIgnoreCase("n") || input.equalsIgnoreCase("n")) {
                        showMessage("");
						showMessage("Continuing the game...");
						System.out.println("Continuing the game...");
                    }
                } else {
                    if (isValidInput(input, currentPlayer.getHand())) {
                        playedCard = findCardInHand(input, currentPlayer.getHand());

                        if (center.isEmpty()) {
                            // First player plays any card as the leading card
                            currentPlayer.removeCardFromHand(playedCard);
                            center.add(playedCard);

							showMessage("Player" + (currentPlayerIndex + 1) + " plays " + playedCard + ".");
							showMessage("Center: " + center);
                            System.out.println("Player" + (currentPlayerIndex + 1) + " plays " + playedCard + ".");
                            System.out.println("Center: " + center);
                            firstCard = playedCard; // Store the first card played in the trick
                            hasValidCard = true;
                        } else {
                            Card leadingCard = center.get(0);
                            if (playedCard.getSuit().equals(leadingCard.getSuit())
                                    || playedCard.getRank().equals(leadingCard.getRank())) {
                                // Subsequent players must follow the leading suit
                                currentPlayer.removeCardFromHand(playedCard);
                                center.add(playedCard);
                                System.out.println("Player" + (currentPlayerIndex + 1) + " plays " + playedCard + ".");
								gameGUI.updateCenter("Center: " + center);
								System.out.println("Center: " + center);
                                hasValidCard = true;
                            } else {
								showMessage("Invalid card. Please choose a card of the leading suit or press 'd' to draw a card.");
                                System.out.println(
                                        "Invalid card. Please choose a card of the leading suit or press 'd' to draw a card.");
                            }
                        }
                    } else {
						showMessage("Invalid input. Please enter a valid card or press 'd' to draw a card.");
                        System.out.println("Invalid input. Please enter a valid card or press 'd' to draw a card.");
                    }
                }
            }

            if (i == 0) {
                firstCard = playedCard; // Assign the first card played in the trick
                highestCard = playedCard; // Assign the highest card as the first card
            } else {
                if (playedCard != null && firstCard != null && highestCard != null
                        && playedCard.getSuit().equals(firstCard.getSuit())) {
                    if (compareRanks(playedCard.getRank(), highestCard.getRank()) > 0) {
                        highestCard = playedCard;
                        trickWinnerIndex = currentPlayerIndex;
                    }
                }
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        Player currentTrickWinner = players.get(trickWinnerIndex);
		showMessage("Trick winner: " + currentTrickWinner.getName());
        System.out.println("Trick winner: " + currentTrickWinner.getName());
        currentTrickWinner.incrementScore();

        currentPlayerIndex = trickWinnerIndex;
        if (trickNumber == 1 && trickWinnerIndex == 0) {
            isFirstTrickWon = true; // Set isFirstTrickWon to true if the first trick is won by Player1
        }

        trickNumber++;

    }

	private static void setCurrentPlayerIndex(int currentPlayerIndex2) {
    }


    private static Map<String, Integer> getScores() {
        Map<String, Integer> scores = new LinkedHashMap<>(); // Use LinkedHashMap for maintaining order

        for (Player player : players) {
            int score = player.getTricks().size();
            scores.put(player.getName(), score);
        }

        return scores;
    }

    private static boolean isValidInput(String input, List<Card> hand) {
        for (Card card : hand) {
            if (card.toString().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return input.equalsIgnoreCase("d");
    }

    private static Card findCardInHand(String input, List<Card> hand) {
        for (Card card : hand) {
            if (card.toString().equalsIgnoreCase(input)) {
                return card;
            }
        }
        return null;
    }

    private static int compareRanks(String rank1, String rank2) {
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        int index1 = Arrays.asList(ranks).indexOf(rank1);
        int index2 = Arrays.asList(ranks).indexOf(rank2);
        return Integer.compare(index1, index2);
    }

    private static boolean isGameOver() {
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // private static void displayScores() {
    // System.out.println("Game Over!");
    // System.out.println("Final Scores:");

    // for (Player player : players) {
    // int score = player.getTricks().size();
    // if (isFirstTrickWon && player == players.get(0)) {
    // score++; // Increase the score of the first trick winner if isFirstTrickWon
    // is true
    // }
    // System.out.println(player.getName() + ": " + score + " tricks");
    // }
    // }

    private static void displayScores() {
		showMessage("");	
		showMessage("Game Over!");
		showMessage("Final Scores : ");

        System.out.println("Game Over!");
        System.out.println("Final Scores:");

        Map<String, Integer> scores = getScores(); // Get the scores using the getScores() method

        // Iterate over the scores in a specific order
        for (Player player : players) {
            int score = scores.get(player.getName());
            System.out.println(player.getName() + ": " + score + " tricks");

			showMessage(player.getName() + ": " + score + " tricks");
        }
    }

    public static void saveGame(String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(players);
            objectOut.writeObject(deck);
            objectOut.writeObject(center);
			objectOut.writeObject(trickNumber);
            objectOut.writeInt(currentPlayerIndex);
            objectOut.writeBoolean(isFirstTrickWon); // Save isFirstTrickWon
            objectOut.writeObject(scores); // Save scores


			objectOut.close();
            fileOut.close();
			showMessage("Game saved successfully.");
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadGame(String fileName, GoBoomGame gameGUI) {
        try {
			// GoBoomGame gameGUI = new GoBoomGame();

            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            List<Player> loadedPlayers = (List<Player>) objectIn.readObject();
            List<Card> loadedDeck = (List<Card>) objectIn.readObject();
            List<Card> loadedCenter = (List<Card>) objectIn.readObject();
            int loadedTrickNumber = (int) objectIn.readObject();
            int loadedCurrentPlayerIndex = objectIn.readInt();
            isFirstTrickWon = objectIn.readBoolean(); // Assign the loaded value to isFirstTrickWon
            scores = (Map<String, Integer>) objectIn.readObject(); // Assign the loaded value to scores

            objectIn.close();
            fileIn.close();

			// Update the game state with the loaded data
            players = loadedPlayers;
            deck = loadedDeck;
            center = loadedCenter;
            trickNumber = loadedTrickNumber;
            currentPlayerIndex = loadedCurrentPlayerIndex;


            // Call the resumeGame method to continue the game from the loaded state
            // resumeGame(fileName);

			// GoBoomGame gameGUI = new GoBoomGame();

            // Print the loaded player's turn
            System.out.println("Game loaded successfully.");
            System.out.println(center);
			gameGUI.updateCenter("Center: " + center);
            // Continue the game from the loaded state

            // Main game loop
            isFirstTrickWon = false;
            while (!isGameOver()) {
				String message = "Trick #" + Integer.toString(trickNumber);
				gameGUI.updateTrickLabel(message);
				System.out.println("Trick #" + trickNumber);
				System.out.println("--------------");

				playTrick(gameGUI);

				System.out.println();
				if (isGameOver()) {
					break; // Exit the loop if the game is over
				}
			}

        	displayScores();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load the game: " + e.getMessage());
        }
    }

}