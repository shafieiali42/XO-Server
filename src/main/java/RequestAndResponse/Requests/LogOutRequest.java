package RequestAndResponse.Requests;


import RequestAndResponse.Response.LogOutResponse;
import RequestAndResponse.Response.Response;
import Server.ClientHandler;
import Server.Server;
import com.google.gson.Gson;

import java.io.IOException;

public class LogOutRequest extends Request {


    public LogOutRequest(String applicatorToken) {
        this.setRequestType("LogOutRequest");
        this.setApplicator(applicatorToken);
    }


    @Override
    public void execute() {
        for (String clientHandlerName : Server.getClients().keySet()) {
            if (this.getApplicator().equalsIgnoreCase(clientHandlerName)){
                try {
                    Server.logOut(Server.getClients().get(clientHandlerName));
                    Response response=new LogOutResponse(true);
                    String responseString=new Gson().toJson(response);
                    Server.sendResponseToClient(this.getApplicator(),"LogOutResponse",responseString);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (String clientHandlerName:Server.getClients().keySet()){
            if (clientHandlerName.equalsIgnoreCase(this.getApplicator())){
                Server.getClients().remove(clientHandlerName);
            }
        }

    }


}
