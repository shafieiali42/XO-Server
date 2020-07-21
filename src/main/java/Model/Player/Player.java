package Model.Player;

import Logic.Alliance;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Player {


    @Expose(serialize = true, deserialize = true)
    private String userName;
    @Expose(serialize = true, deserialize = true)
    private String password;
    @Expose(serialize = true, deserialize = true)
    private int wins;
    @Expose(serialize = true, deserialize = true)
    private int loose;
    @Expose(serialize = true, deserialize = true)
    private int score;
    @Expose(serialize = true, deserialize = true)
    private boolean onlineStatus;

    private transient Alliance alliance;
    private transient ArrayList<Integer> pieces;


    public Player(String userName,String password){
        this.userName=userName;
        this.password=password;
        this.wins=0;
        this.loose=0;
        this.score=0;
        this.onlineStatus=false;
        this.alliance=null;
        this.pieces=new ArrayList<>();
    }





    //getter and setters
    //*********************

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getWins() {
        return wins;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }
    public int getLoose() {
        return loose;
    }
    public void setLoose(int loose) {
        this.loose = loose;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public boolean isOnlineStatus() {
        return onlineStatus;
    }
    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    public Alliance getAlliance() {
        return alliance;
    }
    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }
    public ArrayList<Integer> getPieces() {
        return pieces;
    }
    public void setPieces(ArrayList<Integer> pieces) {
        this.pieces = pieces;
    }



}
