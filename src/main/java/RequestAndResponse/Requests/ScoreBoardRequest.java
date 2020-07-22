package RequestAndResponse.Requests;

import Model.Player.Player;
import RequestAndResponse.Response.Response;
import RequestAndResponse.Response.ScoreBoardResponse;
import Server.ClientHandler;
import Server.Server;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoardRequest extends Request {


    public ScoreBoardRequest(String applicator) {
        this.setRequestType("ScoreBoardRequest");
        this.setApplicator(applicator);
    }


    @Override
    public void execute() {
        Response response=new ScoreBoardResponse(getAllPlayer());
        String  responseString=new Gson().toJson(response);
        Server.sendResponseToClient(this.getApplicator(),"ScoreBoardRequest",responseString);
    }


    public static ArrayList<Player> setOnlineStatus() {
        ArrayList<Player> playerList = new ArrayList<>();
        for (ClientHandler clientHandler : Server.getClients().values()) {
            clientHandler.getPlayer().setOnlineStatus(true);
            playerList.add(clientHandler.getPlayer());
        }
        return playerList;
    }

    public static ArrayList<Player> getAllPlayer() {
        ArrayList<Player> playerList = new ArrayList<>();
        try {
            Type type = new TypeToken<List<Player>>() {
            }.getType();
            playerList = new Gson().fromJson(new FileReader("Players/" + "AllPlayers.json"), type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Player player : playerList) {
            for (Player playerInClientHandler : setOnlineStatus()) {
                if (player.equals(playerInClientHandler)){
                    playerList.remove(player);
                    playerList.add(playerInClientHandler);
                }
            }
        }
        Collections.sort(playerList);
        return playerList;

    }


}
