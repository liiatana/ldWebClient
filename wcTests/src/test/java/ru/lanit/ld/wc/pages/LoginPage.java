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
    private SelenideElement userLogin=$(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='v 2.0.24'])[1]/following::input[1]"));


    //пароль
    private SelenideElement userPassword = $ (By.name("input-10-1"));

    //кнопка Войти
    private SelenideElement loginButton=$(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='v 2.0.24'])[1]/following::button[5]"));


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

    public LoginPage() { }

    public void LoginAs(UserInfo user) {

        userLogin.sendKeys(user.getLogin());
        userPassword.sendKeys(user.getPassword());
        loginButton.click();

        //return page(LoginPage.class);
    }


}
