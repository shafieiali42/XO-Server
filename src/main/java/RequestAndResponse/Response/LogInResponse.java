package RequestAndResponse.Response;


import Model.Player.Player;
import com.google.gson.Gson;

import javax.swing.*;

public class LogInResponse implements Response {


    private Player player;


    public LogInResponse(Player player) {
        this.player = player;
    }


    @Override
    public void execute() {
        if (player != null) {

        } else {
            JOptionPane.showMessageDialog(null, "LogInError", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
