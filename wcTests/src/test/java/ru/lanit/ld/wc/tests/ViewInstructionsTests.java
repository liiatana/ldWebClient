package ru.lanit.ld.wc.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;

import static com.codeborne.selenide.Selenide.sleep;

public class ViewInstructionsTests extends TestBase {
    @BeforeTest
    public void before() {
        LoginPage lp = new LoginPage(app);
        Instructions inst = lp.open("#/login").LoginAs(app.focusedUser);
        sleep(10000);

        inst.SideBar.goToInstructions();

    }


    @DataProvider
    public Object[][] InstructionIds() {
        int[] instr = {51198032, 51198034, 51198037};
        return new Object[][]{new Object[]{instr[0]}, new Object[]{instr[1]}, new Object[]{instr[2]}};

    }

    @Test(dataProvider = "InstructionIds")
    public void ListViewTest(int InstructionID) {


    }


}
