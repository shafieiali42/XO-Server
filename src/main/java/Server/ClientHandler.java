package Server;

import RequestAndResponse.Requests.Request;
import RequestAndResponse.Requests.JsonDeSerializerForRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler extends Thread {

    private Socket socket;
    private Server server;
    private String clientName;
    PrintWriter printer;
    private ArrayList<Request> requests;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.requests = new ArrayList<>();
    }


    private void executeRequests() {
        for (Request request : requests) {
            request.execute();
        }
    }


    @Override
    public void run() {
        int counter = 0;
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            printer = new PrintWriter(socket.getOutputStream());
            String authtoken="";
            String requestName="";
            String message="";

            while (scanner.hasNextLine()) {
                String text = scanner.nextLine();
                switch (counter % 3) {
                    case 0:
                        authtoken=text;
                        break;
                    case 1:
                        requestName=text;
                        break;
                    case 2:
                        message=text;
                        Request request= JsonDeSerializerForRequest.deSerializeRequest(requestName,message);
                        this.requests.add(request);
                        executeRequests();
                        break;
                }
                counter++;



            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendToThisClient(String responseName,String message) { //require to send response to clients
        printer.println(responseName);
        printer.println(message);
    }


}
