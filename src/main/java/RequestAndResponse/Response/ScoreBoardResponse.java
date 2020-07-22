package RequestAndResponse.Response;


import Model.Player.Player;

import java.util.ArrayList;

public class ScoreBoardResponse extends Response {


    private ArrayList<String> userNames;
    private ArrayList<Boolean> onlineStatus;
    private ArrayList<Integer> points;


    public ScoreBoardResponse(ArrayList<Player> players) {
        initUserNameList(players);
        initOnlineStatusList(players);
        initPointsList(players);
    }

    private void initPointsList(ArrayList<Player> players) {
        this.points = new ArrayList<>();
        for (Player player : players) {
            this.points.add(player.getScore());
        }
    }

    private void initOnlineStatusList(ArrayList<Player> players) {
        this.onlineStatus = new ArrayList<>();
        for (Player player : players) {
            this.onlineStatus.add(player.isOnlineStatus());
        }
    }

    private void initUserNameList(ArrayList<Player> players) {
        this.userNames = new ArrayList<>();
        for (Player player : players) {
            this.userNames.add(player.getUserName());
        }
    }


    @Override
    public void execute() {



    }


    //getter and setters
    //*********************

    public ArrayList<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(ArrayList<String> userNames) {
        this.userNames = userNames;
    }

    public ArrayList<Boolean> getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(ArrayList<Boolean> onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }
}
