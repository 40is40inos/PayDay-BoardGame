package project.model;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that represents a card
 */
public abstract class card {

    public void activateCard(board board){
        //performs the action of the card
    }
}

/**
 * Class that represents a mail card
 * there are 48 child objects of mail cards
 */
abstract class mailCard extends card{

    //48 cards

    int money;
    boolean mandatory;      //true: if player cannot pay the money, they must take a loan

    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public void setMandatory(boolean mandatory){
        this.mandatory = mandatory;
    }
    public boolean isMandatory(){
        return this.mandatory;
    }

}

/**
 * the player on turn pays the opponent the amount indicated
 * if needed the player takes loan
 *
 */
class payTheNeighborCard extends mailCard {

    @Override
    public void activateCard(board board) {
        while(board.getPlayer(board.getWhosTurn()).getMoney() < 2000){
            board.getPlayer(board.getWhosTurn()).addLoan(1000);
        }
        board.getPlayer(board.getWhosTurn()).setMoney(board.getPlayer(board.getWhosTurn()).getMoney() - 2000);
        if(board.getWhosTurn() == 0)
            board.getPlayer(1).addMoney(2000);
        else
            board.getPlayer(0).addMoney(2000);
        JOptionPane.showMessageDialog(null, "You gave 2000 money to the other player" +
                "\nIf you had less than 2000, automatically, you took a loan", "Pay The Neighbor Card", JOptionPane.PLAIN_MESSAGE);

    }

    public payTheNeighborCard(){
        setMoney(2000);
        setMandatory(true);
    }
    //8 cards

    //Με αυτήν την κάρτα πρέπει ο παίκτης να
    //πληρώσει άμεσα στον αντίπαλο το ποσό
    //που αναγράφεται εκεί. Αν χρειαστεί πρέπει
    //να πάρει και δάνειο.
}
/**
 * the opponent pays the player on turn the amount indicated
 * if needed the opponent takes loan
 *
 */
class takeFromTheNeighborCard extends mailCard {

    @Override
    public void activateCard(board board) {
        if(board.getWhosTurn() == 0) {
            while (board.getPlayer(1).getMoney() < 2000) {
                board.getPlayer(1).addLoan(1000);
            }
            board.getPlayer(1).setMoney(board.getPlayer(1).getMoney() - 2000);
        }
        else{
            while (board.getPlayer(0).getMoney() < 2000) {
                board.getPlayer(0).addLoan(1000);
            }
            board.getPlayer(0).setMoney(board.getPlayer(0).getMoney() - 2000);
        }


        board.getPlayer(board.getWhosTurn()).addMoney(2000);

        JOptionPane.showMessageDialog(null, "You took 2000 money from the other player" +
                "\nIf they had less than 2000, automatically, they took a loan", "Take From The Neighbor Card", JOptionPane.PLAIN_MESSAGE);

    }

    public takeFromTheNeighborCard(){
        setMoney(2000);
        setMandatory(true);
    }
    //8 cards

    //Με αυτήν την κάρτα πρέπει ο παίκτης να
    //πάρει άμεσα από τον αντίπαλο του το ποσό
    //που αναγράφεται στην κάρτα. Αν χρειαστεί
    //πρέπει να πάρει ο αντίπαλος δάνειο
}

/**
 * Player gives the amount indicated to the jackpot
 */
class charityCard extends mailCard {

    @Override
    public void activateCard(board board){
        while(board.getPlayer(board.getWhosTurn()).getMoney() < 2000){
            board.getPlayer(board.getWhosTurn()).addLoan(1000);
        }
        board.getPlayer(board.getWhosTurn()).setMoney(board.getPlayer(board.getWhosTurn()).getMoney() - 2000);
        board.addToJackpot(2000);
        JOptionPane.showMessageDialog(null, "You gave 2000 to the Jackpot" +
                "\nIf you had less than 2000, automatically, you took a loan", "Charity Card", JOptionPane.PLAIN_MESSAGE);
    }


    public charityCard(){
        setMoney(2000);
        setMandatory(true);
    }
    //8 cards

    //Με αυτήν την κάρτα το ποσό που
    //αναγράφεται δίνεται στο Jackpot. Αν
    //χρειαστεί πρέπει να πάρει ο παίκτης και
    //δάνειο.
}

/**
 * The amount indicated is added to the player's bill
 *
 */
class billCard extends mailCard {

    public void activateCard(board board){
        board.getPlayer(board.getWhosTurn()).addBills(3000);
        JOptionPane.showMessageDialog(null, "You got extra 3000 on your bills" +
                "\nYou got to pay them on pay day", "Bill Card", JOptionPane.PLAIN_MESSAGE);
    }

    public billCard(){
        setMoney(3000);
        setMandatory(false);
    }
    //8 cards

    //Με αυτήν την κάρτα ο παίκτης πληρώνει
    //στην τράπεζα το ποσό που αναγράφεται
    //στην κάρτα στο τέλος του μήνα. Η κάρτα
    //μένει στην κατοχή του μέχρι να την
    //πληρώσει.
}

/**
 * player moves to the closest deal/buyer tile
 */
class moveToDealBuyerCard extends mailCard {

    @Override
    public void activateCard(board board) {
        int i = board.getPlayer(board.getWhosTurn()).getPositionDate() + 1;

        while(board.getPositionAr(i).getPositionsTile().getSerialTileNumber() != 7
                && board.getPositionAr(i).getPositionsTile().getSerialTileNumber() != 3){
            //while tile i isn't a buyerTile or DealTile
            if(i == 31)
                break;
        }
        if(i < 31) {
            board.getPlayer(board.getWhosTurn()).movePosition(board.getPositionAr(i));
            JOptionPane.showMessageDialog(null, "You got moved to the closest deal/buyer tile",
                    "Move To Deal/Buyer Card", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "There is no deal/buyer tile in front of you to move to",
                    "Move To Deal/Buyer Card", JOptionPane.PLAIN_MESSAGE);
        }

    }

    public moveToDealBuyerCard(){}
    //8 cards

    //Με αυτήν την κάρτα πάς κατευθείαν στην
    //πλησιέστερη θέση Συμφωνίας/Αγοραστή
}

/**
 * amount indicated is added to the player's money
 */
class advertisementCard extends mailCard {

    @Override
    public void activateCard(board board) {
        board.getPlayer(board.getWhosTurn()).addMoney(3000);
        JOptionPane.showMessageDialog(null, "You got 3000 money from this card", "Advertisement Card", JOptionPane.PLAIN_MESSAGE);
    }

    public advertisementCard(){
        setMoney(3000);
        setMandatory(true);
    }
    //8 cards

    //Αυτή η κάρτα περιέχει μία διαφήμιση και
    //μπορείς να την πουλήσεις για 20 ευρώ.
}

/**
 * If player wants, he can buy the deal card at buying price,
 * if he steps on a deal/buyer tile he can sell it at selling price
 */
class dealCard extends card{

    //20 cards

    //Αυτές οι κάρτες είναι προαιρετικές, δηλαδή ένας παίκτης μπορεί να αγοράσει ένα προϊόν μόνο
    //εάν το επιθυμεί. Κάθε κάρτα έχει δύο τιμές, την τιμή αγοράς και την τιμή πώλησης. Ο παίκτης
    //μπορεί να αγοράσει ένα προϊόν στην τιμή αγοράς μέσω μιας τέτοιας κάρτας. Έπειτα, αν είναι
    //τυχερός και πέσει σε θέση Αγοραστή(Buyer), μπορεί να πουλήσει το προϊόν αυτό στην τιμή
    //πώλησης. Αν όμως δεν καταφέρει να πουλήσει ένα ή παραπάνω προϊόντα από αυτά που έχει
    //αγοράσει, τότε δε θα κερδίσει κάτι από αυτές τις κάρτες. Αν χρειαστεί να πληρώσει και δεν έχει
    //το ποσό πρέπει να πάρει δάνειο.

    int buyingPrice;
    int sellingPrice;

    public void setBuyingPrice(int buyingPrice){
        this.buyingPrice = buyingPrice;
    }
    public void setSellingPrice(int sellingPrice){
        this.sellingPrice = sellingPrice;
    }
    public int getBuyingPrice(){
        return this.buyingPrice;
    }
    public int getSellingPrice(){
        return this.sellingPrice;
    }

    public dealCard(){
        int randomInt = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        setBuyingPrice(randomInt * 1000);
        setSellingPrice((randomInt + 3)*1000);
    }


}

