package server;


import Logic.Game;
import Logic.TileStatus;
import Model.Board.Board;
import Model.Player.Player;
import Util.Config.ConfigLoader;
import Util.Json.ParsePlayerObjectIntoJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Server extends Thread {

    Properties properties;

    {
        try {
            properties = ConfigLoader.getInstance().
                    readProperties("src/main/resources/ConfigFiles/PortConfig/PortConfig.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String configServerPort = (properties.getProperty("serverPort"));
    private static int serverPort;

    ServerSocket serverSocket;

    private volatile boolean running;

    private static HashMap<String, ClientHandler> clients;
    private static ArrayList<ClientHandler> playQueue;
    private static ArrayList<Game> runningGames;


    public Server() {
        try {
            if (configServerPort == null) {
                serverPort = 8000;
            } else {
                serverPort = Integer.parseInt(configServerPort);
            }
            System.out.println(serverPort);
            serverSocket = new ServerSocket(serverPort);
            clients = new HashMap<>();
            playQueue = new ArrayList<>();
            runningGames = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String checkWins(Board board, int targetTileId) {
        String winner = null;
        TileStatus tileStatus = board.getBoard().get(targetTileId);
        int counter = 0;
        int mode = targetTileId % 7;
        for (int i = 1; i <= mode; i++) {
            if (board.getBoard().get(targetTileId - i).equals(tileStatus)) {
                counter++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 6 - mode; i++) {
            if (board.getBoard().get(targetTileId + i).equals(tileStatus)) {
                counter++;
            } else {
                break;
            }
        }
        if (counter >= 3) {
            if (tileStatus.equals(TileStatus.X)) {
                return winner = "X";
            } else {
                return winner = "O";
            }
        }

        counter = 0;

        int subMultiple = targetTileId / 7;
        for (int i = 1; i <= subMultiple; i++) {
            if (board.getBoard().get(targetTileId - 7 * i).equals(tileStatus)) {
                counter++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 6 - subMultiple; i++) {
            if (board.getBoard().get(targetTileId + 7 * i).equals(tileStatus)) {
                counter++;
            } else {
                break;
            }
        }

        if (counter >= 3) {
            if (tileStatus.equals(TileStatus.X)) {
                return winner = "X";
            } else {
                return winner = "O";
            }
        }


        counter = 0;


        subMultiple = targetTileId / 7;
        mode = targetTileId % 7;
        int plusNum = Math.min((6 - mode), (6 - subMultiple));
        int minusNum = Math.min(mode, subMultiple);


        for (int i = 1; i <= plusNum; i++) {
            if (board.getBoard().get(targetTileId + 8 * i).equals(tileStatus)) {
                counter++;
            } else {
                break;
            }
        }


        for (int i = 1; i <= minusNum; i++) {
            if (board.getBoard().get(targetTileId - 8 * i).equals(tileStatus)) {
                counter++;
            } else {
                break;
            }
        }


        if (counter >= 3) {
            if (tileStatus.equals(TileStatus.X)) {
                return winner = "X";
            } else {
                return winner = "O";
            }
        }


        counter = 0;
        subMultiple = targetTileId / 7;
        mode = targetTileId % 7;
        plusNum = Math.min((6 - subMultiple), (mode));
        minusNum = Math.min(subMultiple, (6 - mode));


        for (int i = 1; i <= plusNum; i++) {
            if (board.getBoard().get(targetTileId + 6 * i).equals(tileStatus)) {
                counter++;
            } else {
                break;
            }
        }


        for (int i = 1; i <= minusNum; i++) {
            if (board.getBoard().get(targetTileId - 6 * i).equals(tileStatus)) {
                counter++;
            } else {
                break;
            }
        }

        if (counter >= 3) {
            if (tileStatus.equals(TileStatus.X)) {

                return winner = "X";
            } else {

                return winner = "O";
            }
        }

        boolean isDraw = true;

        for (int i = 0; i < board.getBoard().size(); i++) {
            if (!(board.getBoard().get(i).equals(TileStatus.X) ||
                    board.getBoard().get(i).equals(TileStatus.O))) {

                isDraw = false;
                break;
            }
        }

        if (isDraw) {
            return "Draw";
        } else {
            return "null";
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

    public static ArrayList<ClientHandler> getPlayQueue() {
        return playQueue;
    }

    public static void setPlayQueue(ArrayList<ClientHandler> playQueue) {
        Server.playQueue = playQueue;
    }

    public static ArrayList<Game> getRunningGames() {
        return runningGames;
    }

    public static void setRunningGames(ArrayList<Game> runningGames) {
        Server.runningGames = runningGames;
    }

}
