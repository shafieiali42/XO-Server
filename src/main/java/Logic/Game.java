package Logic;

import Model.Board.Board;
import Model.Player.Player;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private Player XPlayer;
    private Player OPlayer;
    private Player currentPlayer;
    private Player formerPlayer;
    private Alliance currentAlliance;
    private Alliance formerAlliance;
    private Board board;
    private boolean finished;
    private ArrayList<Board> boards;


    public Game(Player XPlayer, Player OPlayer) {
        this.XPlayer = XPlayer;
        this.OPlayer = OPlayer;
        currentPlayer = null;
        formerPlayer = null;
        this.board = new Board();
        this.finished=false;
        currentAlliance = Alliance.X;
        formerAlliance = Alliance.O;
        currentPlayer = XPlayer;
        formerPlayer = OPlayer;
        boards=new ArrayList<>();
        boards.add(new Board());
//        defineStarter();
    }


    private void defineStarter() {
        Random random = new Random();
        int randomIndex = random.nextInt(2);
        if (randomIndex % 2 == 0) {
            currentAlliance = Alliance.X;
            formerAlliance = Alliance.O;
            currentPlayer = XPlayer;
            formerPlayer = OPlayer;
        } else {
            currentAlliance = Alliance.O;
            formerAlliance = Alliance.X;
            currentPlayer = OPlayer;
            formerPlayer = XPlayer;
        }
    }


    public void changeTurn() {
        if (currentAlliance.equals(Alliance.O)) {
            currentAlliance = Alliance.X;
            formerAlliance = Alliance.O;
            currentPlayer = XPlayer;
            formerPlayer = OPlayer;
        } else {
            currentAlliance = Alliance.O;
            formerAlliance = Alliance.X;
            currentPlayer = OPlayer;
            formerPlayer = XPlayer;
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getFormerPlayer() {
        return formerPlayer;
    }

    public void setFormerPlayer(Player formerPlayer) {
        this.formerPlayer = formerPlayer;
    }

    public Alliance getFormerAlliance() {
        return formerAlliance;
    }

    public void setFormerAlliance(Alliance formerAlliance) {
        this.formerAlliance = formerAlliance;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }

}
