package RequestAndResponse.Response;


import Controller.Controller;

import Model.Player.Player;


import javax.swing.*;

public class LogInResponse extends Response {

    private Player player;


    public LogInResponse(Player player) {
        this.player = player;
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


}
