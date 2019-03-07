package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.UserInfo;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage {


    private SelenideElement userLogin;
    private SelenideElement userPassword = $(By.xpath("//input[@id=\"psw-field\"]"));
    private SelenideElement loginButton;

    public SelenideElement errorText;

    public LoginPage() {

        userLogin=$(By.xpath("//input[@id=\"login-field\"]"));
        loginButton=$ (By.xpath("//button[@id=\"sql-auth-btn\"]"));
        errorText=$(By.xpath("//span[@class=\"error-message\"]"));

    }

    public Instructions LoginAs(UserInfo user) {

        fillLoginInfomation(user);
        return page(Instructions.class);

    }


    public LoginPage failedLoginAs(UserInfo user) {

        fillLoginInfomation(user);
        return this;
    }

    @Step("Ввести логин и пароль, нажать кнопку Войти")
    private void fillLoginInfomation(UserInfo user) {


        userLogin.sendKeys(Keys.CONTROL + "a");
        userLogin.sendKeys(user.getLogin());

        userPassword.sendKeys(Keys.CONTROL + "a");
        userPassword.sendKeys(user.getPassword());

        loginButton.click();
    }


    public LoginPage open(String url) {
        Selenide.open(url);
        return this;

    }


}
