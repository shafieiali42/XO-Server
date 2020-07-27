package RequestAndResponse.Response;

import com.google.gson.Gson;

import java.util.HashMap;

public class JsonDeSerializerForResponse {



    public static HashMap<String, Class> map = new HashMap<>();

    public static void setMap() {
        map.clear();
        map.put("LogInResponse", LogInResponse.class);
        map.put("LogOutResponse",LogOutResponse.class);
        map.put("ScoreBoardResponse",ScoreBoardResponse.class);
        map.put("StartGameResponse",StartGameResponse.class);
        map.put("PlayPieceResponse",PlayPieceResponse.class);
        map.put("EndGameResponse",EndGameResponse.class);
        map.put("StatusResponse",StatusResponse.class);
        map.put("ScreenRecorderResponse",ScreenRecorderResponse.class);
    }


    public static Response deSerializeResponse(String responseName, String responseString) {
        setMap();
        Gson gson = new Gson();
        Class classOfCard = map.get(responseName);
//            System.out.println(minionNames.name());
        Response response = (Response) gson.fromJson(responseString, classOfCard);
        return response;
    }
}
