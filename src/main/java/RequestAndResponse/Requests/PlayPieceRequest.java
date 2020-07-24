package RequestAndResponse.Requests;

import Logic.Game;
import Model.Player.Player;
import RequestAndResponse.Response.PlayPieceResponse;
import RequestAndResponse.Response.Response;
import com.google.gson.Gson;
import server.Server;

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
                    boolean finished = Server.getClients().get(clientHandlerName).getGame().checkWins();

                    Game game = Server.getClients().get(clientHandlerName).getGame();

                    Response response = new PlayPieceResponse(game.getBoard(), game.getCurrentPlayer().getUserName(),
                            game.getCurrentAlliance().toString(),
                            game.getFormerPlayer().getUserName(), game.getFormerAlliance().toString(),
                            game.getCurrentAlliance().toString(), finished);
                    String responseString = new Gson().toJson(response);
                    Server.sendResponseToClient(this.getApplicator(),
                            "PlayPieceResponse", responseString);


//                    String token = null;
                    for (String nameOfClientHandler : Server.getClients().keySet()) {
                        if (Server.getClients().get(nameOfClientHandler).getPlayer().
                                getUserName().equalsIgnoreCase(game.getCurrentPlayer().getUserName())) {
                            Server.sendResponseToClient(nameOfClientHandler,
                                    "PlayPieceResponse", responseString);
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
