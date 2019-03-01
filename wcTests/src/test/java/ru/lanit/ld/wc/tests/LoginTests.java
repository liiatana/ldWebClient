package ru.lanit.ld.wc.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;

import static com.codeborne.selenide.Selenide.sleep;


public class LoginTests extends TestBase {


    @DataProvider
    public Object[][] badAuthUsers() {
        UserInfo user = app.focusedUser;

        UserInfo badUser1 = new UserInfo(user.getLogin(), "");
        UserInfo badUser2 = new UserInfo(user.getLogin(), "wrong");
        UserInfo badUser3 = new UserInfo("", "");

        return new Object[][]{new Object[]{badUser1}, new Object[]{badUser2}, new Object[]{badUser3}};
    }

    @Test(dataProvider = "badAuthUsers", enabled = true,description = "Авторизация пользователя с НЕкорректным паролем")
    public void loginWithWrongAuthenticationData(UserInfo badUser) {

        LoginPage lp = new LoginPage();
        LoginPage loginPage = lp.open("#/login").failedLoginAs(badUser);
        sleep(1000);

        loginPage.errorText.shouldHave(Condition.text("Не удалось авторизоваться. Попробуйте еще раз!"));
        Selenide.refresh();

    }

    @DataProvider
    public Object[][] CoodUsers() {
        UserInfo user = app.focusedUser;
        return new Object[][]{new Object[]{user}};
    }


    @Test(dataProvider = "CoodUsers",description = "Авторизация пользователя с правильным логином/паролем")
    public void loginWithGoodAuthenticationData(UserInfo user) {


        LoginPage lp = new LoginPage();
        Instructions inst = lp.open("#/login").LoginAs(user);

        sleep(10000);

        Assert.assertEquals(inst.Header.getLastName(), user.getLastName());
        inst.Header.exit();

    }


}
