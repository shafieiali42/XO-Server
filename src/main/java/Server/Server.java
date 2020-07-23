package Server;


import Model.Player.Player;
import Util.Json.ParsePlayerObjectIntoJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class Server extends Thread {

    private static final int serverPort = 1011;
    ServerSocket serverSocket;

    private volatile boolean running;

    private static HashMap<String, ClientHandler> clients;


    public Server() {
        try {
            serverSocket = new ServerSocket(serverPort);
            clients = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void sendResponseToClient(String clientName, String responseName, String response) {
        for (String clientsName : clients.keySet()) {
            if (clientName.equalsIgnoreCase(clientsName)) {
                clients.get(clientsName).sendToThisClient(clientName, responseName, response);
            }
        }
    }


    @Override
    public void run() {
        running = true;
        while (running) {
            Socket socket = null;
            System.out.println(clients);
            try {
                socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(this, socket);
                clients.put(socket.getRemoteSocketAddress().toString(), clientHandler);
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static ClientHandler getClientHandler(String authtoken) {
        for (String clientHandleName : clients.keySet()) {
            if (clientHandleName.equalsIgnoreCase(authtoken)) {
                return clients.get(clientHandleName);
            }
        }
        return null;
    }


    public static Player signUp(ClientHandler clientHandler, String userName, String passWord) throws IOException {
        Type type = new TypeToken<List<Player>>() {
        }.getType();
        List<Player> playerList = new Gson().fromJson(new FileReader("Players/AllPlayers.json"), type);
        boolean canSignUp = true;
        for (Player player : playerList) {
            if (userName.equals(player.getUserName())) {
                canSignUp = false;
            }
        }
        if (canSignUp) {
            Player player = new Player(userName, passWord);
            player.setOnlineStatus(true);
            clientHandler.setPlayer(player);
            return player;
        } else {
            return null;
        }
    }

    public static Player signIn(ClientHandler clientHandler, String userName, String passWord) throws IOException {
        Type type = new TypeToken<List<Player>>() {
        }.getType();
        List<Player> playerList = new Gson().fromJson(new FileReader("Players/AllPlayers.json"), type);
        boolean validUserNameAndPassword = false;
        for (Player player : playerList) {
            if (userName.equals(player.getUserName()) && passWord.equals(player.getPassword())) {
                validUserNameAndPassword = true;
                player.setOnlineStatus(true);
                clientHandler.setPlayer(player);
                return player;
            }
        }
        return null;
    }

    public static void logOut(ClientHandler clientHandler) throws IOException {
        clientHandler.getPlayer().setOnlineStatus(false);
        clientHandler.setLoggedIn(false);
        ParsePlayerObjectIntoJson.serializePlayer(clientHandler.getPlayer());
    }


    //getter and setters
    //*********************

    public static HashMap<String, ClientHandler> getClients() {
        return clients;
    }

    public static void setClients(HashMap<String, ClientHandler> clients) {
        Server.clients = clients;
    }


}
