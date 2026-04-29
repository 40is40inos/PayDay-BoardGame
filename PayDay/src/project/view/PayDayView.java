package project.view;

import project.model.board;
import project.model.position;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class PayDayView extends JFrame implements ActionListener{

    ImageIcon windowIcon = new ImageIcon("resized images/logo.png"); //window icon


    //CALENDAR BOX
    JPanel calendarPanel = new JPanel();
    JPanel week1 = new JPanel();
    JPanel week2 = new JPanel();
    JPanel week3 = new JPanel();
    JPanel week4 = new JPanel();
    JPanel week5 = new JPanel();

    ImageIcon bluePawn = new ImageIcon("resized images/pawn_blue.png");
    ImageIcon yellowPawn = new ImageIcon("resized images/pawn_yellow.png");
    JLabel pawn1 = new JLabel(bluePawn);
    JLabel pawn2 = new JLabel(yellowPawn);
    Border calendarBorder = BorderFactory.createLineBorder(Color.black,2);

    /**
     * Initializes the tiles in parameter's order,
     * then returns them so they can be added to the calendarPanel
     *
     * @param positionAr
     * @return
     */
    public JLabel[] tilesLabelArrayInit(position[] positionAr){
        System.out.println("tilesLabelInit started");

        JLabel[] tilesLabelArray = new JLabel[32];

        ImageIcon imageIcon = null;

        for (int i = 0; i < 32; i++){

            /*serial numbers
                0- start
                1- mail 1 tile
                2- mail 2 tile
                3- deal tile
                4- laxeio tile
                5- lotaria
                6- radio
                7- buyer
                8- casino
                9- yard
                10-payday
            */
            switch(positionAr[i].getPositionsTile().getSerialTileNumber()) {
                case 0:     //start
                    imageIcon = new ImageIcon("resized images/start.png");
                    break;
                case 1:     //mail 1
                    imageIcon = new ImageIcon("resized images/mc1.png");
                    break;
                case 2:     //mail 2
                    imageIcon = new ImageIcon("resized images/mc2.png");
                    break;
                case 3:     //deal
                    imageIcon = new ImageIcon("resized images/deal.png");
                    break;
                case 4:     //laxeio
                    imageIcon = new ImageIcon("resized images/sweep.png");
                    break;
                case 5:     //lotaria
                    imageIcon = new ImageIcon("resized images/lottery.png");
                    break;
                case 6:     //radio
                    imageIcon = new ImageIcon("resized images/radio.png");
                    break;
                case 7:     //buyer
                    imageIcon = new ImageIcon("resized images/buyer.png");
                    break;
                case 8:     //casino
                    imageIcon = new ImageIcon("resized images/casino.png");
                    break;
                case 9:     //yard
                    imageIcon = new ImageIcon("resized images/yard.png");
                    break;
                case 10:    //payday
                    imageIcon = new ImageIcon("resized images/pay.png");
                    break;
                default:
                    System.out.println("Error: default switch at tilesLabelArrayInit");
            }

            tilesLabelArray[i] = new JLabel(imageIcon);

            if(i == 0) //coz the Start tile doesn't have a date number
                tilesLabelArray[i].setText(positionAr[i].getDayString());
            else
                tilesLabelArray[i].setText(positionAr[i].getDayString() + " " + i );

            tilesLabelArray[i].setHorizontalTextPosition(JLabel.CENTER);
            tilesLabelArray[i].setVerticalTextPosition(JLabel.TOP);
            tilesLabelArray[i].setBackground(Color.yellow);
            tilesLabelArray[i].setOpaque(true); //makes background visible
            tilesLabelArray[i].setBorder(calendarBorder);

            tilesLabelArray[i].setHorizontalAlignment(JLabel.CENTER);
            tilesLabelArray[i].setVerticalAlignment(JLabel.CENTER); //sets position of icon+text within Label
        }
        System.out.println("tilesLabelInit done");
        return tilesLabelArray;
    }


    //PLAYER A BOX
    JPanel player1panel = new JPanel();

    JLabel player1 = new JLabel("Player A");
    JButton rollDice1button = new JButton("A: Roll The Dice");
    JButton myDealCards1button = new JButton("A: My Deal Cards");
    JButton getLoan1button = new JButton("A: Get Loan");
    JButton endTurn1button = new JButton("A: End Turn");
    int money1 = 3500;
    JLabel moneyLabel1 = new JLabel("Money: " + money1 + "€");
    int loan1 = 0;
    JLabel loanLabel1 = new JLabel("Loan: " + loan1 + "€");
    int bills1 = 0;
    JLabel billsLabel1 = new JLabel("Bills: " + bills1 + "€");
    int dice1;
    JLabel dice1Label = new JLabel("Dice: " + dice1);


    //INFO BOX
    JPanel infoPanel = new JPanel();

    int infoMonths = 5;
    int infoCurrentMonth = -1;
    int infoTurn = 1;
    int jackpot = 0;
    JLabel infoBoxLabel = new JLabel("Info Box\n");
    JLabel infoMonthsLabel = new JLabel("Current Month " + infoCurrentMonth + "/" + infoMonths);
    JLabel infoTurnLabel = new JLabel("Turn: Player " + infoTurn);
    JLabel infoJackpotLabel = new JLabel("JackPot: " + jackpot);
    Border infoBorder = BorderFactory.createLineBorder(Color.cyan, 1);


    //PLAYER B BOX
    JPanel player2panel = new JPanel();


    JLabel player2 = new JLabel("Player B");
    JButton rollDice2button = new JButton("B: Roll The Dice");
    JButton myDealCards2button = new JButton("B: My Deal Cards");
    JButton getLoan2button = new JButton("B: Get Loan");
    JButton endTurn2button = new JButton("B: End Turn");
    int money2 = 3500;
    JLabel moneyLabel2 = new JLabel("Money: " + money2 + "€");
    int loan2 = 0;
    JLabel loanLabel2 = new JLabel("Loan: " + loan2 + "€");
    int bills2 = 0;
    JLabel billsLabel2 = new JLabel("Bills: " + bills2 + "€");
    int dice2;
    JLabel dice2Label = new JLabel("Dice: " + dice2);


    //UPDATERS
    public void updatePlayerPanel(board board){
        money1 = board.getPlayer1().getMoney();
        money2 = board.getPlayer2().getMoney();
        loan1 = board.getPlayer1().getLoan();
        loan2 = board.getPlayer2().getLoan();
        bills1 = board.getPlayer1().getBills();
        bills2 = board.getPlayer2().getBills();
        moneyLabel1.setText("Money: " + money1 + "€");
        loanLabel1.setText("Loan: " + loan1 + "€");
        billsLabel1.setText("Bills: " + bills1 + "€");
        moneyLabel2.setText("Money: " + money2 + "€");
        loanLabel2.setText("Loan: " + loan2 + "€");
        billsLabel2.setText("Bills: " + bills2 + "€");
    }
    public void updatePlayersDice(board board){
        dice1 = board.getDice1();
        dice2 = board.getDice2();
        dice1Label.setText("Dice: " + dice1);
        dice2Label.setText("Dice: " + dice2);
    }
    public void updatePawnsPosition(board board){
        int dateA = board.getPlayer1().getPositionDate();
        int dateB = board.getPlayer2().getPositionDate();

        if(dateA < 28){
            pawn1.setBounds((120 + (110 * (dateA % 7))), (30 + (160 * (dateA / 7))),100,100);
        }
        else{
            pawn1.setBounds((290 + (110 * (dateA % 7))), (30 + (160 * (dateA / 7))),100,100);
        }
        if(dateB < 28){
            pawn2.setBounds((130 + (110 * (dateB % 7))), (30 + (160 * (dateB / 7))),100,100);
        }
        else{
            pawn2.setBounds((300 + (110 * (dateB % 7))), (30 + (160 * (dateB / 7))),100,100);
        }
    }
    public void updateInfoBox(board board){
        infoMonths = board.getMonths();
        infoCurrentMonth = board.getCurrentMonth();
        infoMonthsLabel.setText("Current Month " + infoCurrentMonth + "/" + infoMonths);
        jackpot = board.getJackpot();
        infoJackpotLabel.setText("JackPot: " + jackpot);
        if(board.getWhosTurn() == 0)
            infoTurnLabel.setText("Turn: Player A");
        else
            infoTurnLabel.setText("Turn: Player B");
    }


    //VIEW CONSTRUCTOR
    public PayDayView(board board) {
        super("Payday");

        //CALENDAR PANEL INIT
        calendarPanel.setBackground(Color.yellow);
        calendarPanel.setBounds(0,0,1000,800);
        calendarPanel.setLayout(null);

        //adding tile tileLabels to week panels and then to calendar panel
        JLabel[] tileLabels = tilesLabelArrayInit(board.getPositionAr());
        for (int i = 0; i < 32 ;i++){
            if(i < 7)
                week1.add(tileLabels[i]);
            else if(i < 14)
                week2.add(tileLabels[i]);
            else if(i < 21)
                week3.add(tileLabels[i]);
            else if(i < 28)
                week4.add(tileLabels[i]);
            else
                week5.add(tileLabels[i]);
        }

        pawn1.setBounds(120,30, 100,100);
        pawn2.setBounds(130,30,100,100);
        calendarPanel.add(pawn1);
        calendarPanel.add(pawn2);

        week1.setBounds(0,0,1000, 160);
        week2.setBounds(0,160,1000,160);
        week3.setBounds(0,320,1000,160);
        week4.setBounds(0,480,1000,160);
        week5.setBounds(0,640,1000,160);
        calendarPanel.add(week1);
        calendarPanel.add(week2);
        calendarPanel.add(week3);
        calendarPanel.add(week4);
        calendarPanel.add(week5);

        //PLAYER A PANEL INIT
        player1panel.setBackground(Color.cyan);
        player1panel.setBounds(1200, 0,300,300);
        player1panel.add(player1);
        player1panel.add(rollDice1button);
        player1panel.add(myDealCards1button);
        player1panel.add(getLoan1button);
        player1panel.add(endTurn1button);
        player1panel.add(moneyLabel1);
        player1panel.add(loanLabel1);
        player1panel.add(billsLabel1);
        player1panel.add(dice1Label);


        //INFO PANEL INIT
        infoPanel.setBackground(Color.gray);
        infoPanel.setBounds(1200, 350, 300, 100);
        infoBoxLabel.setBorder(infoBorder);
        infoBoxLabel.setBounds(0,0,300,25);
        infoMonthsLabel.setBorder(infoBorder);
        infoMonthsLabel.setBounds(0,25,300,25);
        infoTurnLabel.setBorder(infoBorder);
        infoTurnLabel.setBounds(0,50,300,25);
        infoJackpotLabel.setBorder(infoBorder);
        infoJackpotLabel.setBounds(0,75,300,25);
        infoPanel.setLayout(null);
        infoPanel.add(infoBoxLabel);
        infoPanel.add(infoMonthsLabel);
        infoPanel.add(infoTurnLabel);
        infoPanel.add(infoJackpotLabel);

        //PLAYER B PANEL INIT
        player2panel.setBackground(Color.orange);
        player2panel.setBounds(1200, 500,300,300);
        player2panel.add(player2);
        player2panel.add(rollDice2button);
        player2panel.add(myDealCards2button);
        player2panel.add(getLoan2button);
        player2panel.add(endTurn2button);
        player2panel.add(moneyLabel2);
        player2panel.add(loanLabel2);
        player2panel.add(billsLabel2);
        player2panel.add(dice2Label);

        //FRAME INIT
        this.setIconImage(windowIcon.getImage());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1600,1000);
        this.setResizable(false);
        this.setLayout(null);

        this.add(calendarPanel);
        this.add(player1panel);
        this.add(player2panel);
        this.add(infoPanel);
        this.setVisible(true);
    }


    //BUTTONS' ACTIONS

    public void addRollDice1Listener(ActionListener listenerForRollDice1button){
        rollDice1button.addActionListener(listenerForRollDice1button);

    }
    public void addRollDice2Listener(ActionListener listenerForRollDice2button){
        rollDice2button.addActionListener(listenerForRollDice2button);
    }
    public void addMyDealCards1Listener(ActionListener ListenerForMyDealCards1button){
        myDealCards1button.addActionListener(ListenerForMyDealCards1button);
    }
    public void addMyDealCards2Listener(ActionListener ListenerForMyDealCards2button){
        myDealCards2button.addActionListener(ListenerForMyDealCards2button);
    }
    public void addGetLoan1Listener(ActionListener ListenerForGetLoan1button){
        getLoan1button.addActionListener(ListenerForGetLoan1button);
    }
    public void addGetLoan2Listener(ActionListener ListenerForGetLoan2button){
        getLoan2button.addActionListener(ListenerForGetLoan2button);
    }
    public void addEndTurn1Listener(ActionListener ListenerForEndTurn1button){
        endTurn1button.addActionListener(ListenerForEndTurn1button);
    }
    public void addEndTurn2Listener(ActionListener ListenerForEndTurn2button){
        endTurn2button.addActionListener(ListenerForEndTurn2button);
    }

    public void enableDice(boolean onOff){
        rollDice1button.setEnabled(onOff);
        rollDice2button.setEnabled(onOff);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
