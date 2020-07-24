package RequestAndResponse.Requests;


import Logic.Game;
import RequestAndResponse.Response.Response;
import RequestAndResponse.Response.ScoreBoardResponse;
import RequestAndResponse.Response.StartGameResponse;
import com.google.gson.Gson;
import server.ClientHandler;
import server.Server;


public class PlayRequest extends Request {


    public PlayRequest(String applicator) {
        this.setRequestType("PlayRequest");
        this.setApplicator(applicator);
    }


    @Override
    public void execute() {
        System.out.println("execute playRequest");
        for (String clientHandlerName : Server.getClients().keySet()) {
            if (clientHandlerName.equals(this.getApplicator())) {
                Server.getPlayQueue().add(Server.getClients().get(clientHandlerName));
                System.out.println("add this player into play queue: " +
                        Server.getClients().get(clientHandlerName).getPlayer().getUserName());

                while (Server.getPlayQueue().size() >= 2) {
                    ClientHandler clientHandler1 = Server.getPlayQueue().get(0);
                    ClientHandler clientHandler2 = Server.getPlayQueue().get(1);
                    Game game = new Game(clientHandler1.getPlayer(), clientHandler2.getPlayer());
                    clientHandler1.setGame(game);
                    clientHandler2.setGame(game);
                    Server.getRunningGames().add(game);
                    Server.getPlayQueue().remove(clientHandler1);
                    Server.getPlayQueue().remove(clientHandler2);
                    Response response = new StartGameResponse(game.getBoard(),game.getXPlayer().getUserName(),
                            "X",game.getOPlayer().getUserName(),"O",
                            game.getCurrentAlliance().toString());

                    String responseString = new Gson().toJson(response);

                    String token1 = null;
                    String token2=null;

                    for (String clientHandleNme:Server.getClients().keySet()){
                        if (Server.getClients().get(clientHandleNme).equals(clientHandler1)){
                            token1=clientHandleNme;
                        }else if (Server.getClients().get(clientHandleNme).equals(clientHandler2)){
                            token2=clientHandleNme;
                        }
                    }

                    Server.sendResponseToClient(token1,"StartGameResponse", responseString);
                    Server.sendResponseToClient(token2,"StartGameResponse", responseString);


                }
            }
        }

    }


}
