package ru.lanit.ld.wc.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.Header;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.SideBar;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class NavigationTests extends TestBase {
    @BeforeTest
    public void before(){
        /*LoginPage loginPage = open(app.baseUrl+"#/login", LoginPage.class);
        sleep(2000);
        SideBar side =loginPage.Login_s(app.UserList.users.get(0));*/
    }

    

    @Test()
    public void openSection() {


        UserInfo user=app.UserList.users.get(0);

        /*LoginPage loginPage = open(app.baseUrl+"#/login", LoginPage.class);
        sleep(2000);

        Header header =loginPage.LoginAs(user);

        sleep(10000);
        Assert.assertEquals(header.getLastName(),user.getLastName());
        sleep(1000);
        header.exit();*/
    }


}
