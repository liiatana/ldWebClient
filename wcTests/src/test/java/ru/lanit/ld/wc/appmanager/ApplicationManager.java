package ru.lanit.ld.wc.appmanager;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import ru.lanit.ld.wc.model.InstructionTypes;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.Users;
import ru.lanit.ld.wc.model.viewState;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {

    //public WebDriver wd;
    private String browser;
    public String baseUrl;
    public String version;
    private String defaultBrowserSize;

    public final Properties properties;
    public Users UserList;
    public InstructionTypes InstructionList;
    public UserInfo focusedUser;
    public viewState defaultViewState;


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

        focusedUser.getUserApi().makeHomeAsLastUrl();

        version = properties.getProperty("version");
        baseUrl = properties.getProperty("web.baseUrl");


        switch (browser) {
            case  ("chrome"):
                System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
                break;

        }



        Configuration.browser = browser;
        Configuration.baseUrl = baseUrl;
        Configuration.timeout = 30000;
        Configuration.browserSize = defaultBrowserSize;
        Configuration.screenshots=true;
        Configuration.reopenBrowserOnFail=true;

        exportEnviromentInfornationToFile();


    }

    private void exportEnviromentInfornationToFile() throws IOException {
        File allureEnvFile = new File("allure-results\\environment.properties");
        if (allureEnvFile.exists()) {
            allureEnvFile.delete();
        }
        allureEnvFile.createNewFile();
        try (FileWriter writer = new FileWriter(allureEnvFile, false);) {
            writer.write("Browser=" + browser);
            writer.write(System.lineSeparator());
            writer.write("BrowserSize=" + Configuration.browserSize);
            writer.write(System.lineSeparator());
            writer.write("Stand=" + baseUrl);
            writer.write(System.lineSeparator());
            writer.write("FrontVersion=" + version);
            writer.write(System.lineSeparator());
            writer.write("Back=" + properties.getProperty("web.apiUrl"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void stop() {
        //wd.quit();
        focusedUser.getUserApi().makeHomeAsLastUrl();
    }


}
