package RequestAndResponse.Requests;

import Logic.Game;
import Model.Player.Player;
import RequestAndResponse.Response.PlayPieceResponse;
import RequestAndResponse.Response.Response;
import Util.Json.ParsePlayerObjectIntoJson;
import com.google.gson.Gson;
import server.*;

import java.io.IOException;
import java.util.ArrayList;

public class PlayPieceRequest extends Request {


    private int targetTileId;


    public PlayPieceRequest(String applicator, int targetTileId) {
        this.setRequestType("ScoreBoardRequest");
        this.setApplicator(applicator);
        this.targetTileId = targetTileId;
    }


    @Override
    public void execute() {
        Player player = null;

        for (String clientHandlerName : Server.getClients().keySet()) {
            if (this.getApplicator().equalsIgnoreCase(clientHandlerName)) {
                player = Server.getClients().get(clientHandlerName).getPlayer();
                if (player.getUserName().equalsIgnoreCase(Server.getClients().
                        get(clientHandlerName).getGame().getCurrentPlayer().getUserName())) {

                    Server.getClients().get(clientHandlerName).playPiece(targetTileId);
                    Game game = Server.getClients().get(clientHandlerName).getGame();
                    String resultList = Server.checkWins(game.getBoard(), targetTileId);


                    Response response = new PlayPieceResponse(game.getBoard(),
                            game.getCurrentPlayer().getUserName(),
                            game.getCurrentAlliance().toString(),
                            game.getFormerPlayer().getUserName(), game.getFormerAlliance().toString(),
                            game.getCurrentAlliance().toString(), resultList);


                    String responseString = new Gson().toJson(response);
                    Server.sendResponseToClient(this.getApplicator(),
                            "PlayPieceResponse", responseString);


//                    String token = null;
                    for (String nameOfClientHandler : Server.getClients().keySet()) {//todo
                        if (Server.getClients().get(nameOfClientHandler).getPlayer() != null) {
                            if (Server.getClients().get(nameOfClientHandler).getPlayer().
                                    getUserName().equalsIgnoreCase(game.getXPlayer().getUserName())) {
                                Server.sendResponseToClient(nameOfClientHandler,
                                        "PlayPieceResponse", responseString);
                            } else if (Server.getClients().get(nameOfClientHandler).getPlayer().
                                    getUserName().equalsIgnoreCase(game.getOPlayer().getUserName())) {
                                Server.sendResponseToClient(nameOfClientHandler,
                                        "PlayPieceResponse", responseString);
                            }
                        }
                    }


                    ClientHandler clientHandler1 = null;
                    ClientHandler clientHandler2 = null;
                    for (ClientHandler clientHandler : Server.getClients().values()) {
                        if (clientHandler.getPlayer().getUserName().
                                equalsIgnoreCase(game.getXPlayer().getUserName())) {
                            clientHandler1 = clientHandler;
                        } else if (clientHandler.getPlayer().getUserName().
                                equalsIgnoreCase(game.getOPlayer().getUserName())) {
                            clientHandler2 = clientHandler;
                        }
                    }

                    if (resultList.equalsIgnoreCase("X")) {
                        game.getXPlayer().setWins(game.getXPlayer().getWins() + 1);
                        game.getOPlayer().setLoose(game.getOPlayer().getLoose() + 1);
//                            System.out.println("X Player: " + game.getXPlayer());
//                            System.out.println("O Player: " + game.getOPlayer());
                        game.getXPlayer().setScore();
                        game.getOPlayer().setScore();
                        game.setFinished(true);
                        Server.getRunningGames().remove(game);
                        clientHandler1.setLastGame(clientHandler1.getGame());
                        clientHandler2.setLastGame(clientHandler2.getGame());
                        clientHandler1.setGame(null);
                        clientHandler2.setGame(null);
                        try {
                            ParsePlayerObjectIntoJson.serializePlayer(game.getXPlayer());
                            ParsePlayerObjectIntoJson.serializePlayer(game.getOPlayer());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (resultList.equalsIgnoreCase("O")) {
                        game.getOPlayer().setWins(game.getXPlayer().getWins() + 1);
                        game.getXPlayer().setLoose(game.getXPlayer().getLoose() + 1);
                        game.getXPlayer().setScore();
                        game.getOPlayer().setScore();
                        game.setFinished(true);
                        Server.getRunningGames().remove(game);
                        clientHandler1.setLastGame(clientHandler1.getGame());
                        clientHandler2.setLastGame(clientHandler2.getGame());
                        clientHandler1.setGame(null);
                        clientHandler2.setGame(null);
                        try {
                            ParsePlayerObjectIntoJson.serializePlayer(game.getXPlayer());
                            ParsePlayerObjectIntoJson.serializePlayer(game.getOPlayer());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (resultList.equalsIgnoreCase("Draw")) {
                        game.getXPlayer().setScore();
                        game.getOPlayer().setScore();
                        game.setFinished(true);
                        Server.getRunningGames().remove(game);
                        clientHandler1.setLastGame(clientHandler1.getGame());
                        clientHandler2.setLastGame(clientHandler2.getGame());
                        clientHandler1.setGame(null);
                        clientHandler2.setGame(null);

                        try {
                            ParsePlayerObjectIntoJson.serializePlayer(game.getXPlayer());
                            ParsePlayerObjectIntoJson.serializePlayer(game.getOPlayer());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }
        }


    }


    //getter and setters
    //********************

    public int getTargetTileId() {
        return targetTileId;
    }

    public void setTargetTileId(int targetTileId) {
        this.targetTileId = targetTileId;
    }


}
