package RequestAndResponse.Response;

import Model.Board.Board;

import java.util.ArrayList;

public class PlayPieceResponse extends Response {



    private Board board;
    private String turn;
    private String friendlyName;
    private String friendlyIcon;
    private String enemyName;
    private String enemyIcon;
    private String resultList;



    public PlayPieceResponse(Board board, String friendlyName,
                             String friendlyIcon, String enemyName, String enemyIcon,
                             String turn, String resultList) {



        this.board=board;
        this.friendlyName=friendlyName;
        this.friendlyIcon=friendlyIcon;
        this.enemyName=enemyName;
        this.enemyIcon=enemyIcon;
        this.turn=turn;
        this.resultList = resultList;

    }


    @Override
    public void execute() {

    }





    //getter and setters
    //********************

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyIcon() {
        return friendlyIcon;
    }

    public void setFriendlyIcon(String friendlyIcon) {
        this.friendlyIcon = friendlyIcon;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public String getEnemyIcon() {
        return enemyIcon;
    }

    public void setEnemyIcon(String enemyIcon) {
        this.enemyIcon = enemyIcon;
    }

    public String ResultList() {
        return resultList;
    }

    public void setResultList(String resultList) {
        this.resultList = resultList;
    }

}
