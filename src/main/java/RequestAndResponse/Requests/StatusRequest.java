package RequestAndResponse.Requests;


import Model.Player.Player;
import RequestAndResponse.Response.Response;
import RequestAndResponse.Response.StatusResponse;
import com.google.gson.Gson;

import server.Server;



public class StatusRequest extends Request {




    public StatusRequest(String applicator) {
        this.setRequestType("ScoreBoardRequest");
        this.setApplicator(applicator);
    }


    @Override
    public void execute() {
        Player player=null;
        for (String clientHandlerName:Server.getClients().keySet()){
            if (clientHandlerName.equalsIgnoreCase(getApplicator())){
                player=Server.getClients().get(clientHandlerName).getPlayer();
            }
        }

        player.setScore();
        System.out.println("send status response");
        Response response = new StatusResponse(player.getUserName(),player.getWins(),player.getLoose(),player.getScore());
        String responseString = new Gson().toJson(response);
        Server.sendResponseToClient(this.getApplicator(), "StatusResponse", responseString);
    }








}
