package ru.lanit.ld.wc.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import ru.lanit.ld.wc.model.*;

import java.util.ArrayList;

public class RestApiHelper {

    private String cookies;
    private String apiPath;

    public RestApiHelper(UserInfo user, ApplicationManager applicationManager) {

        apiPath = applicationManager.properties.getProperty("web.apiUrl");

        Response response = RestAssured
                .given().header("Authorization", user.getAuth())
                .get(String.format("%sauth/basic", apiPath));
        cookies = String.format("landocs.claims=%s; landocs_sessionid=%s", response.getCookie("landocs.claims"), response.getCookie("landocs_sessionid"));

    }

    public JsonElement me() {

        String json = RestAssured
                .given().header("Cookie", cookies)
                .get(String.format("%sme", apiPath))
                .asString();

        return new JsonParser().parse(json);


    }

    public ArrayList<instructionType> instructionTypesInfo() {
        String json = RestAssured
                .given().header("Cookie", cookies)
                .get(String.format("%sinstruction/typesinfo", apiPath))
                .asString();


        JsonElement parsed = new JsonParser().parse(json);

        ArrayList<instructionType> iTypes = new ArrayList<>();

        for (int i = 0; i <= parsed.getAsJsonArray().size() - 1; i++) {
            JsonElement types1 = parsed.getAsJsonArray().get(i).getAsJsonObject().get("instructionType");
            instructionType itype = new Gson().fromJson(types1, instructionType.class);

            JsonElement r2 = parsed.getAsJsonArray().get(i).getAsJsonObject().get("receiverTypes").getAsJsonArray();
            int[] rt = new Gson().fromJson(r2, new TypeToken<int[]>() {
            }.getType());

            itype.receiverTypes = rt;
            iTypes.add(itype);
        }

        return iTypes;
    }

    public instResponse instructionSavePrj(Instruction src) {//сохранить проект сообщения


        String res = RestAssured
                .given().header("Cookie", cookies)
                .contentType("application/json")
                //.cookie(cookies)
                .body(src.toJson(false).toString())
                .when()
                .post(String.format("%sinstruction/", apiPath))
                .asString();

        JsonElement parsed = new JsonParser().parse(res);
        JsonObject asJsonObject = parsed.getAsJsonObject().getAsJsonObject("success");

        return new Gson().fromJson(asJsonObject, new TypeToken<instResponse>() {
        }.getType());

    }

    public instResponse send(Instruction src) { //отправить сообщение


        String res = RestAssured
                .given().header("Cookie", cookies)
                .contentType("application/json")
                //.cookie(cookies)
                .body(src.toJson(true).toString())
                .when()
                .post(String.format("%sinstruction/send", apiPath))
                .asString();

        JsonElement parsed = new JsonParser().parse(res);
        JsonObject asJsonObject = parsed.getAsJsonObject().getAsJsonObject("success");

        return new Gson().fromJson(asJsonObject, new TypeToken<instResponse>() {
        }.getType());

    }

    public reportResponse createReportProject( Report src){// создать проект отчета

       // src.withInitiatorID();

        String res = RestAssured
                .given().header("Cookie", cookies)
                .contentType("application/json")
                //.cookie(cookies)
                .body(src.toJson().toString())
                .when()
                .post(String.format("%sinstruction/%s/report/project", apiPath,src.getInstructionId()))
                .asString();

        JsonElement parsed = new JsonParser().parse(res);
        JsonObject asJsonObject = parsed.getAsJsonObject().getAsJsonObject("success");

        return new Gson().fromJson(asJsonObject, new TypeToken<reportResponse>() {
        }.getType());
    }


}
