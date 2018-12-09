package ru.lanit.ld.wc.appmanager;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import ru.lanit.ld.wc.Serializer.instructionSerializer;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.instructionType;
import ru.lanit.ld.wc.model.UserInfo;

import java.time.format.DateTimeFormatter;
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

            JsonElement r2 = parsed.getAsJsonArray().get(i).getAsJsonObject().get("receiverTypes").getAsJsonArray();
            int[] rt = new Gson().fromJson(r2, new TypeToken<int[]>() {
            }.getType());

            itype.receiverTypes = rt;
            iTypes.add(itype);
        }

       return iTypes;
    }

    public void instructionSavePrj(Instruction src){

       /* GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instruction.class, new instructionSerializer()).serializeNulls();
        Gson gson = builder.create();

        //JSONObject jo2 = new JSONObject(gson.toJson(inst).toString());
        //JsonObject s1=gson.toJson(inst).ge;
        String s=gson.toJson(inst).toString();
        //gson.toJson(inst);
        JsonObject js=new JsonObject();
        gson.toJson(js);
*/

        JsonObject mainInstruction = new JsonObject();
        mainInstruction.addProperty("receiverID", src.receiverID[0]);
        //mainInstruction.addProperty("text", src.text);

        mainInstruction.addProperty("text", "9122137");
       // mainInstruction.addProperty("subject", src.subject);
        //mainInstruction.addProperty("subject", "zana");

        mainInstruction.addProperty("comment", src.comment);

        if (src.fileIds == null) {
            mainInstruction.add("fileIds", null);
        } else {
            mainInstruction.addProperty("fileIds", src.fileIds[0]);
        }

        mainInstruction.addProperty("execAuditorID", src.execAuditorID);
        mainInstruction.addProperty("initiatorID", src.initiatorID);
        mainInstruction.addProperty("reportReceiverID", src.reportReceiverID);
        if (src.startDate == null) {
            mainInstruction.add("startDate", null);
        } else {
            mainInstruction.addProperty("startDate",String.format("%sZ",src.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000")))  );
        }


        //mainInstruction.addProperty("startDate", src.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

        if (src.executionDate == null) {
            mainInstruction.add("executionDate", null);
        } else {
            mainInstruction.addProperty("executionDate", String.format("%sZ",src.executionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));
        }

        if (src.execInterval == 0) {
            mainInstruction.add("execInterval", null);
        } else {
            mainInstruction.addProperty("execInterval", src.execInterval);
        }
        if (src.execAuditorID == 0) {
            mainInstruction.add("execAuditorID", null);
        } else {
            mainInstruction.addProperty("execAuditorID", src.execAuditorID);
        }
        if (src.reportReceiverID == 0) {
            mainInstruction.add("reportReceiverID", null);
        } else {
            mainInstruction.addProperty("reportReceiverID", src.reportReceiverID);
        }


        JsonObject jsonInstruction = new JsonObject();
        jsonInstruction.addProperty("instructionTypeId", src.instructionTypeId);

        if (src.documentId == 0) {
            jsonInstruction.add("documentId", null);
        } else {
            jsonInstruction.addProperty("documentId", src.documentId);
        }


        jsonInstruction.addProperty("sendType", src.sendType);
        jsonInstruction.addProperty("reportToExecutive", src.reportToExecutive);
        jsonInstruction.addProperty("withExecutive", src.withExecutive);
        jsonInstruction.addProperty("control", src.control);

        jsonInstruction.add("mainInstruction", mainInstruction);

        JsonObject request = new JsonObject();
        request.add("request", jsonInstruction);

        Response response = RestAssured
                .given().header("Cookie", cookies)
                .contentType( "application/json")
                //.cookie(cookies)
                .body(request.toString())// body(js)
                .when()
                .post(String.format("%sinstruction", apiPath));
                //.put()  ((String.format("%sinstruction", apiPath)),s )
                //.asString();
int i=response.getStatusCode();
//String s=json.toString();
//Headers v=json.getHeaders();
       // JsonElement parsed=new JsonParser().parse(json.); //.parse(json);

    };

}
