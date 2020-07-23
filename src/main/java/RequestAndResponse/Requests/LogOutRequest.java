package RequestAndResponse.Requests;


import Model.Player.Player;
import RequestAndResponse.Response.LogOutResponse;
import RequestAndResponse.Response.Response;
import Server.ClientHandler;
import Server.Server;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class LogOutRequest extends Request {


    public LogOutRequest(String applicatorToken) {
        this.setRequestType("LogOutRequest");
        this.setApplicator(applicatorToken);
    }


    @Override
    public void execute() {

        for (String clientHandlerName : Server.getClients().keySet()) {
            if (this.getApplicator().equalsIgnoreCase(clientHandlerName)) {
                try {
                    Server.logOut(Server.getClients().get(clientHandlerName));
                    Response response = new LogOutResponse(true);
                    String responseString = new Gson().toJson(response);
                    for (String clientHandlerNamew : Server.getClients().keySet()) {
                        if (clientHandlerNamew != null) {
                            if (Server.getClients().get(clientHandlerNamew).getPlayer().getUserName() != null) {
                                System.out.println(Server.getClients().get(clientHandlerNamew).getPlayer().getUserName());
                                Request request = new ScoreBoardRequest(clientHandlerNamew);
                                request.execute();

                            }
                        }
                    }



                    Server.sendResponseToClient(this.getApplicator(), "LogOutResponse", responseString);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        Server.getClients().entrySet().removeIf(entry -> entry.getKey().equalsIgnoreCase(this.getApplicator()));



    }


}
