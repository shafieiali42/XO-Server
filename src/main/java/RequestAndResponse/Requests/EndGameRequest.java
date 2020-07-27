package RequestAndResponse.Requests;

import Logic.Game;
import Model.Player.Player;
import RequestAndResponse.Response.EndGameResponse;
import RequestAndResponse.Response.Response;
import Util.Json.ParsePlayerObjectIntoJson;
import com.google.gson.Gson;
import server.ClientHandler;
import server.Server;

import java.io.IOException;

public class EndGameRequest extends Request {


    public EndGameRequest(String applicator) {
        this.setRequestType("EndGameRequest");
        this.setApplicator(applicator);
    }


    @Override
    public void execute() {
        String opponent = null;

        for (String clientHandlerName : Server.getClients().keySet()) {
            if (clientHandlerName.equalsIgnoreCase(this.getApplicator())) {




                try {
                    ParsePlayerObjectIntoJson.serializePlayer(Server.getClients().get(clientHandlerName).getPlayer());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Server.getClients().get(clientHandlerName).getGame() != null) {
                    if (Server.getClients().get(clientHandlerName).getGame().getXPlayer().
                            getUserName().equalsIgnoreCase(Server.getClients().
                            get(clientHandlerName).getPlayer().getUserName())) {


                        try {
                            ParsePlayerObjectIntoJson.serializePlayer(Server.getClients().get(clientHandlerName).getGame().getXPlayer());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        opponent = Server.getClients().get(clientHandlerName).getGame().getOPlayer().getUserName();

                    } else if (Server.getClients().get(clientHandlerName).getGame().getOPlayer().
                            getUserName().equalsIgnoreCase(Server.getClients().
                            get(clientHandlerName).getPlayer().getUserName())) {


                        try {
                            ParsePlayerObjectIntoJson.serializePlayer(Server.getClients().get(clientHandlerName).getGame().getOPlayer());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        opponent = Server.getClients().get(clientHandlerName).getGame().getXPlayer().getUserName();


                    }
                    Server.getClients().get(clientHandlerName).getGame().setFinished(true);
                    Server.getRunningGames().remove(Server.getClients().
                            get(clientHandlerName).getGame());
//

                    String opponentHandler=null;
                    for (String name:Server.getClients().keySet()){
                        if (Server.getClients().get(name).getPlayer().getUserName().equals(opponent)){
                            opponentHandler=name;
                        }
                    }
//                    for (ClientHandler clientHandler : Server.getClients().values()) {
//                        if (clientHandler.getPlayer().getUserName().equalsIgnoreCase(opponent)) {
//                            clientHandler.setLastGame(clientHandler.getGame());
//                            clientHandler.setGame(null);
//                        }
//                    }



                    Server.getClients().get(clientHandlerName).setLastGame(Server.getClients().get(clientHandlerName).getGame());
                    Response response = new EndGameResponse();
                    String responseString = new Gson().toJson(response);
                    Server.sendResponseToClient(opponentHandler, "EndGameResponse", responseString);
                    Server.getClients().get(clientHandlerName).setGame(null);
                    Server.getClients().get(opponentHandler).setGame(null);
                }


            }
        }


    }


}
