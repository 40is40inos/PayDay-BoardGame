package project.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 *Class that represents the board
 */
public class board {

    //positions
    position[] positionAr = new position[32];
    public position getPositionAr(int date){
        return positionAr[date];
    }
    public void setPositionToAr(position position){
        this.positionAr[position.date] = position;
    }
    public position[] getPositionAr(){
        return positionAr;
    }

    //months
    int months = 1;
    int currentMonth = 1;
    int monthsPassed[] = {0,0};
    public int getMonths(){
        return months;
    }
    public void setMonths(int months){
        this.months = months;
    }
    public int getCurrentMonth(){
        return currentMonth;
    }
    /**
     *
     * monthPassed() is used when a player reaches payday,
     * It tracks how many times a player reached it
     * and when both players do, the current month rises.
     *
     * When current month rises above the months of the game,
     * the game ends and getWinner() gets called.
     */
    public void monthPassed(){
        monthsPassed[getWhosTurn()]++;
        if(monthsPassed[getWhosTurn()] >= getMonths()){
            players[getWhosTurn()].setHasFinished(true);
        }
        if(players[0].isHasFinished() && players[1].isHasFinished()){
            System.out.println("The game has ended");
            getWinner();
            System.exit(0);
        }
        if(monthsPassed[0] >= currentMonth && monthsPassed[1] >= currentMonth){
            System.out.println("currentMonth++");
            currentMonth++;
        }

    }

    //jackpot
    int jackpot = 0;
    public void addToJackpot(int money){
        this.jackpot += money;
        System.out.println(money + " money added to jackpot");
    }
    public int getJackpot(){
        return jackpot;
    }
    public void emptyTheJackpot(){
        jackpot = 0;
        System.out.println("jackpot is now empty");
    }

    //dice
    int dice1, dice2;
    public int rollTheDice1() {
        dice1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        return dice1;
    }
    public int getDice1(){
        return dice1;
    }
    public int rollTheDice2() {
        dice2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        return dice2;
    }
    public int getDice2(){
        return dice2;
    }


    //Cards
    ArrayList<mailCard> mailCardPile = new ArrayList<>();
    ArrayList<dealCard> dealCardPile = new ArrayList<>();
    ArrayList<mailCard> mailTrash = new ArrayList<>();
    ArrayList<dealCard> dealTrash = new ArrayList<>();

    /**
     * getTopMailCard()/getTopDealCard() returns the top card of the pile,
     *
     * if the pile is empty they use reUseMailTrash()/reUseDealTrash()
     */
    public mailCard getTopMailCard(){
        if(this.mailCardPile.size() == 0){
            this.reUseMailTrash();
        }
        mailCard mailCard = this.mailCardPile.get(mailCardPile.size() - 1);
        this.mailCardPile.remove(mailCardPile.size() - 1);
        return mailCard;
    }
    public dealCard getTopDealCard(){
        if(this.dealCardPile.size() == 0){
            this.reUseDealTrash();
        }
        dealCard dealCard = this.dealCardPile.get(dealCardPile.size() - 1);
        this.dealCardPile.remove(dealCardPile.size() - 1);
        return dealCard;
    }

    /**
     * addMailTrash/addDealTrash are used to send the @param card to the trash pile,
     *
     * they are called when a mailCard is used or when a dealCard is rejected or sold
     */
    public void addMailTrash(mailCard mailCard){
        mailTrash.add(mailCard);
        System.out.println("mail card added to trash pile");
    }
    public void addDealTrash(dealCard dealCard){
        dealTrash.add(dealCard);
        System.out.println("deal card added to trash pile");
    }

    /**
     * reUseMailTrash()/reUseDealTrash() are called by getTopMailCard()/getTopDealCard()
     * when there are no cards in the pile,
     * they take all the cards from mailTrash/dealTrash and return them to mailCardPile/dealCardPile,
     * then they call shufflePiles().
     */
    public void reUseMailTrash(){
        mailCardPile.clear();
        mailCardPile.addAll(mailTrash);
        mailTrash.clear();
        System.out.println("mail cards restored from trash");
        shufflePiles();
    }
    public void reUseDealTrash(){
        dealCardPile.clear();
        dealCardPile.addAll(dealTrash);
        dealTrash.clear();
        System.out.println("deal cards restored from trash");
        shufflePiles();
    }

    /**
     * shufflePiles() shuffles the cards in mailCardPile and dealCardPile
     */
    public void shufflePiles(){
        Collections.shuffle(mailCardPile);
        Collections.shuffle(dealCardPile);
        System.out.println("cards shuffled");
    }

    //Players
    int whosTurn = 0; // 0 == Player A, 1 == Player B
    player[] players = new player[2];
    public player getPlayer(int num){
            return players[num];
    }
    public player getPlayer1(){
        return players[0];
    }
    public player getPlayer2(){
        return players[1];
    }

    /**
     * changeTurn() gets called when a player pushes the "End Turn" button and changes the turn
     */
    public void changeTurn() {
        if (getWhosTurn() == 0)
            whosTurn = 1;
        else
            whosTurn = 0;
        System.out.println("Turn changed");
    }
    public int getWhosTurn() {
        return whosTurn;
    }

    //Initializers
    /**
     * Adds the new created positions with new created tiles to a temporary ArrayList</position>,
     * then shuffles the ArrayList so that the tiles will be placed random every time,
     * then copies the temporary ArrayList to the position Array,
     * advances all array items by one so the starting position can fit in the first place,
     * adds the staring position in the first place
     * adds the payday position in the last place
     *
     */
    public void tilesInit(){

        ArrayList<position> positionArrayList = new ArrayList<>();

        for( int i = 0; i < 4; i++)
            positionArrayList.add(new cardPosition(0, new mail1Tile()));
        for( int i = 0; i < 4; i++)
            positionArrayList.add(new cardPosition(0, new mail2Tile()));
        for( int i = 0; i < 5; i++)
            positionArrayList.add(new cardPosition(0, new dealTile()));
        for( int i = 0; i < 2; i++)
            positionArrayList.add(new onePlayerDicePosition(0, new laxeioTile()));
        for( int i = 0; i < 3; i++)
            positionArrayList.add(new twoPlayerDicePosition(0, new lotariaTile()));
        for( int i = 0; i < 6; i++)
            positionArrayList.add(new buyerPosition(0, new buyerTile()));
        for( int i = 0; i < 2; i++)
            positionArrayList.add(new onePlayerDicePosition(0, new casinoTile()));
        for( int i = 0; i < 2; i++)
            positionArrayList.add(new onePlayerDicePosition(0, new yardSaleTile()));
        for( int i = 0; i < 2; i++)
            positionArrayList.add(new twoPlayerDicePosition(0, new radioTile()));

        //randomising the tile order, except 0: start and 31: payday which are added after that
        Collections.shuffle(positionArrayList);

        positionArrayList.toArray(positionAr);

        for(int i = 29; i >= 0; i--){
            positionAr[i + 1] = positionAr[i];
            positionAr[i + 1].setDate(i + 1);
        }

        positionAr[0] = new position(0, new startTile());
        positionAr[31] = new position(31, new payDayTile());
    }

    /**
     * Creates all the mailCards and dealCards, adds them to their piles
     * then calls shufflePiles() so they get shuffled
     */
    public void cardPilesInit(){

        for(int i = 0; i < 8; i++)
            mailCardPile.add(new payTheNeighborCard());
        for(int i = 0; i < 8; i++)
            mailCardPile.add(new takeFromTheNeighborCard());
        for(int i = 0; i < 8; i++)
            mailCardPile.add(new charityCard());
        for(int i = 0; i < 8; i++)
            mailCardPile.add(new billCard());
        for(int i = 0; i < 8; i++)
            mailCardPile.add(new moveToDealBuyerCard());
        for(int i = 0; i < 8; i++)
            mailCardPile.add(new advertisementCard());

        for(int i = 0; i < 20; i++)
            dealCardPile.add(new dealCard());

        shufflePiles();
    }


    /**
     * Gets called by the controller,
     * It calls the Action of each Tile so it can be performed
     *
     * It also implements the action of the special days (thursday and sunday)
     * which could not be implemented on new tiles
     */
    public void tileAction(){
        //performs Action of the Tile which the player is on
        this.positionAr[this.players[whosTurn].getPositionDate()].getPositionsTile().performAction(this);

        //Special days
        int playersDate = this.getPlayer(this.getWhosTurn()).getPositionDate();
        //SUNDAY FOOTBALL DAY
        if (playersDate == 7 || playersDate == 14 || playersDate == 21 || playersDate == 28) {
            int answer;
            if (this.getPlayer(this.getWhosTurn()).getMoney() >= 500) {
                answer = JOptionPane.showConfirmDialog(null, "Do you want to gamble 500 money on the match?",
                        "Sunday Football Day", JOptionPane.YES_NO_OPTION);
                if (answer == 0) { //if answer is yes
                    String answer2 = "0";
                    while (!(answer2.equals("1") || answer2.equals("X") || answer2.equals("2"))) {
                        answer2 = JOptionPane.showInputDialog(null, "Enter 1, X or 2" +
                                "\n1 --> Home wins" +
                                "\nX --> Its a draw" +
                                "\n2 --> Guest wins", "Sunday Football Day", JOptionPane.PLAIN_MESSAGE);
                    }
                    int result = ThreadLocalRandom.current().nextInt(1, 3 + 1);
                    if ((answer2.equals("1") && result == 1) || (answer2.equals("X") && result == 3) || (answer2.equals("2") && result == 2)) {
                        JOptionPane.showMessageDialog(null, "Your guess was right!" +
                                        "\nYou won 500 extra money",
                                "Sunday Football Day", JOptionPane.PLAIN_MESSAGE);
                        this.getPlayer(this.getWhosTurn()).addMoney(500);
                    } else {
                        String matchResult;
                        if(result == 1)
                            matchResult = "Home Win";
                        else if(result == 2)
                            matchResult = "Guest Win";
                        else
                            matchResult = "Draw";
                        JOptionPane.showMessageDialog(null, "Your guess was wrong" +
                                        "\nMatch resulted in a " + matchResult +
                                        "\nYou lost 500 money",
                                "Sunday Football Day", JOptionPane.PLAIN_MESSAGE);
                        this.getPlayer(this.getWhosTurn()).setMoney(this.getPlayer(this.getWhosTurn()).getMoney() - 500);
                    }
                }
            } else{ // money < 500
                JOptionPane.showMessageDialog(null, "You do not have enough money to gamble",
                        "Sunday Football Day", JOptionPane.PLAIN_MESSAGE);
            }
            /*
            Όποιος πάει σε θέση όπου είναι Κυριακή, έχει τη δυνατότητα να στοιχηματίσει αν θέλει 500
            ευρώ σε ένα αγώνα ποδοσφαίρου και υπάρχουν 3 ενδεχόμενα α) νίκη γηπεδούχου β)
            ισοπαλία γ) νίκη φιλοξενούμενου. Ανάλογα με το τι θα φέρει το ζάρι και με την επιλογή του
            παίκτη μπορεί να διπλασιάσει ή να χάσει τα χρήματα που πόνταρε . Ο παίκτης διπλασιάζει τα
            χρήματα που πόνταρε εάν α) στοιχημάτισε σε νίκη γηπεδούχου και το ζάρι έφερε 1 ή 2 β)
            στοιχημάτισε σε ισοπαλία και το ζάρι έφερε 3 ή 4 στη σειρά και γ) στοιχημάτισε σε νίκη
            φιλοξενούμενου και το ζάρι έφερε 5 ή 6. Σε κάθε άλλη περίπτωση χάνει τα χρήματα του.
            Μπορείτε να θεωρήσετε ότι η κλήρωση γίνεται μέσα στο πρόγραμμα σας (χωρίς να χρειαστεί
            να ρίξει ο παίκτης το ζάρι), οπότε αυτόματα ενημερώνεται για το εάν κέρδισε ή όχι.
            */
        }
        //THURSDAY CRYPTO DAY
        else if(playersDate == 4 || playersDate == 11 || playersDate == 18 || playersDate == 25){
            int answer;
            if (this.getPlayer(this.getWhosTurn()).getMoney() >= 300) {
                answer = JOptionPane.showConfirmDialog(null, "Do you want to buy the crypto for 300 money?",
                        "Thursday Crypto day", JOptionPane.YES_NO_OPTION);
                if (answer == 0) { //if answer is yes
                    int result = ThreadLocalRandom.current().nextInt(1, 3 + 1);
                    if(result == 1){
                        JOptionPane.showMessageDialog(null, "The Crypto's price dropped" +
                                        "\nYou lost 300 money",
                                "Thursday Crypto day", JOptionPane.PLAIN_MESSAGE);
                        this.getPlayer(this.getWhosTurn()).setMoney(this.getPlayer(this.getWhosTurn()).getMoney() - 300);
                    }
                    else if(result == 2){
                        JOptionPane.showMessageDialog(null, "The Crypto's price stabilized",
                                "Thursday Crypto day", JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "The Crypto's price increased" +
                                        "\nYou gained 300 money",
                                "Thursday Crypto day", JOptionPane.PLAIN_MESSAGE);
                        this.getPlayer(this.getWhosTurn()).addMoney(300);
                    }
                }
            } else{ // money < 300
                JOptionPane.showMessageDialog(null, "You do not have enough money to gamble",
                        "Sunday Football Day", JOptionPane.PLAIN_MESSAGE);
            }
             /*
            Όποιος πάει σε θέση όπου είναι Πέμπτη, έχει τη δυνατότητα να στοιχηματίσει αν θέλει 300
            ευρώ σε ένα κρυπτονόμισμα και υπάρχουν 3 ενδεχόμενα α) πτώση αξίας β) σταθεροποίηση
            αξίας γ) άνοδος αξίας. Ανάλογα με το τι θα φέρει το ζάρι και με την επιλογή του παίκτη μπορεί
            να διπλασιάσει, να χάσει τα χρήματα που πόνταρε ή να τα πάρει πίσω . Ο παίκτης χάνει τα
            χρήματα που πόνταρε εάν α) με το ζάρι έφερε 1 ή 2 β) παίρνει τα λεφτά του πίσω αν με το ζάρι
            έφερε 3 ή 4 στη σειρά και γ) κερδίζει τα διπλά αν με το ζάρι έφερε 5 ή 6. Μπορείτε να θεωρήσετε
            ότι η κλήρωση γίνεται μέσα στο πρόγραμμα σας (χωρίς να χρειαστεί να ρίξει ο παίκτης το ζάρι),
            οπότε αυτόματα ενημερώνεται για το εάν κέρδισε ή όχι.
            */
        }
    }

    /**
     *
     * getWinner() is called when the game ends by monthsPassed()
     * It counts the money, loans, and bills of the players and calculates who is the winner
     */
    public void getWinner(){
        int moneyA = getPlayer1().getMoney() - getPlayer1().getLoan() - getPlayer1().getBills();
        int moneyB = getPlayer2().getMoney() - getPlayer2().getLoan() - getPlayer2().getBills();

        if(moneyA > moneyB) {
            JOptionPane.showMessageDialog(null,"Player A is the winner with " + moneyA + " money" +
                    "\nPlayer B got " + moneyB + " money", "Congratulation", JOptionPane.PLAIN_MESSAGE);
        }
        else if(moneyA < moneyB){
            JOptionPane.showMessageDialog(null,"Player B is the winner with " + moneyB + " money" +
                    "\nPlayer A got " + moneyA + " money", "Congratulation", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null,"WE GOT A DRAW!!!" +
                    "You both got " + moneyA + " money" +
                    "\nVery Rare", "Congratulation", JOptionPane.PLAIN_MESSAGE);
        }
    }


    /** Constructor
     *
     *  Initialises the board with its tiles and cards,
     *  creates the players and sets them to starting position,
     *  Sets the months that will be played by random.
     */
    public board(){

        tilesInit();
        System.out.println("tiles initialised");
        cardPilesInit();
        System.out.println("card piles initialised");

        players[0] = new player(0,positionAr[0]);
        players[1] = new player(1,positionAr[0]);
        System.out.println("players joined");

        setMonths(1);
        //setMonths(ThreadLocalRandom.current().nextInt(1, 3 + 1));
        JOptionPane.showMessageDialog(null,"the game will be played for " + getMonths()
                + " months", "The Game of PayDay begins", JOptionPane.PLAIN_MESSAGE);
    }
}
