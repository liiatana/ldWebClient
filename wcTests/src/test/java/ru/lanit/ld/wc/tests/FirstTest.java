package ru.lanit.ld.wc.tests;

import org.testng.annotations.Test;
import ru.lanit.ld.wc.appmanager.RestApiHelper;

public class FirstTest extends TestBase {

    @Test(enabled = true)
    public void addContactToGroupTest() {
        System.out.println(app.properties.getProperty("web.baseUrl"));
        System.out.println(app.UserList.users.get(1).getLogin());
        RestApiHelper api= new RestApiHelper(app.UserList.users.get(1),app.properties.getProperty("web.apiUrl"));
        int i=api.me(app.UserList.users.get(1));
    }

}
