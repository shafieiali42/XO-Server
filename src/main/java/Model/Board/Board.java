package Model.Board;

import Logic.TileStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private ArrayList<TileStatus> board;


    public Board() {

        this.board = new ArrayList<>();
        for (int i = 0; i <49 ; i++) {
            board.add(TileStatus.EMPTY);
        }
    }


    //getter and setters
    //********************

    public ArrayList<TileStatus> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<TileStatus> board) {
        this.board = board;
    }


}
