package ru.lanit.ld.wc.appmanager;

import com.codeborne.selenide.Configuration;
import ru.lanit.ld.wc.model.InstructionTypes;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.Users;
import ru.lanit.ld.wc.model.viewState;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {

    //public WebDriver wd;
    public String browser;
    public String baseUrl;
    public String apiUrl;
    public String defaultBrowserSize;


    public final Properties properties;
    public Users UserList;
    public InstructionTypes InstructionList;
    public UserInfo focusedUser;
    public viewState defaultViewState;

    public AllureManager allureManager;


    public ApplicationManager(String browser, String browserSize) {

        properties = new Properties();
        UserList = new Users();
        InstructionList = new InstructionTypes();
        defaultViewState= new viewState();

        this.browser = browser;
        this.defaultBrowserSize = browserSize;

    }

    public void init() throws IOException {

        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src\\test\\resources\\%s.properties", target))));

        UserList.load(this);
        focusedUser = UserList.users.get(0);

        //focusedUser.getUserApi().makeHomeAsLastUrl();


        baseUrl = properties.getProperty("web.baseUrl");
        apiUrl=properties.getProperty("web.apiUrl");

        switch (browser) {
            case  ("chrome"):
                System.setProperty("webdriver.chrome.driver", "C:\\1\\chromedriver.exe");
                break;

            case  ("firefox"):
                System.setProperty("webdriver.gecko.driver", "C:\\1\\geckodriver.exe");
                break;
        }



        Configuration.browser = browser;
        Configuration.baseUrl = baseUrl;
        Configuration.timeout = 20000;
        Configuration.browserSize = defaultBrowserSize;
        Configuration.screenshots=true;
        Configuration.reopenBrowserOnFail=true;

        allureManager =new AllureManager(properties.getProperty("data.allureProrertyFilePath"));
        allureManager.exportEnviromentInfornationToFile(this);

    }


    public void stop() {

        focusedUser.getUserApi().makeHomeAsLastUrl();
    }


}
