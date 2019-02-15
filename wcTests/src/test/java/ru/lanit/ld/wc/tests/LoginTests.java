package ru.lanit.ld.wc.tests;

import com.codeborne.selenide.Selenide;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.Header;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;


public class LoginTests extends TestBase {



    @DataProvider
    public Object[][] badAuthUsers() {
        UserInfo user=app.focusedUser;

        UserInfo badUser1= new UserInfo(user.getLogin(),"");
        UserInfo badUser2= new UserInfo(user.getLogin(),"wrong");
        UserInfo badUser3= new UserInfo("","");

        return new Object[][] {new Object[]{badUser1},new Object[]{badUser2},new Object[]{badUser3}};
    }

    @Test(dataProvider = "badAuthUsers",enabled=true) //dataProvider = "Notice"
    public void loginWithWrongAuthenticationData(UserInfo badUser) {

        LoginPage lp=new LoginPage(app);
        LoginPage loginPage=lp.open("#/login").failedLoginAs(badUser);
        Assert.assertTrue(loginPage.errorText.isDisplayed());
        Assert.assertEquals("Не удалось авторизоваться. Попробуйте еще раз!",loginPage.errorText.getText());

    }


    @Test()
    public void openApplication() {

        UserInfo user=app.focusedUser;

        LoginPage lp=new LoginPage(app);
        Instructions inst=lp.open("#/login").LoginAs(user);

        sleep(10000);

        Assert.assertEquals( inst.Header.getLastName(),user.getLastName());
        inst.Header.exit();

    }


}
