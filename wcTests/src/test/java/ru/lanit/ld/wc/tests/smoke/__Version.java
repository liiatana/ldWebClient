package ru.lanit.ld.wc.tests.smoke;

import org.testng.annotations.Test;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

public class __Version extends TestBase {

    @Test
    public void getFrontVersion() {
        LoginPage lp = new LoginPage();
        app.logManager.addEnviromentInfo("FrontVersion", lp.open().version.getText());
    }


    @Test
    public void getBackVersion() {
        app.logManager.addEnviromentInfo("BackVersion",app.UserList.users.get(0).getUserApi().getBackVersion());
    }

}
