package RequestAndResponse.Requests;


import Model.Player.Player;
import RequestAndResponse.Response.LogInResponse;
import RequestAndResponse.Response.Response;

import server.*;
import com.google.gson.Gson;

import java.io.IOException;

public class LogInRequest extends Request {

    private String userName;
    private String password;
    private String mode; //SignUp or LogIn


    public LogInRequest(String applicator, String userName, String password, String mode) {
        this.setRequestType("LogInRequest");
        this.setApplicator(applicator);
        this.userName = userName;
        this.password = password;
        this.mode = mode;

    }


    @Override
    public void execute() {
        if (this.mode.equalsIgnoreCase("SignUp")) {
            try {
                Player player = Server.signUp(Server.getClientHandler(this.getApplicator()), userName, password);
                Response response = new LogInResponse(player);
                String responseString = new Gson().toJson(response);

                for (String clientHandlerName : Server.getClients().keySet()) {
                    if (clientHandlerName != null) {
                        if (Server.getClients().get(clientHandlerName).getPlayer().getUserName() != null) {
                            Request request = new ScoreBoardRequest(clientHandlerName);
                            request.execute();

                        }
                    }
                }


                for (ClientHandler clientHandler : Server.getClients().values()) {
                    if (clientHandler.getPlayer().getUserName() != null && player != null) {
                        if (clientHandler.getPlayer().equals(player)) {
                                clientHandler.setLoggedIn(true);
                            }

                    }
                }

                Server.sendResponseToClient(this.getApplicator(), "LogInResponse", responseString);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (this.mode.equalsIgnoreCase("LogIn")) {
            try {
                Player player = Server.signIn(Server.getClientHandler(this.getApplicator()), userName, password);
                Response response = new LogInResponse(player);
                String responseString = new Gson().toJson(response);
                for (String clientHandlerName : Server.getClients().keySet()) {
                    if (clientHandlerName != null) {
                        if (Server.getClients().get(clientHandlerName).getPlayer().getUserName() != null) {
                            Request request = new ScoreBoardRequest(clientHandlerName);
                            request.execute();

                        }
                    }
                }


                for (ClientHandler clientHandler : Server.getClients().values()) {
                    if (clientHandler.getPlayer().getUserName() != null && player != null) {
                        if (clientHandler.getPlayer().equals(player)) {
                                clientHandler.setLoggedIn(true);

                        }
                    }
                }

                Server.sendResponseToClient(this.getApplicator(), "LogInResponse", responseString);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}
