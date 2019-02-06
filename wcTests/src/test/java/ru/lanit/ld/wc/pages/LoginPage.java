package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.UserInfo;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

      //private WebDriver driver;

    //логин
    //@FindBy(how = How.XPATH, using = "(.//*[normalize-space(text()) and normalize-space(.)='v 2.0.24'])[1]/following::input[1]")
   // private SelenideElement userLogin;

    //       /html/body/div/div/div[12]/main/div/div/div/div/div/div/div[2]/form/div/div/div[1]/div/div/div[1]/div/input

    //пароль
    //@FindBy(how = How.NAME, using = "input-10-1")
    //private SelenideElement userPassword ;//= Selenide.$ ("input-10-1");
    //private SelenideElement userPassword = $ ("div.flex:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > input:nth-child(2)");

    //кнопка Войти
    //@FindBy(how = How.XPATH, using = "(.//*[normalize-space(text()) and normalize-space(.)='v 2.0.24'])[1]/following::button[5]")
    //private SelenideElement loginButton;


    //надпись Не удалось авторизоваться. Попробуйте еще раз!
    //@FindBy(how = How.CLASS_NAME, using = "error-message")
    //private SelenideElement errorText;

    /*public LoginPage LoginAs(UserInfo user) {

        //userPassword.sendKeys(user.getPassword());
        //userLogin.sendKeys(user.getLogin());
        //loginButton.click();
        return page(LoginPage.class);
    }*/

    /*public void init(final WebDriver driver) {
        //this.driver = driver;
        //PageFactory.initElements(driver, this);
    }*/

    public LoginPage() {

    }

    public void LoginAs(UserInfo user) {

        $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='v 2.0.24'])[1]/following::input[1]")).sendKeys(user.getLogin());

        $(By.name("input-10-1")).sendKeys(user.getPassword());

        $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='v 2.0.24'])[1]/following::button[5]")).click();

        //return page(LoginPage.class);
    }
}
