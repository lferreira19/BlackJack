import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Random;

/**
 * Lena Ferreira
 * Project 4- Black Jack
 * COSC 117-002
 * 12/8/12
 */

public class Blackjack extends JApplet implements ActionListener {

    // images
    private Image[] images = new Image[52];
    private Image downCard;

    // card deck
    private Card[] deck = new Card[52];
    private int nextCard = 0;

    // dealer info
    private Card[] dealerHand = new Card[20];
    private int dealerHandCount = 0;
    private int dealerScore = 0;
    private boolean dealerCardDown = true;

    // player info
    private Card[] playerHand = new Card[20];
    private int playerHandCount = 0;
    private int playerScore = 0;

    // displayed message
    private String gameMessage = "";

    // buttons
    private Button dealButton = new Button("Deal");
    private Button hitButton = new Button("Hit");
    private Button standButton = new Button("Stand");
    private Button continueButton = new Button("Continue");

    public void init() {
        //Set the applet layout to null
       setLayout(null);

        
        
        //Set an appropriate background color for the applet. Also set
        // the size of the applet; make sure this matches the size you specify
        // in the html file used to launch your finished applet.
        setBackground(Color.CYAN);
        setSize(700,700);


        
        //There are four buttons used by the applet defined above. For
        // each button, specify its location/boundary, add it to the applet, and
        // add an actionListener for it.
        
        dealButton.setBounds(60, 550, 100, 20);
		add(dealButton);
		dealButton.addActionListener(this);
		
		hitButton.setBounds(180, 550, 100, 20);
		add(hitButton);
		hitButton.addActionListener(this);
		
		standButton.setBounds(300, 550, 100, 20);
		add(standButton);
		standButton.addActionListener(this);
		
		continueButton.setBounds(420, 550, 100, 20);
		add(continueButton);
		continueButton.addActionListener(this);


        
        // load card images
        URL url = getCodeBase();
        for (int i = 0; i < 52; i++) {
            char suit = "cdhs".charAt((i / 13));
            char rank = "123456789tjqk".charAt((i % 13));
            String card = "" + suit + rank;
            images[i] = getImage(url, card + ".gif");
        }
        downCard = getImage(url, "b1fv.gif");

        // TODO: Call the setUpCards method
        setUpCards();


        
        // Enable the deal button and disable all other buttons
        dealButton.setEnabled(true);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        continueButton.setEnabled(false);


        
        // Set the game message to instruct the user to press the deal
        // button if they want to deal a new hand
        gameMessage = "Please press Deal to begin a new hand of Blackjack!";


    }

    private void setUpCards() {
        // create a new "deck" of cards (an array of card objects)
        for (int i = 0; i < 52; i++) {
            char suit = "cdhs".charAt((i / 13));
            char rank = "123456789tjqk".charAt((i % 13));
            deck[i] = new Card(suit, rank, i);
        }

        // TODO: Call the shuffleDeck method to shuffle the cards
        shuffleDeck();
        


        
        // TODO: set the nextCard to equal the first position (i.e. 0) of the of
        // the deck array
       nextCard = 0;


        
        // There are 4 global variables defined above for the "dealer".
        // They all have initial values but those get overwritten when a hand is
        // played. Reset the values for each variable to their original values.
       dealerHand = new Card[20];
       dealerHandCount = 0;
       dealerScore = 0;
       dealerCardDown = true;


        
        // There are 3 global variables defined above for the "player".
        // They all have initial values but those get overwritten when a hand is
        // played. Reset the values for each variable to their original values.
       playerHand = new Card[20];
       playerHandCount = 0;
       playerScore = 0;


    }

    private void shuffleDeck() {
        Random randGen = new Random();
        for (int i = 0; i < 10000; i++) {
            int pos1 = randGen.nextInt(deck.length);
            int pos2 = randGen.nextInt(deck.length);
            Card temp = deck[pos1];
            deck[pos1] = deck[pos2];
            deck[pos2] = temp;
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        // Use the clearRect method to set the background for the entire
        // applet area to the background color you specified in the init method.
        g.clearRect(0, 0, 700, 700);


        
        // Create a smaller area to be used as the playing surface of the
        // card game. Use a different color than you selected for the
        // background.
        g.setColor(Color.gray);
        g.fillRoundRect(20, 50, 660, 600, 20, 20);
        


        
        // Display the game message using an appropriate color and font.
        g.setColor(Color.black);
        Font f = new Font(Font.SERIF,Font.BOLD, 27);
		g.setFont(f);
		g.drawString(gameMessage, 40, 610);
        


        
        // TODO: Display the card Images that make up the player's hand. Also
        // display an appropriate heading above these cards, so we know whose
        // hand it is. The playerHand array should contain the Card objects that
        // have been dealt to the player and the playerHandCount should indicat
        // how many there are. One of the fields in the Card object is the
        // position of that card's Image object in the images array. You'll need
        // to use a loop to process the hand.
        Font r = new Font(Font.SERIF,Font.PLAIN,32);
		g.setFont(r);
		g.drawString("Player's Hand", 40, 100);
		for(int i = 0; i < playerHandCount; i++){
			g.drawImage(images[playerHand[i].imageNum],40 + (i * 30) , 150 ,88 , 120, this);
			
		}


        
        // TODO: Display card Images and a heading for the dealer's hand in a
        // fashion similar to that used for the player's hand above. There is,
        // however, one additional twist... The dealer's first card is initially
        // dealt down. But it is displayed face up at the end of the hand. A
        // boolean variable dealerCardDown is true if the face of the card is
        // not to be shown.

		g.drawString("Dealer's Hand", 40, 350);
		for(int i = 0; i < dealerHandCount; i++){
			if(i == 0 && dealerCardDown == true){
				g.drawImage(downCard,40 , 400 ,88 , 120, this);
			}else{
				g.drawImage(images[dealerHand[i].imageNum],40 + (i * 30) , 400 ,88 , 120, this);
			}

		}

    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // The actionPerformed method will be called whenever the user
        // clicks on a button. There are 4 buttons, each of which has their
        // own unique behavior. This is a good place for an if-else-if
        // structure... so one has been set up for you below.

        if (e.getSource() == dealButton) {
            // Update buttons and messages:
            // (1) enable the hit and stand buttons,
            // (2) disable the deal and continue buttons,
            // (3) set the game message to "Press Hit or Stand"
        	 dealButton.setEnabled(false);
             hitButton.setEnabled(true);
             standButton.setEnabled(true);
             continueButton.setEnabled(false);
             gameMessage = "Would you like to Hit or Stand?";
            


            
            // Deal 2 cards to the dealer. Dealing a card consists of
            // setting the next open cell in the dealerHand array (indexed by
            // dealerHandCount) equal to the next card in the deck array
            // (indexed by nextCard). Also make sure to increment
            // dealerHandCount and nextCard
            
             dealerHand[dealerHandCount] = deck[nextCard];
             dealerHandCount++;
             nextCard++;
             dealerHand[dealerHandCount] = deck[nextCard];
             dealerHandCount++;
             nextCard++;


            
            // Deal 2 cards to the player. This time use playerHand and
            // PlayerHandCount.
          
             playerHand[playerHandCount] = deck[nextCard];
             playerHandCount++;
             nextCard++;
             playerHand[playerHandCount] = deck[nextCard];
             playerHandCount++;
             nextCard++;


            
            // Check if the player's hand score is 21. If it is, the
            // player has blackjack and wins the hand. In this case, change
            // the game message to indicate that the player wins, enable the
            // continue button, and disable all other buttons.
            //
            // If the player score is not 21, check if the dealer has 21
            // instead. In this case, change the message to indicate that the
            // dealer wins and enable/disable buttons the same as if the player
            // won.
             playerScore = computeScore(playerHand, playerHandCount);
             dealerScore = (computeScore(dealerHand, dealerHandCount));

             if(playerScore == 21){
            	 gameMessage = "Player has Blackjack! Player wins this hand.";
            	 dealButton.setEnabled(false);
                 hitButton.setEnabled(false);
                 standButton.setEnabled(false);
                 continueButton.setEnabled(true);
            	 
             }else if(dealerScore == 21){
            	 gameMessage = "The Dealer has Blackjack! The Dealer wins this hand.";
            	 dealButton.setEnabled(false);
                 hitButton.setEnabled(false);
                 standButton.setEnabled(false);
                 continueButton.setEnabled(true);
             }
            	 


        
        }

        else if (e.getSource() == continueButton) {
            // Update buttons and messages:
            // (1) enable the deal button,
            // (2) disable the hit, stand, and continue buttons,
            // (3) set the game message to "Press Deal to deal a new hand"
            // (4) call the setUpCards method to set up for another hand
        	 dealButton.setEnabled(true);
             hitButton.setEnabled(false);
             standButton.setEnabled(false);
             continueButton.setEnabled(false);
             gameMessage = "Please press Deal to begin a new hand of Black Jack!";
             setUpCards();


        
        
        }

        else if (e.getSource() == hitButton) {
            //Deal one card to the player
        	playerHand[playerHandCount] = deck[nextCard];
            playerHandCount++;
            nextCard++;


            
            // Call the computeScore method to determine the player's score
            playerScore = computeScore(playerHand, playerHandCount);

            // TODO: if the player busts (i.e., the playerScore > 21) do the
            // following:
            // (1) enable the continue button,
            // (2) disable all other buttons buttons,
            // (3) set the game message to "Player Busts! Dealer Wins"
            if(playerScore > 21){
            dealButton.setEnabled(false);
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            continueButton.setEnabled(true);
            gameMessage = "Player Busts! The Dealer wins this hand.";
            }
        
        } else if (e.getSource() == standButton) {
            // turn up dealer's first card... set dealerCardDown = false;
            dealerCardDown = false;


            
            // TODO: Deal cards to the dealer until the dealer's score is >= 17.
            // The computeScore method can be used to determine the dealer's
            // score similar to how it was used above to determine the player's
            // score. It may not be necessary to deal any cards to get to 17.
            dealerScore = (computeScore(dealerHand, dealerHandCount));

            	while(dealerScore < 17){
            		dealerHand[dealerHandCount] = deck[nextCard];
                    dealerHandCount++;
                    nextCard++;
                    dealerScore = (computeScore(dealerHand, dealerHandCount));
            
            }


            
            // Once the dealer's score is known (and is 17 or more),
            // compute the player's score.
            playerScore = computeScore(playerHand, playerHandCount);

            
            
            // The hand is now over. At this point, there are 4 possible
            // outcomes (A) the dealer's score > 21 (the dealer busts and the
            // player wins). (B) the dealer's score > the player's score (dealer
            // wins). (C) the player's score > the dealer's score (player wins).
            // (D) it's a tie (a push)
            // Determine which outcome applies and set the game message
            // appropriately,
            if(dealerScore > 21 && playerScore > 21){
            	gameMessage = "The Dealer and the Player Bust! There is no winner.";
            } else if(dealerScore > 21){
                gameMessage = "The Dealer Busts! Player wins this hand.";
            	
            }else if(dealerScore > playerScore){
                gameMessage = "The Dealer wins this hand.";
            	
            }else if(playerScore > dealerScore){
                gameMessage = "Player wins this hand.";
            	
            }else if(dealerScore == playerScore){
                gameMessage = "Player and the Dealer tie.";
            	
            }


            
            
            // All that remains, is to allow the user to see the outcome
            // of the hand.
            // (1) enable the continue button,
            // (2) disable all other buttons,
            dealButton.setEnabled(false);
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            continueButton.setEnabled(true);


        
        
        }
        
        // request that the applet's paint method be executed.
        repaint();
    }

    private int computeScore(Card[] hand, int count) {
        // TODO: This method receives two parameters: a Card array that contains
        // the hand of either the player or the dealer and an integer that
        // indicates how many positions in that array actually have Card objects
        // in them. Process each card and determine the total score for the
        // hand, and return the score.
        //
        // A hand's score can be determined by adding up the 'worth' for all of
        // the cards in the hand. The trick is that Aces can count for either 11
        // or 1. As you determine the hand's score, start by assuming that all
        // Aces are worth 11 (this is the value used by the Card class), but
        // keep track of how many there are. If you arrive at a total score
        // greater than 21 this is a losing hand... But if there were Aces in
        // the hand, you can count one or more of the Aces as just 1 point.
        // Doing so could result in a score less than 21!
    	
        int score = 0;
        int aces = 0;
        
        for(int i = 0; i < count; i++){
        	score =score + hand[i].worth;
        	if(hand[i].worth == 11){
        		aces++;
        	}
        
        }
        while(aces != 0){
        	if(score > 21){
        		score = score -10;
        		aces--;
        	}else{
        		aces = 0;
        	}
        }


        
        return score;
    }
}
