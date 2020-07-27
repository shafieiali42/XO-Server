package RequestAndResponse.Response;


import Controller.Controller;

import Model.Player.Player;


import javax.swing.*;

public class LogInResponse extends Response {

    private Player player;
    private boolean logInBefore;


    public LogInResponse(Player player,boolean logInBefore) {
        this.player = player;
        this.logInBefore=logInBefore;
    }


    @Override
    public void execute() {
        if (player != null) {
//            Controller.getCurrentClient().setPlayer(player);
//            ClientMain.getMyMainFrame().setContentPane(new MainMenuPage());
        } else {

        }
    }


    //getter and setters
    //*********************

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isLogInBefore() {
        return logInBefore;
    }

    public void setLogInBefore(boolean logInBefore) {
        this.logInBefore = logInBefore;
    }

}
