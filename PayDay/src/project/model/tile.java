package project.model;

import javax.swing.JOptionPane;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that represents a tile,
 * every tile has a position on the board,
 * when a player steps on a tile an action is performed,
 * the action differs by the tile
 *
 * Every tile has a serial Number to be recognised by the view and the "move to the deal/buyer tile" card
 *
 * serial numbers
 *     0- start
 *     1- mail 1 tile
 *     2- mail 2 tile
 *     3- deal tile
 *     4- laxeio tile
 *     5- lotaria
 *     6- radio
 *     7- buyer
 *     8- casino
 *     9- yard
 *     10-payday
 */
public abstract class tile {

    String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    int serialTileNumber;
    public int getSerialTileNumber() {
        return serialTileNumber;
    }
    public void setSerialTileNumber(int serialTileNumber) {
        this.serialTileNumber = serialTileNumber;
    }


    public void performAction(board board){    }

    /**
     * Constructors
     */
    public tile(){
        setSerialTileNumber(-1);
        setTitle(null);
    }
    public tile(int serialTileNumber){
        setSerialTileNumber(serialTileNumber);
        setTitle(null);
    }
    public tile(int serialTileNumber, String title){
        setSerialTileNumber(serialTileNumber);
        setTitle(title);
    }

}

/**
 * Represents the starting position
 * No actions are needed in this tile
 */
class startTile extends tile{

    public startTile(){
        super(0, "startTile");
    }

}

/**
 * Player draws 1 or 2 mail cards depending on the numberOfDraws
 */
abstract class mailTile extends tile{

    int numberOfDraws;

    public int getNumberOfDraws() {
        return numberOfDraws;
    }
    public void setNumberOfDraws(int numberOfDraws) {
        this.numberOfDraws = numberOfDraws;
    }
    public mailTile(int drawsAndSerial){
        super(drawsAndSerial, "mail" + drawsAndSerial +"tile");
    }

    public void performAction(board board){
        mailCard karta = board.getTopMailCard();
        karta.activateCard(board);
        board.addMailTrash(karta);
    }
    /*
    4 tiles for take1message
    4 tiles for take2message
    Σε αυτήν την περίπτωση, πρέπει ο παίκτης να τραβήξει τον
    αριθμό των καρτών που αναγράφεται στη θέση αυτή. Σε
    περίπτωση που η ενέργεια πρέπει να γίνει άμεσα, ο παίκτης
    πληρώνει/πληρώνεται αμέσως. Αλλιώς τα χρήματα
    μεταφέρονται στους λογαριασμούς που δεν έχει ξεπληρώσει.
    Στις περιπτώσεις που δε χρειάζεται η κάρτα να μείνει στην
    κατοχή του παίκτη τοποθετείται στη στοίβα απόρριψης. Αλλιώς,
    κρατείται μέχρι να την ξεπληρώσει ( στην περίπτωση μόνο της
    κάρτας λογαριασμού)
    Σε περίπτωση που τελειώσουν οι κάρτες, χρησιμοποιείται ξανά
    αυτές που έχουν απορριφθεί (τις ανακατεύεται ξανά).
    */


}
class mail1Tile extends mailTile {

    public mail1Tile(){
        super(1);
    }

    @Override
    public void performAction(board board) {
        super.performAction(board);
    }

    //4 tiles
}
class mail2Tile extends mailTile {

    public mail2Tile(){
        super(2);
    }

    @Override
    public void performAction(board board) {
        super.performAction(board);
        super.performAction(board);
    }

    /*
    4 tiles
    Σε περίπτωση που πρέπει να πάρετε 2 κάρτες και η πρώτη
    είναι η ‘Μετακίνηση στην πλησιέστερη θέση
    Συμφωνίας/Αγοραστή’, διαβάστε και την δεύτερη και μετά
    πηγαίνετε στη θέση που σας λέει η κάρτα.
    */

}

/**
 * Player draws a dealCard,
 * if he wants he can buy it,
 * if he has not enough money he takes a loan automatically
 */
class dealTile extends tile{

    int answer;

    public dealTile() {
        super(3, "dealTile");
    }

    public void performAction(board board){
        dealCard karta = board.getTopDealCard();
        answer = JOptionPane.showConfirmDialog(null, "Buying Price = "+ karta.getBuyingPrice() +
                "\nSelling Price = " + karta.getSellingPrice(),"Do you want to buy this deal card?", JOptionPane.YES_NO_OPTION);
        if(answer == 0) { //if answer is yes
            while(board.getPlayer(board.getWhosTurn()).getMoney() < karta.getBuyingPrice()){
                //System.out.println("Entered while loop coz player is broke");
                //if player wants to buy the card but doesn't have enough money, a loan is automatically taken
                board.getPlayer(board.getWhosTurn()).setLoan(board.getPlayer(board.getWhosTurn()).getLoan() + 1000);
                //System.out.println("Players loan: " + board.getPlayer(board.getWhosTurn()).getLoan());
                board.getPlayer(board.getWhosTurn()).addMoney(1000);
                //System.out.println("players money: " + board.getPlayer(board.getWhosTurn()).getMoney());
            }
            //System.out.println("got out the loop");
            board.getPlayer(board.getWhosTurn()).setMoney(board.getPlayer(board.getWhosTurn()).getMoney() - karta.getBuyingPrice());
            board.getPlayer(board.getWhosTurn()).addCard(karta);
            //System.out.println("got the card");
        }
        else {
            board.addDealTrash(karta); //petaei thn karta sto trash
        }
    }
    /*
    5 deal tiles
    Σε αυτήν την περίπτωση, πρέπει ο παίκτης να τραβήξει μία
    κάρτα τύπου Συμφωνίας (Deal). Έπειτα, αποφασίζει εάν θα
    αγοράσει το προϊόν ή όχι. Σε περίπτωση που το αγοράσει, η
    κάρτα κρατείται από τον παίκτη, αλλιώς απορρίπτεται. Ο
    παίκτης μπορεί οποιαδήποτε στιγμή να δει ποιες κάρτες έχει
    στην κατοχή του.
    Σε περίπτωση που τελειώσουν οι κάρτες, χρησιμοποιείται ξανά
    αυτές που έχουν απορριφθεί (τις ανακατεύεται ξανά).
    */
}

/**
 * Player rolls the dice again and gets money equal to the dice number x1000
 */
class laxeioTile extends tile{

    public laxeioTile(){
        super(4, "laxeioTile");
    }

    public void performAction(board board){

        int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);

        board.getPlayer(board.getWhosTurn()).addMoney(randomNum * 1000);

        JOptionPane.showMessageDialog(null, "Dice rolled " + randomNum
        + "\nYou got " + (randomNum * 1000) + " extra money", "Sweepstakes", JOptionPane.PLAIN_MESSAGE);
    }
    //2 tiles

    //Αν πέσει ο παίκτης σε αυτή θέση, τότε ρίχνει ξανά το ζάρι και
    //κερδίζει 1000 Χ τον αριθμό που έφερε με το ζάρι σε ευρώ
}

/**
 * the players choose a number between 1-6
 * they roll the dice until a number they chose occurs
 * the winner gets 1000 money
 */
class lotariaTile extends tile{

    public lotariaTile(){
        super(5);
    }

    @Override             //works
    public void performAction(board board) {
        int choiceA = -1;
        int choiceB = -1;
        int randomNum = -1;

        while(choiceA < 0 || choiceA > 6 || choiceB < 0 || choiceB > 6 || choiceA == choiceB){
            choiceA = Integer.parseInt(JOptionPane.showInputDialog("PlayerA give a number between 1 and 6"));
            choiceB = Integer.parseInt(JOptionPane.showInputDialog("PlayerB give a different number between 1 and 6"));
        }

        while(choiceA != randomNum && choiceB != randomNum){
            randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        }
        if(choiceA == randomNum){
            JOptionPane.showMessageDialog(null, "Dice roller " + randomNum
            + "\nPlayer A won 1000 money", "Lottery", JOptionPane.PLAIN_MESSAGE);
            board.getPlayer1().addMoney(1000);
        }
        else if(choiceB == randomNum) {
            JOptionPane.showMessageDialog(null, "Dice roller " + randomNum
                    + "\nPlayer B won 1000 money", "Lottery", JOptionPane.PLAIN_MESSAGE);
            board.getPlayer2().addMoney(1000);
        }

    }


    /*
    3 tiles
    Αν πέσει ο παίκτης σε αυτή θέση, τότε η τράπεζα προσφέρει
    1000 ευρώ σε ένα μόνο παίκτη μέσω κλήρωσης. Κάθε παίκτης
    διαλέγει έναν αριθμό από το 1-6, ξεκινώντας από τον παίκτη
    που βρίσκεται σε αυτό το τετράγωνο. Έπειτα γίνεται κλήρωση
    με το ζάρι μέχρι να έρθει ένας από τους 2 αριθμούς που
    επιλέχθηκαν. Μόλις γίνει αυτό, ο παίκτης που κερδίζει παίρνει
    το ποσό.
    */

}

/**
 * the players roll the dice
 * the player with the highest dice number gets 1000 money
 * if tied the roll is repeated
 */
class radioTile extends tile{

    public radioTile(){
        super(6);
    }

    @Override
    public void performAction(board board) { //works fine
        int roll1 = -1;
        int roll2 = -1;
        while(roll1 == roll2){
            roll1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            roll2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        }
        JOptionPane.showMessageDialog(null, "Player A rolled " + roll1
                + "\nPlayer B rolled " + roll2
                + "\nThe winner gets 1000 money", "Radio competition", JOptionPane.PLAIN_MESSAGE);
        if(roll1 > roll2) {
            board.getPlayer1().addMoney(1000);
        }
        else if(roll2 > roll1){
            board.getPlayer2().addMoney(1000);
        }
    }


    /*
    2 tiles
    Αν πέσει ο παίκτης σε αυτή θέση, τότε η τράπεζα προσφέρει
    1000 ευρώ στον παίκτη που θα φέρει τον μεγαλύτερο αριθμό .
    Σε περίπτωση ισοπαλίας, οι παίκτες ρίχνουν το ζάρι ξανά.
    Πρώτος ξεκινάει να ρίχνει το ζάρι ο παίκτης που πήγε σε αυτό
    το τετράγωνο.
    */

}

/**
 * The player can choose one of their deal cards and sell it
 */
class buyerTile extends tile{

    public buyerTile(){
        super(7);
    }

    int answer = -1;

    @Override
    public void performAction(board board) {
        board.getPlayer(board.getWhosTurn()).showCards();
        if(board.getPlayer(board.getWhosTurn()).getCardStorage().size() != 0) {
            while(answer < 0 || answer >= board.getPlayer(board.getWhosTurn()).getCardStorage().size()) {
                answer = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter the number of the card you want to sell",
                        "Buyers Tile", JOptionPane.PLAIN_MESSAGE));
            }
            board.getPlayer(board.getWhosTurn()).addMoney(board.getPlayer(board.getWhosTurn()).removeCard(answer).sellingPrice);
        }
    }

    /*
    6 tiles
    Σε αυτή τη θέση, κάποιος πρέπει να πουλήσει ένα μόνο προϊόν
    που έχει αγοράσει πριν μέσω μίας κάρτας συμφωνίας. Οπότε
    αν έχει ένα ή παραπάνω προϊόντα, πρέπει να του δίνεται η
    επιλογή να επιλέξει ποιο θέλει να πουλήσει. Αν το κάνει αυτό,
    μεταφέρονται τα χρήματα στο λογαριασμό του και η κάρτα
    φεύγει από την κατοχή του παίκτη.
    */

}

/**
 * if player's dice number is odd, he gives 500 money to the jackpot
 * if its even he gets 500 money from the bank
 */
class casinoTile extends tile{

    public casinoTile(){
        super(8);
    }

    @Override
    public void performAction(board board) {
        int diceRolled;
        boolean win;

        if(board.getWhosTurn() == 0){
            diceRolled = board.getDice1();
            if(diceRolled == 1 || diceRolled == 3 || diceRolled == 5){
                win = false;
                board.getPlayer1().setMoney(board.getPlayer1().getMoney() - 500);
                board.addToJackpot(500);
            }
            else{
                win = true;
                board.getPlayer1().addMoney(500);
            }
        }
        else{
            diceRolled = board.getDice2();
            if(diceRolled == 1 || diceRolled == 3 || diceRolled == 5){
                win = false;
                board.getPlayer2().setMoney(board.getPlayer1().getMoney() - 500);
                board.addToJackpot(500);
            }
            else{
                win = true;
                board.getPlayer2().addMoney(500);
            }
        }
        if(win) {
            JOptionPane.showMessageDialog(null, "You won 500 money", "Family Casino Night", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "You gave 500 money to the jackpot", "Family Casino Night", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /*
    2 tiles
    Εάν ο παίκτης έφτασε σε αυτή τη θέση φέρνοντας περιττό
    αριθμό στο ζάρι (1, 3 ή 5) τότε δίνει 500 ευρώ στο Jackpot.
    Όμως, εάν ο παίκτης έφτασε σε αυτή τη θέση φέρνοντας άρτιο
    αριθμό στο ζαρι (2, 4 ή 6) τότε παίρνει 500 ευρώ από την
    τράπεζα.
    */

}

/**
 * the player rolls the dice and gets money equal to the dice number x100
 * then he gets the top deal card without paying its buying price
 */
class yardSaleTile extends tile{

    public yardSaleTile(){
        super(9);
    }

    @Override
    public void performAction(board board) {
        int roll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        JOptionPane.showMessageDialog(null, "Dice rolled " + roll
                +"\nYou get charged " + (roll * 1000) + " money but you get one deal card in return"
                ,"Yard Sale", JOptionPane.PLAIN_MESSAGE);
        board.getPlayer(board.getWhosTurn()).setMoney(board.getPlayer(board.getWhosTurn()).getMoney() - (roll * 1000));
        while(board.getPlayer(board.getWhosTurn()).getMoney() < 0){
            board.getPlayer(board.getWhosTurn()).addLoan(1000);
        }
        board.getPlayer(board.getWhosTurn()).addCard(board.getTopDealCard());
    }

    //2 tiles

    //Ο παίκτης ρίχνει το ζάρι ξανά και πληρώνει την τράπεζα 100 x
    //τον αριθμό που έφερε σε ευρώ. Έπειτα παίρνει την πρώτη
    //κάρτα που βρίσκεται στη στοίβα των καρτών συμφωνίας χωρίς
    //να πληρώσει άλλα χρήματα.
}

/**
 * this tile is always at the last position of the board,
 * the player must stop even if the dice says he must go forward,
 * gets 3500 money from the bank
 * pays the bills that he has not yet paid,
 * for the loans he has not paid yet, he pays as a tax 10% of them
 * and if he can not afford it, he takes a new loan
 * In advance to the tax, he can pay a loan or a part of it, so that he has to pay less the next month,
 * if its the last month, then the player does not play again and their deal cards go to the deal card trash,
 * if its not the last month, the players pawn moves to the start.
 */
class payDayTile extends tile{

    public payDayTile(){
        super(10);
    }

    int answer;
    int repayMoney = -1;

    @Override
    public void performAction(board board) {
        player player = board.getPlayer(board.getWhosTurn());

        //Paying the bills and getting 3500 money
        player.setMoney(player.getMoney() - player.getBills() + 3500);

        //Paying the loan charge
        int loanCharge = (int) (0.1 * player.getLoan());
        if(player.getMoney() < loanCharge) {
            while (player.getMoney() < loanCharge) {
                player.setLoan(player.getLoan() + 1000);
                player.addMoney(1000);
            }
        }
        player.setMoney(player.getMoney() - loanCharge);
        //Paying the loan
        if(player.getMoney() > 999 && player.getLoan() > 999) {
            answer = JOptionPane.showConfirmDialog(null, "Do you want to repay the loan or a part of it?" +
                    "\nYour money: " + player.getMoney() + "\nYour loan: " + player.getLoan(), "Loan Repay", JOptionPane.YES_NO_OPTION);
            if (answer == 0) { //if answer is yes
                while (repayMoney < 0 || repayMoney * 1000 > player.getMoney()) {
                    repayMoney = Integer.parseInt(JOptionPane.showInputDialog(null,
                            "Enter the number of x1000 money you want to repay", "Loan Repay", JOptionPane.PLAIN_MESSAGE));
                }
                player.setMoney(player.getMoney() - repayMoney * 1000);
                player.setLoan(player.getLoan() - repayMoney * 1000);
            }
        }
    }
    /*
    1 tile: always the last one
    Εδώ ο παίκτης είναι αναγκασμένος να σταματήσει ακόμα και αν
    το ζάρι έδειξε παραπάνω. Έπειτα κάνει τα παρακάτω.
    1. Παίρνει μισθό 3500 ευρώ από την τράπεζα.
    2. Πληρώνει τους λογαριασμούς που δεν έχει πληρώσει
    και απορρίπτει τις κάρτες αυτές.
    3. Για το δάνειο που δεν έχει ξεπληρώσει αυτή τη στιγμή,
    πληρώνει άμεσα φόρο 10%. Για παράδειγμα αν έχει
    πάρει δάνειο 2000 ευρώ, πρέπει να πληρώσει άμεσα
    200 ευρώ. Αν δεν έχει τα λεφτά πρέπει να πάρει και
    άλλο δάνειο.
    4. Εκτός από το φόρο, ο παίκτης μπορεί να πληρώσει το
    δάνειο ή μέρος του δανείου. Πρέπει το ποσό που θα
    πληρώσει να είναι πολλαπλάσιο του 1000. Σε
    περίπτωση που δε πληρώσει το δάνειο ή μέρος του
    δανείου, το αρνητικό είναι ότι για τα χρήματα που δεν
    έχει ξεπληρώσει θα πρέπει να πληρώσει ξανά φόρο και
    τον επόμενο μήνα (ή τους επόμενους μήνες).
    5. Αν είναι ο τελευταίος μήνας τότε ο παίκτης δε παίζει
    ξανά και τοποθετεί όλες τις κάρτες συμφωνίας που έχει
    στη στοίβα απόρριψης.
    6. Αν δεν είναι ο τελευταίος μήνας, τότε το πιόνι του παίκτη
    πάει ξανά στην αρχή.
    */
}