package RequestAndResponse.Requests;

import Model.Board.Board;
import RequestAndResponse.Response.Response;
import RequestAndResponse.Response.ScoreBoardResponse;
import RequestAndResponse.Response.ScreenRecorderResponse;
import com.google.gson.Gson;
import server.Server;

import java.util.ArrayList;

public class ScreenRecorderRequest extends Request {





    public ScreenRecorderRequest(String applicator) {
        this.setRequestType("ScreenRecorderRequest");
        this.setApplicator(applicator);
    }


    @Override
    public void execute() {
        for (String clientHandlerName: Server.getClients().keySet()){
            if (clientHandlerName.equalsIgnoreCase(getApplicator())){
                ArrayList<Board> boards =Server.getClients().get(clientHandlerName).getLastGame().getBoards();
                Response response =new ScreenRecorderResponse(boards);
                String responseString = new Gson().toJson(response);
                Server.sendResponseToClient(this.getApplicator(), "ScreenRecorderResponse", responseString);



            }
        }



    }


}
