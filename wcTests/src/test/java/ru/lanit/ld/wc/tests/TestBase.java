package ru.lanit.ld.wc.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.pages.LoginPage;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {
    public Logger logger = LoggerFactory.getLogger(TestBase.class);


    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME), System.getProperty("browserSize", "1366x768")); //
    // в настроках запуска теста нужно дописать  -Dbrowser=firefox( в поле VM)
    // -DbrowserSize="1024х768"

    @BeforeSuite
    public void setUp() throws Exception {

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
        app.init();
        app.logManager.addEnviromentInfo("FrontVersion",getFrontVersion());

    }


    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        //logger.info("start " + m.getName() + " with parameters " + Arrays.asList(p).toString());
        logger.info("Start test: " + m.getName() + " with parameters " + Arrays.asList(p));

    }

    @AfterMethod(alwaysRun = true) //@AfterMethod //после каждого меnода
    public void logTestStop(Method m) {
        logger.info("End test: " + m.getName());
    }


    /*@AfterClass //от лямбды
    public void tearDown() {
        if (driver != null)
            driver.quit();

    }*/

    private String getFrontVersion(){

        LoginPage lp = new LoginPage();
        return lp.open().version.getText();

    }

}


