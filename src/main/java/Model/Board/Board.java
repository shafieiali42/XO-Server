package Model.Board;

import Logic.TileStatus;
import java.util.HashMap;

public class Board {

    private HashMap<Integer, TileStatus> board;


    public Board() {
        this.board = new HashMap<>();
    }


    //getter and setters
    //********************

    public HashMap<Integer, TileStatus> getBoard() {
        return board;
    }

    public void setBoard(HashMap<Integer, TileStatus> board) {
        this.board = board;
    }


}
