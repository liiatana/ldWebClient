package ru.lanit.ld.wc.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.lanit.ld.wc.appmanager.ApplicationManager;

import java.lang.reflect.Method;
import java.util.Arrays;

//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import ru.stqa.pft.addressbook.model.NewContactData;

public class TestBase {
    Logger logger = LoggerFactory.getLogger(TestBase.class);


    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX)); // в настроках запуска теста нужно дописать  -Dbrowser=firefox( в поле VM)

    /*protected static final ApplicationManager app = new ApplicationManager (BrowserType.CHROME);*/
    //@BeforeMethod // если beforemethod-то запускается перед каждым тестовым методом lecture 5.1
    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }


    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        //logger.info("start " + m.getName() + " with parameters " + Arrays.asList(p).toString());
        logger.info("start " + m.getName() + " with parameters " + Arrays.asList(p));

    }


    @AfterMethod(alwaysRun = true) //@AfterMethod //после каждого меnода
    public void logTestStop(Method m) {
        logger.info("end " + m.getName());

    }

}


