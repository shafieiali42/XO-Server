package RequestAndResponse.Requests;


import Model.Player.Player;
import RequestAndResponse.Response.LogInResponse;
import RequestAndResponse.Response.Response;
import Server.Server;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class LogInRequest implements Request {

    private String userName;
    private String password;
    private String mode; //SignUp or LogIn


    public LogInRequest(String userName, String password, String mode) {
        this.userName = userName;
        this.password = password;
        this.mode = mode;
    }


    @Override
    public void execute() {
        if (this.mode.equalsIgnoreCase("SignUp")) {
            try {
                Player player = Server.signUp(userName, password);
                Response response = new LogInResponse(player);
                String responseString=new Gson().toJson(response);
                Server.sendResponseToClient("","LogInResponse",responseString);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (this.mode.equalsIgnoreCase("LogIn")) {
            try {
                Player player = Server.signIn(userName, password);
                Response response = new LogInResponse(player);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public String getRequestType() {
        return "LogInRequest";
    }

}
