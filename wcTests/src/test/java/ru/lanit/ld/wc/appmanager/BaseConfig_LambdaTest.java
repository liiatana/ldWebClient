package ru.lanit.ld.wc.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class BaseConfig_LambdaTest {
    private WebDriver driver;
    private DesiredCapabilities desiredCapabilities;

    public String getLambdaUserName() {
        return System.getenv("LAMBDA_USERNAME");
    }

    public String getLambdaPassword() {

        return System.getenv("LAMBDA_PASSWORD");
    }

    @BeforeClass
    @Parameters({"browserName", "platform", "browserVersion", "visualEnable"})
    public void init(String browserName, String platform, String browserVersion, String visualEnable) throws MalformedURLException {
        desiredCapabilities = new DesiredCapabilities();
        if (browserName.equals("chrome")) {
            desiredCapabilities.setCapability("browserName", "chrome");
        } else if (browserName.equals("firefox")) {
            desiredCapabilities.setCapability("browserName", "firefox");
        }
        desiredCapabilities.setCapability("platform", platform);
        desiredCapabilities.setCapability("version", browserVersion);
        desiredCapabilities.setCapability("visual", visualEnable);
        desiredCapabilities.setCapability("build", "LambdaTestBuild");
        desiredCapabilities.setCapability("name", "LambdaTest Execution");
        System.out.println("https://" + getLambdaUserName() + ":" + getLambdaPassword() + "@hub.lambdatest.com/wd/hub");
        driver = new RemoteWebDriver(new URL("https://muthu.crr:Kw8xyUoShEO9GAtPqW509OWHggQYzzaLJnznu9R3RChfRSnmhh@hub.lambdatest.com/wd/hub"), desiredCapabilities);
        //driver = new RemoteWebDriver(new URL("https://" + getLambdaUserName() + ":" + getLambdaPassword() + "@hub.lambdatest.com/wd/hub"), desiredCapabilities);
        setWebDriver(driver);
        open("https://www.lambdatest.com/");
        //driver.manage().window().maximize();
    }


}
