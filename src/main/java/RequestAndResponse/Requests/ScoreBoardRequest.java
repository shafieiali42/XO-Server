package RequestAndResponse.Requests;

import Model.Player.Player;
import RequestAndResponse.Response.Response;
import RequestAndResponse.Response.ScoreBoardResponse;
import server.ClientHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.Server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ScoreBoardRequest extends Request {


    public ScoreBoardRequest(String applicator) {
        this.setRequestType("ScoreBoardRequest");
        this.setApplicator(applicator);
    }


    @Override
    public void execute() {
        Response response = new ScoreBoardResponse(getAllPlayer());
        String responseString = new Gson().toJson(response);
        Server.sendResponseToClient(this.getApplicator(), "ScoreBoardResponse", responseString);
    }


    public static ArrayList<Player> setOnlineStatus() {
        ArrayList<Player> playerList = new ArrayList<>();
        for (ClientHandler clientHandler : Server.getClients().values()) {
            if (clientHandler.isLoggedIn()){
                clientHandler.getPlayer().setOnlineStatus(true);
            }
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

        Iterator<Player> itr = playerList.iterator();

        while (itr.hasNext()) {
            Player player = itr.next();
            for (Player playerInClientHandler : setOnlineStatus()) {
                if (player.equals(playerInClientHandler)) {
                    player.setOnlineStatus(playerInClientHandler.isOnlineStatus());
                }
            }
        }

        for (Player player : setOnlineStatus()) {
            if (player.getUserName() == null) {
                playerList.add(player);
            } else {
                if (!playerList.contains(player)) {
                    playerList.add(player);
                }
            }
        }

        Collections.sort(playerList);

        for (Player player:playerList){
            player.setScore();
        }


        return playerList;

    }


}
