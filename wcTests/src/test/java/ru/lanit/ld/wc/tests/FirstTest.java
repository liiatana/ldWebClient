package ru.lanit.ld.wc.tests;

import org.testng.annotations.Test;

public class FirstTest extends TestBase {

    @Test(enabled = true)
    public void addContactToGroupTest() {
        System.out.println(app.properties.getProperty("web.baseUrl"));
        System.out.println(app.UserList.users.get(1).getLogin());
    }

}
