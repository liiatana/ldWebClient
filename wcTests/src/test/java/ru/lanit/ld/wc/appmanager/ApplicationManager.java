package ru.lanit.ld.wc.appmanager;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.remote.BrowserType;
import ru.lanit.ld.wc.model.InstructionTypes;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.Users;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ApplicationManager {

    //public WebDriver wd;
    private String browser;
    public String baseUrl;
    public String version;


    public final Properties properties;
    public Users UserList;
    public InstructionTypes InstructionList;
    public UserInfo focusedUser;


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
        version = properties.getProperty("version");
        baseUrl = properties.getProperty("web.baseUrl");

        if (Objects.equals(browser, BrowserType.FIREFOX)) {
            Configuration.browser = "firefox";
        } else if (Objects.equals(browser, BrowserType.CHROME)) {
            Configuration.browser = "chrome";
        }
        Configuration.baseUrl = baseUrl;
        Configuration.timeout = 6000;
        //Configuration.browserSize ("");

    }


    public void stop() {
        //wd.quit();
    }


}
