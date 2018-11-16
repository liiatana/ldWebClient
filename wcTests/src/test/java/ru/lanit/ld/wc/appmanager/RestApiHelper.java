package ru.lanit.ld.wc.appmanager;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import ru.lanit.ld.wc.model.UserInfo;

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


}
