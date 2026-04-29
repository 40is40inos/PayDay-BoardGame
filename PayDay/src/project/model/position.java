package project.model;

/**
 *Class that represents a position on the board
 */
public class position {

    day day;
    public project.model.day getDay() {
        return day;
    }
    public String getDayString(){
        return day.name();
    }
    public void setDay(project.model.day day) {
        this.day = day;
    }


    int date;
    public int getDate() {
        return date;
    }
    public void setDate(int date) {
        this.date = date;
        switch (date % 7){
            case 0:
                if(date != 0) setDay(project.model.day.Sunday);
                else setDay(project.model.day.Start);
                break;
            case 1:
                setDay(project.model.day.Monday);
                break;
            case 2:
                setDay(project.model.day.Tuesday);
                break;
            case 3:
                setDay(project.model.day.Wednesday);
                break;
            case 4:
                setDay(project.model.day.Thursday);
                break;
            case 5:
                setDay(project.model.day.Friday);
                break;
            case 6:
                setDay(project.model.day.Saturday);
                break;
        }
    }


    tile positionsTile;
    public tile getPositionsTile() {
        return positionsTile;
    }
    public void setPositionsTile(tile positionTile) {
        this.positionsTile = positionTile;
    }


    public position(int date, tile positionTile) {
        setDate(date);
        setPositionsTile(positionTile);
    }
}

/**
 * Position on the board that forces the use of a card
 */
class cardPosition extends position{

    public cardPosition(int date, tile positionTile) {
        super(date, positionTile);
    }
}
/**
 * Position on the board that forces the use of dice
 */
class dicePosition extends position{

    public dicePosition(int date, tile positionTile) {
        super(date, positionTile);
    }
}
/**
 * Position on the board that forces the use of dice
 * involving one player
 */
class onePlayerDicePosition extends dicePosition{

    public onePlayerDicePosition(int date, tile positionTile) {
        super(date, positionTile);
    }
}
/**
 * Position on the board that forces the use of dice
 * involving both players
 */
class twoPlayerDicePosition extends dicePosition{

    public twoPlayerDicePosition(int date, tile positionTile) {
        super(date, positionTile);
    }
}
/**
 * Position on the board that gives the option of selling a dealCard
 */
class buyerPosition extends position{

    public buyerPosition(int date, tile positionTile) {
        super(date, positionTile);
    }
}