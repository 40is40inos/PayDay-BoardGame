package project.controller;


import project.model.board;

import project.view.PayDayView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Controller controls all the operations executed
 * and communicates the model with the view
 */
public class PayDayController {

    public PayDayView theView;

    public board theBoard;

    public PayDayController(PayDayView theView, board theBoard){

        this.theBoard = theBoard;
        this.theView = theView;

        this.theView.addRollDice1Listener(new rollDice1Listener());
        this.theView.addRollDice2Listener(new rollDice2Listener());
        this.theView.addEndTurn1Listener(new endTurn1Listener());
        this.theView.addEndTurn2Listener(new endTurn2Listener());
        this.theView.addGetLoan1Listener(new getLoan1Listener());
        this.theView.addGetLoan2Listener(new getLoan2Listener());
        this.theView.addMyDealCards1Listener(new myDealCards1Listener());
        this.theView.addMyDealCards2Listener(new myDealCards2Listener());
    }

    class rollDice1Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int newDatePosition;
            if(!theBoard.getPlayer1().isHasFinished()) {
                if (theBoard.getWhosTurn() == 0) {
                    theBoard.rollTheDice1();
                    //Jackpot
                    if(theBoard.getDice1() == 6){
                        theBoard.getPlayer1().addMoney(theBoard.getJackpot());
                        theBoard.emptyTheJackpot();
                        JOptionPane.showMessageDialog(null,"Dice rolled 6, You get the jackpot",
                                "Jackpot", JOptionPane.PLAIN_MESSAGE);
                    }
                    if (theBoard.getPlayer1().getPositionDate() == 31) {
                        newDatePosition = theBoard.getDice1();
                        theBoard.getPlayer1().movePosition(theBoard.getPositionAr(newDatePosition));
                    } else {
                        newDatePosition = theBoard.getDice1() + theBoard.getPlayer1().getPositionDate();
                        if (newDatePosition < 31) {
                            theBoard.getPlayer1().movePosition(theBoard.getPositionAr(newDatePosition));
                        } else {
                            theBoard.getPlayer1().movePosition(theBoard.getPositionAr(31));
                            theBoard.monthPassed();
                        }
                    }

                    theView.updatePlayersDice(theBoard);
                    theView.updatePawnsPosition(theBoard);
                    theView.updateInfoBox(theBoard);
                    theView.enableDice(false);
                    theBoard.tileAction();
                }
            }
            theView.updatePlayerPanel(theBoard);
        }
    }
    class endTurn1Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(theBoard.getWhosTurn() == 0){
                if(!(theBoard.getPlayer2().isHasFinished())) {
                    theBoard.changeTurn();
                }
                theView.enableDice(true);
                theView.updateInfoBox(theBoard);
            }
            theView.updatePlayerPanel(theBoard);
            theView.updatePawnsPosition(theBoard);
        }
    }
    class rollDice2Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int newDatePosition;
            if(!theBoard.getPlayer2().isHasFinished()) {
                if (theBoard.getWhosTurn() == 1) {
                    theBoard.rollTheDice2();
                    if(theBoard.getDice2() == 6){
                        theBoard.getPlayer2().addMoney(theBoard.getJackpot());
                        theBoard.emptyTheJackpot();
                        JOptionPane.showMessageDialog(null,"Dice rolled 6, You get the jackpot",
                                "Jackpot", JOptionPane.PLAIN_MESSAGE);
                    }
                    if (theBoard.getPlayer2().getPositionDate() == 31) {
                        newDatePosition = theBoard.getDice2();
                        theBoard.getPlayer2().movePosition(theBoard.getPositionAr(newDatePosition));
                    } else {
                        newDatePosition = theBoard.getDice2() + theBoard.getPlayer2().getPositionDate();

                        if (newDatePosition < 31) {
                            theBoard.getPlayer2().movePosition(theBoard.getPositionAr(newDatePosition));
                        } else {
                            theBoard.getPlayer2().movePosition(theBoard.getPositionAr(31));
                            theBoard.monthPassed();
                        }
                    }
                    theView.updatePlayersDice(theBoard);
                    theView.updatePawnsPosition(theBoard);
                    theView.updateInfoBox(theBoard);
                    theView.enableDice(false);
                    theBoard.tileAction();
                }
            }
            theView.updatePlayerPanel(theBoard);
        }
    }
    class endTurn2Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(theBoard.getWhosTurn() == 1){
                if(!(theBoard.getPlayer1().isHasFinished())) {
                    theBoard.changeTurn();
                }
                theView.enableDice(true);
                theView.updateInfoBox(theBoard);
            }
            theView.updatePawnsPosition(theBoard);
            theView.updatePlayerPanel(theBoard);
        }
    }
    class getLoan1Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(theBoard.getWhosTurn() == 0){
                theBoard.getPlayer1().setLoan(theBoard.getPlayer1().getLoan() + 1000);
                theBoard.getPlayer1().setMoney(theBoard.getPlayer1().getMoney() + 1000);
            }
            theView.updatePlayerPanel(theBoard);
        }
    }
    class getLoan2Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(theBoard.getWhosTurn() == 1){
                theBoard.getPlayer2().setLoan(theBoard.getPlayer2().getLoan() + 1000);
                theBoard.getPlayer2().setMoney(theBoard.getPlayer2().getMoney() + 1000);
            }
            theView.updatePlayerPanel(theBoard);
        }
    }
    class myDealCards1Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            theBoard.getPlayer1().showCards();
        }
    }
    class myDealCards2Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            theBoard.getPlayer2().showCards();
        }
    }


    public static void main(String[] args) {
        board board = new board();
        PayDayView view = new PayDayView(board);
        view.updateInfoBox(board);
        new PayDayController(view, board);
        view.setVisible(true);
    }

}
