package Logic;

import Model.Player.Player;

import java.util.Random;

public class Game {

    private Player XPlayer;
    private Player OPlayer;
    private Player currentPlayer;
    private Alliance currentAlliance;


    public Game(Player XPlayer, Player OPlayer) {
        this.XPlayer = XPlayer;
        this.OPlayer = OPlayer;
        currentPlayer = null;
        defineStarter();
    }



    private void defineStarter() {
        Random random = new Random();
        int randomIndex = random.nextInt(2);
        if (randomIndex % 2 == 0) {
            currentAlliance = Alliance.X;
            currentPlayer = XPlayer;
        } else {
            currentAlliance = Alliance.O;
            currentPlayer = OPlayer;
        }
    }


    //getter and setters
    //********************

    public Player getXPlayer() {
        return XPlayer;
    }

    public void setXPlayer(Player XPlayer) {
        this.XPlayer = XPlayer;
    }

    public Player getOPlayer() {
        return OPlayer;
    }

    public void setOPlayer(Player OPlayer) {
        this.OPlayer = OPlayer;
    }

    public Alliance getCurrentAlliance() {
        return currentAlliance;
    }

    public void setCurrentAlliance(Alliance currentAlliance) {
        this.currentAlliance = currentAlliance;
    }


}
