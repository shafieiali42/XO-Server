package RequestAndResponse.Requests;

import com.google.gson.Gson;

import java.util.HashMap;

public class JsonDeSerializerForRequest {


    public static HashMap<String, Class> map = new HashMap<>();

    public static void setMap() {
        map.clear();
        map.put("LogInRequest", LogInRequest.class);
        map.put("LogOutRequest",LogOutRequest.class);
        map.put("ScoreBoardRequest",ScoreBoardRequest.class);
        map.put("PlayRequest",PlayRequest.class);
        map.put("PlayPieceRequest",PlayPieceRequest.class);
        map.put("EndGameRequest",EndGameRequest.class);
        map.put("StatusRequest",StatusRequest.class);
        map.put("ScreenRecorderRequest",ScreenRecorderRequest.class);
    }


    public static Request deSerializeRequest(String applicator,String requestName, String requestString) {
        setMap();
        Gson gson = new Gson();
        Class classOfCard = map.get(requestName);
//            System.out.println(minionNames.name());
        Request request = (Request) gson.fromJson(requestString, classOfCard);
        request.setApplicator(applicator);
        return request;
    }



}



