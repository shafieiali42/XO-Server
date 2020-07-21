package RequestAndResponse.Response;

import RequestAndResponse.Requests.Request;
import com.google.gson.Gson;

import java.util.HashMap;

public class JsonDeSerializerForResponse {



    public static HashMap<String, Class> map = new HashMap<>();

    public static void setMap() {
        map.clear();
        map.put("LogInResponse", LogInResponse.class);
    }


    public static Request deSerializeRequest(String responseName, String responseString) {
        setMap();
        Gson gson = new Gson();
        Class classOfCard = map.get(responseName);
//            System.out.println(minionNames.name());
        Request request = (Request) gson.fromJson(responseString, classOfCard);
        return request;
    }
}
