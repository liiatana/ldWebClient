package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.lanit.ld.wc.model.UserInfo;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage {


    private SelenideElement userLogin= $(By.xpath("//input[@id=\"login-field\"]"));
    private SelenideElement userPassword = $(By.xpath("//input[@id=\"psw-field\"]"));
    private SelenideElement loginButton= $(By.xpath("//button[@id=\"sql-auth-btn\"]"));
    public SelenideElement version=$(By.xpath("//div[@class=\"version\"]"));

    public SelenideElement errorText=$(By.xpath("//span[@class=\"error-message\"]"));

    public LoginPage() {
    }

    public InstructionsSection LoginAs(UserInfo user) {



        fillLoginInfomation(user);
        sleep(6000);
        return page(InstructionsSection.class);

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

        sleep(1000);
        loginButton.click();
    }


    public LoginPage open() {
        String url= "login";//"http://172.29.17.193/landocsweb2/login";
        Selenide.open(url);
        return this;

    }


}
