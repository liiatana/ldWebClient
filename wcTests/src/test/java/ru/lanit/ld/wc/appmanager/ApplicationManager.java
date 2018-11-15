package ru.lanit.ld.wc.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import ru.lanit.ld.wc.model.Users;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ApplicationManager {

 // WebDriver wd;

  public final Properties properties;
  public Users UserList;


  public ApplicationManager(String browser)  {

    properties = new Properties();
    UserList= new Users();

  }
  public void init() throws IOException {

    String target=System.getProperty("target","local");
    properties.load(new FileReader(new File(String.format("src\\test\\resources\\%s.properties",target))));
    UserList.load(properties.getProperty("data.usersFilePath"));

    //wd.get(properties.getProperty("web.baseUrl"));

  }


  public void stop() {
    //wd.quit();
  }

  public RestApiHelper apiHelper() {
    return apiHelper();
  }

}
