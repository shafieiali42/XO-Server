package Server;


import Model.Player.Player;
import RequestAndResponse.Requests.Request;
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

    private static final int serverPort = 8585;
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


    public static void sendResponseToClient(String clientName,String responseName,String response){
        for (String clientsName:clients.keySet()){
            if (clientName.equalsIgnoreCase(clientsName)){
                clients.get(clientsName).sendToThisClient(responseName,response);
            }
        }
    }


    @Override
    public void run() {
        running = true;
        while (running) {
            Socket socket = null;
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


    public static Player signUp(String userName, String passWord) throws IOException {
        Type type = new TypeToken<List<Player>>() {}.getType();
        List<Player> playerList = new Gson().fromJson(new FileReader("Players/AllPlayers.json"), type);
        boolean canSignUp = true;
        for (Player player : playerList) {
            if (userName.equals(player.getUserName())) {
                canSignUp = false;
            }
        }
        if (canSignUp) {
            Player player = new Player(userName, passWord);
            return player;
        } else {
            return null;
        }
    }

    public static Player signIn(String userName,String passWord) throws IOException {
        Type type = new TypeToken<List<Player>>() { }.getType();
        List<Player> playerList = new Gson().fromJson(new FileReader("Players/AllPlayers.json"), type);
        boolean valiUserNameAndPassword = false;
        for (Player player : playerList) {
            if (userName.equals(player.getUserName()) && passWord.equals(player.getPassword())) {
                valiUserNameAndPassword = true;
                return player;
            }
        }
        return null;
    }



}
