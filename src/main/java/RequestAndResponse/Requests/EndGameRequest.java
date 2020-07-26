package RequestAndResponse.Requests;

import Logic.Game;
import Util.Json.ParsePlayerObjectIntoJson;
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
                Server.getClients().get(clientHandlerName).getPlayer().
                        setLoose(Server.getClients().get(clientHandlerName).
                                getPlayer().getLoose() + 1);


                Server.getClients().get(clientHandlerName).getPlayer().setScore();

                try {
                    ParsePlayerObjectIntoJson.serializePlayer(Server.getClients().get(clientHandlerName).getPlayer());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Server.getClients().get(clientHandlerName).getGame() != null) {
                    if (Server.getClients().get(clientHandlerName).getGame().getXPlayer().
                            getUserName().equalsIgnoreCase(Server.getClients().
                            get(clientHandlerName).getPlayer().getUserName())) {
                        Server.getClients().get(clientHandlerName).getGame().getXPlayer().
                                setWins(Server.getClients().get(clientHandlerName).
                                        getGame().getXPlayer().getWins() + 1);

                        System.out.println(Server.getClients().get(clientHandlerName).getGame().getXPlayer());
                        Server.getClients().get(clientHandlerName).getGame().getXPlayer().
                                setScore();

                        try {
                            ParsePlayerObjectIntoJson.serializePlayer(Server.getClients().get(clientHandlerName).getGame().getXPlayer());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        opponent = Server.getClients().get(clientHandlerName).getGame().getXPlayer().getUserName();

                    } else if (Server.getClients().get(clientHandlerName).getGame().getOPlayer().
                            getUserName().equalsIgnoreCase(Server.getClients().
                            get(clientHandlerName).getPlayer().getUserName())) {
                        Server.getClients().get(clientHandlerName).getGame().getOPlayer().
                                setWins(Server.getClients().get(clientHandlerName).
                                        getGame().getOPlayer().getWins() + 1);
                        Server.getClients().get(clientHandlerName).getGame().getOPlayer().
                                setScore();


                        try {
                            ParsePlayerObjectIntoJson.serializePlayer( Server.getClients().get(clientHandlerName).getGame().getOPlayer());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        opponent = Server.getClients().get(clientHandlerName).getGame().getOPlayer().getUserName();


                    }
                    Server.getClients().get(clientHandlerName).getGame().setFinished(true);
                    Server.getRunningGames().remove(Server.getClients().
                            get(clientHandlerName).getGame());

                    for (ClientHandler clientHandler : Server.getClients().values()) {
                        if (clientHandler.getPlayer().getUserName().equalsIgnoreCase(opponent)) {
                            clientHandler.setGame(null);
                        }
                    }


                    Server.getClients().get(clientHandlerName).setGame(null);
                }


            }
        }


    }


}
