package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.UserInfo;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage {

    //private WebDriver driver;

    //логин
    private SelenideElement userLogin;

    //пароль
    private SelenideElement userPassword = $(By.name("input-10-1"));

    //кнопка Войти
    private SelenideElement loginButton;

    //надпись Не удалось авторизоваться. Попробуйте еще раз!
    public SelenideElement errorText;

    public LoginPage(ApplicationManager app) {

        //userLogin = $(By.xpath(String.format("(.//*[normalize-space(text()) and normalize-space(.)='v %s'])[1]/following::input[1]", app.version)));
        userLogin=$(By.xpath("//input[@aria-label=\"Логин\"]"));
        loginButton = $(By.xpath(String.format("(.//*[normalize-space(text()) and normalize-space(.)='v %s'])[1]/following::button[5]", app.version)));
        //loginButton=$(By.xpath("//*[@id=\"main-content\"]/div/div/div[1]/div/div/div/div[2]/form/div/div/div[3]/button[1]"));
        errorText = $(By.xpath(String.format("(.//*[normalize-space(text()) and normalize-space(.)='v %s'])[1]/following::span[2]", app.version)));
    }

    public Instructions LoginAs(UserInfo user) {

        fillLoginInfomation(user);
        return page(Instructions.class);
    }

    public LoginPage failedLoginAs(UserInfo user) {

        fillLoginInfomation(user);
        return this;
    }

    private void fillLoginInfomation(UserInfo user) {

        //userLogin.sendKeys(user.getLogin());
        userLogin.setValue(user.getLogin());
        userPassword.sendKeys(user.getPassword());
        loginButton.click();
    }


    public LoginPage open(String url) {
        Selenide.open(url);
        return this;

    }


}
