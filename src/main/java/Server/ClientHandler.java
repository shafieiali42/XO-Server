package Server;

import Model.Player.Player;
import RequestAndResponse.Requests.Request;
import RequestAndResponse.Requests.JsonDeSerializerForRequest;
import RequestAndResponse.Response.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ClientHandler extends Thread {

    private Socket socket;
    private Server server;
    private String clientName;
    PrintWriter printer;
    private ArrayList<Request> requests;
    private Player player;
    private String authtoken;
    private boolean loggedIn;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.loggedIn=false;
        this.requests = new ArrayList<>();
        this.player=new Player();
    }



    public void executeRequests() {
        Iterator<Request> itr = requests.iterator();
        while (itr.hasNext()) {
            Request request = itr.next();
            request.execute();
            itr.remove();
        }
    }


    @Override
    public void run() {
        int counter = 0;
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            printer = new PrintWriter(socket.getOutputStream());

            String authtoken = "";
            String requestName = "";
            String message = "";
            while (true) {
                while (scanner.hasNextLine()) {
                    String text = scanner.nextLine();
                    switch (counter % 3) {
                        case 0:
                            authtoken = text;
                            break;
                        case 1:
                            requestName = text;
                            break;
                        case 2:
                            message = text;
                            Request request = JsonDeSerializerForRequest.deSerializeRequest(authtoken, requestName, message);
                            if (authtoken.equals("null")) {
                                if (request.getRequestType().equalsIgnoreCase("LogInRequest")) {
//                                    synchronized (Server.getClients()) {
                                        for (ClientHandler clientHandler : Server.getClients().values()) {
                                            if (this.socket.getRemoteSocketAddress().toString().
                                                    equalsIgnoreCase(clientHandler.socket.getRemoteSocketAddress().toString())) {
                                                Server.getClients().remove(clientHandler.socket.getRemoteSocketAddress().toString());
                                                authtoken = GenerateAuthtoken.generateNewToken();
                                                request.setApplicator(authtoken);
                                                this.authtoken = authtoken;
                                                Server.getClients().put(authtoken, this);
                                                System.out.println(Server.getClients());
                                                break;
                                            }
//                                        }
                                    }
                                    this.requests.add(request);
                                    executeRequests();
                                }
                            } else {

                                if (authtoken.equalsIgnoreCase(this.authtoken)) {
                                    this.requests.add(request);
                                    executeRequests();
                                }
                            }
                            break;
                    }
                    counter++;


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendToThisClient(String clientName, String responseName, String message) { //require to send response to clients
        printer.println(clientName);
        printer.println(responseName);
        printer.println(message);
        printer.flush();
    }


    //getter and setters
    //*********************

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}

