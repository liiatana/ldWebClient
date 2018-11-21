package ru.lanit.ld.wc.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import ru.lanit.ld.wc.model.instructionType;
import ru.lanit.ld.wc.model.UserInfo;

import java.util.ArrayList;

public class RestApiHelper {

    private String cookies;
    private String apiPath;

    public RestApiHelper(UserInfo user, ApplicationManager applicationManager) {

        apiPath = applicationManager.properties.getProperty("web.apiUrl");

        Response response = RestAssured
                .given().header("Authorization", user.getAuth())
                .get(String.format("%s/auth/basic", apiPath));
        cookies = String.format("landocs.claims=%s; landocs_sessionid=%s", response.getCookie("landocs.claims"), response.getCookie("landocs_sessionid"));

    }

    public JsonElement me() {

        String json = RestAssured
                .given().header("Cookie", cookies)
                .get(String.format("%sme", apiPath))
                .asString();

       /* JsonElement parsed = new JsonParser().parse(json);
        //parsed=parsed.getAsJsonObject().get("effectiveUserId");

        return parsed.getAsJsonObject().get("effectiveUserId").getAsInt();
                //.getAsJsonArray().get(0)
                //.getAsJsonObject().get("state").getAsInt();*/

        return new JsonParser().parse(json);


    }

    public ArrayList<instructionType> instructionTypesInfo(){
        String json = RestAssured
                .given().header("Cookie", cookies)
                .get(String.format("%sinstruction/typesinfo", apiPath))
                .asString();


        JsonElement parsed=new JsonParser().parse(json);

        ArrayList<instructionType> iTypes = new ArrayList<>();

        for (int i = 0; i <= parsed.getAsJsonArray().size()-1; i++) {
            JsonElement types1 = parsed.getAsJsonArray().get(i).getAsJsonObject().get("instructionType");
            instructionType itype = new Gson().fromJson(types1, instructionType.class);

            JsonElement r2 = parsed.getAsJsonArray().get(0).getAsJsonObject().get("receiverTypes").getAsJsonArray();
            int[] rt = new Gson().fromJson(r2, new TypeToken<int[]>() {
            }.getType());

            itype.receiverTypes = rt;
            iTypes.add(itype);
        }

       return iTypes;
    }

}
