package project.model;

import javax.swing.*;
import java.util.ArrayList;

/**
 *Class that represents a player
 */
public class player {

    int serialPlayerNum;
    public int getSerialPlayerNum(){
        return serialPlayerNum;
    }
    private void setSerialPlayerNum(int serialPlayerNum){
        this.serialPlayerNum = serialPlayerNum;
    }


    int money;
    public int getMoney(){
        return money;
    }
    public void setMoney(int money){
        this.money = money;
    }
    public void addMoney(int money){
        this.money += money;
    }


    int loan;
    public int getLoan(){
        return loan;
    }
    public void setLoan(int loan) {
        this.loan = loan;
    }
    /**
     * addLoan not only adds the
     * @param loan value to loan but adds it to the money as well
     */
    public void addLoan(int loan) {
        addMoney(loan);
        setLoan(getLoan() + loan);
    }


    int bills;
    public int getBills(){
        return bills;
    }
    public void setBills(int bills) {
        this.bills = bills;
    }
    public void addBills(int bills){
        this.bills += bills;
    }


    int positionDate;
    position position;
    public int getPositionDate() {
        return positionDate;
    }
    private void setPositionDate(int positionDate){
        this.positionDate = positionDate;
    }
    /**
     * sets the players position to the
     * @param position
     *
     * also sets the positionDate to the @param position's date
     */
    public void movePosition(position position){
        this.position = position;
        this.setPositionDate(position.date);
    }


    boolean hasFinished = false;
    public boolean isHasFinished() {
        return hasFinished;
    }
    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }



    ArrayList<dealCard> cardStorage = new ArrayList<>();
    public ArrayList<dealCard> getCardStorage(){
        return cardStorage;
    }
    public void addCard(dealCard newCard){
        this.cardStorage.add(newCard);
    }
    public dealCard removeCard(int num){
        dealCard carta = cardStorage.get(num);
        this.cardStorage.remove(num);
        return carta;
    }
    public void showCards(){
        if(cardStorage.size() == 0)
            JOptionPane.showMessageDialog(null, "You got no deal cards", "Buyers Tile", JOptionPane.PLAIN_MESSAGE);
        else
            for(int i = 0; i < cardStorage.size(); i++){
                JOptionPane.showMessageDialog(null,
                        "Buying Price = "+cardStorage.get(i).getBuyingPrice() +
                                "\nSelling Price = "+cardStorage.get(i).getSellingPrice(),
                        "Card No." + i, JOptionPane.PLAIN_MESSAGE);
            }
    }

    /** Constructor
     *
     * @param serialPlayerNum
     * @param startingPosition
     */
    public player(int serialPlayerNum, position startingPosition){
        setSerialPlayerNum(serialPlayerNum);
        setMoney(3500);
        setLoan(0);
        setBills(0);
        movePosition(startingPosition);

    }
}

