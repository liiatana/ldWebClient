package ru.lanit.ld.wc.tests.smoke;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import static com.codeborne.selenide.Selenide.sleep;


public class _Login_Tests extends TestBase {


    @DataProvider
    public Object[][] badAuthUsers() {
        UserInfo user = app.focusedUser;

        UserInfo badUser1 = new UserInfo(user.getLogin(), "");
        UserInfo badUser2 = new UserInfo(user.getLogin(), "wrong");
        UserInfo badUser3 = new UserInfo("", "");

        return new Object[][]{new Object[]{badUser1}, new Object[]{badUser2}, new Object[]{badUser3}};
    }

    @Test(dataProvider = "badAuthUsers",priority = 1, enabled = true,description ="Авторизация пользователя с НЕкорректным паролем" )
    public void loginWithWrongAuthenticationData(UserInfo badUser) {


        LoginPage lp = new LoginPage();
        LoginPage loginPage = lp.open().failedLoginAs(badUser);
        sleep(1000);

        loginPage.errorText.shouldHave(Condition.text("Не удалось авторизоваться. Попробуйте еще раз!"));
        Selenide.refresh();

    }

    @DataProvider
    public Object[][] GoodUsers() {
        UserInfo user = app.focusedUser;
        return new Object[][]{new Object[]{user}};
    }


    @Test(dataProvider = "GoodUsers",priority = 1, description ="Авторизация пользователя с верными данными" )
    public void loginWithGoodAuthenticationData(UserInfo user) {


        LoginPage lp = new LoginPage();
        InstructionsSection inst = lp.open().LoginAs(user);

        Assert.assertEquals(inst.Header.getLastName().toUpperCase(), user.getLastName().toUpperCase());
        inst.Header.exit();

    }


}
