package ru.lanit.ld.wc.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.lanit.ld.wc.model.InstructionTypes;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.Users;
import ru.lanit.ld.wc.pages.LoginPage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ApplicationManager {

    public WebDriver wd;
    private String browser;
    public String baseUrl;


    public final Properties properties;
    public Users UserList;
    public InstructionTypes InstructionList;
    public UserInfo focusedUser;
    //public RestApiHelper focusedUserApi;


    public ApplicationManager(String browser) {

        properties = new Properties();
        UserList = new Users();
        InstructionList = new InstructionTypes();

        this.browser = browser;
    }

    public void init() throws IOException {

        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src\\test\\resources\\%s.properties", target))));
        //UserList.load(properties.getProperty("data.usersFilePath"));
        UserList.load(this);
        focusedUser = UserList.users.get(0);
        //InstructionList.load(this);
        baseUrl=properties.getProperty("web.baseUrl");



        /*if (Objects.equals(browser, BrowserType.FIREFOX)) {
            wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        } else if (Objects.equals(browser, BrowserType.CHROME)) {
            wd = new ChromeDriver() ;

        } else if ((Objects.equals(browser, BrowserType.IE))) {
            wd = new InternetExplorerDriver();
        }*/

    }


    public void stop() {
        //wd.quit();
    }

    /*public RestApiHelper apiHelper() {
        return apiHelper();
    }*/

}
