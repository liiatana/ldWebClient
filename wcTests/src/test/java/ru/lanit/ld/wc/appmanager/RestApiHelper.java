package ru.lanit.ld.wc.appmanager;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.Validatable;
import ru.lanit.ld.wc.model.UserInfo;

public class RestApiHelper {

    private String cookies;
    private String apiPath;

    public RestApiHelper(UserInfo user,String apiPath) {
        
        apiPath=apiPath;

        Response response=RestAssured
                   .given().header("Authorization",user.getAuth())
                   //.get("http://195.26.187.231:8080/LanDocs.WebApi.NetCore/api/v1/auth/basic");
                   .get( String.format("%s/auth/basic",apiPath));
        cookies=String.format("landocs.claims=%s; landocs_sessionid=%s",response.getCookie("landocs.claims"),response.getCookie("landocs_sessionid"));

    }

    public int me(UserInfo user){


        String json1=RestAssured.given().header("Cookie",cookies)
                //parameter("issue_id",issueId).
                //.basic("Basic ZGJhOnNxbA==", "")
                .get(String.format("%s/v1/me",apiPath))
                .asString();

        return 1;


    }



}
