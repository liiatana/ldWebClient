package ru.lanit.ld.wc.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;


public class LoginTests extends TestBase {
    @Test()
    public void openApplication() {

        LoginPage loginPage = new LoginPage();
        loginPage = open(app.baseUrl+"#/login", LoginPage.class);
        //PageFactory.initElements(app.wd,loginPage);

        // loginPage.init(app.wd);
        loginPage.LoginAs(app.focusedUser);
    }

}
