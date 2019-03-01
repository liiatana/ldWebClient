package ru.lanit.ld.wc.appmanager;

import com.codeborne.selenide.Configuration;
import ru.lanit.ld.wc.model.InstructionTypes;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.Users;

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


    public ApplicationManager(String browser, String browserSize) {

        properties = new Properties();
        UserList = new Users();
        InstructionList = new InstructionTypes();

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

        /*if (Objects.equals(browser, BrowserType.FIREFOX)) {
            Configuration.browser = "firefox";
        } else if (Objects.equals(browser, BrowserType.CHROME)) {
            Configuration.browser = "chrome";
        }*/
        Configuration.browser = browser;
        Configuration.baseUrl = baseUrl;
        Configuration.timeout = 10000;

        //Configuration.defaultBrowserSize="1024x768";
       // if (defaultBrowserSize.length() > 0) {
            Configuration.browserSize = defaultBrowserSize;
        //}

        exportEnvirometInfornationToFile();

        //Configuration.browserVersion=""

    }

    private void exportEnvirometInfornationToFile() throws IOException {
        File allureEnvFile = new File("allure-results\\environment.properties");
        if (allureEnvFile.exists()) {
            allureEnvFile.delete();
        }
        allureEnvFile.createNewFile();
        try (FileWriter writer = new FileWriter(allureEnvFile, false);) {
            writer.write("Browser=" + browser);
            writer.write(System.lineSeparator());
            writer.write("Stand=" + baseUrl);
            writer.write(System.lineSeparator());
            writer.write("BrowserSize=" + Configuration.browserSize);
            writer.write(System.lineSeparator());
            writer.write("FrontVersion=" + version);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void stop() {
        //wd.quit();
        focusedUser.getUserApi().makeHomeAsLastUrl();
    }


}
