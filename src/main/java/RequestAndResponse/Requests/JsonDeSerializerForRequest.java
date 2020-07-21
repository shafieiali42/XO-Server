package RequestAndResponse.Requests;

import RequestAndResponse.Requests.LogInRequest;
import RequestAndResponse.Requests.Request;
import RequestAndResponse.Requests.RequestsNames;
import com.google.gson.Gson;

import java.io.FileReader;
import java.util.HashMap;

public class JsonDeSerializerForRequest {


    public static HashMap<String, Class> map = new HashMap<>();

    public static void setMap() {
        map.clear();
        map.put("LogInRequest", LogInRequest.class);
    }


    public static Request deSerializeRequest(String requestName, String requestString) {
        setMap();
        Gson gson = new Gson();
        Class classOfCard = map.get(requestName);
//            System.out.println(minionNames.name());
        Request request = (Request) gson.fromJson(requestString, classOfCard);
        return request;
    }



}



