package ru.lanit.ld.wc.tests.smoke;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class __Version extends TestBase {

    @Test
    public void getFrontVersion() {
        LoginPage lp = new LoginPage();
        //app.allureManager.addEnviromentInfo("FrontVersion", lp.open().version.getText());
        assertThat(lp.open().version.getText().substring(2),equalTo("3.5.22.11"));
    }


    @Test
    public void getBackVersion() {
        //app.allureManager.addEnviromentInfo("BackVersion",app.UserList.users.get(0).getUserApi().getBackVersion());

        assertThat(app.UserList.users.get(0).getUserApi().getBackVersion(),equalTo("3.5.22.11"));
    }

}
