package ru.lanit.ld.wc.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.ViewInstruction;

import static com.codeborne.selenide.Selenide.sleep;

public class ViewInstructionsTests extends TestBase {
    Instructions inst;


    @BeforeTest
    public void before() {
        LoginPage lp = new LoginPage(app);
        inst = lp.open("#/login").LoginAs(app.focusedUser);

        sleep(10000);

    }

    @Test()
    public void ListViewTest() {

        inst.SideBar.goHome();
        inst.ActionPanel.PreviewIs("Off");


    }

    @DataProvider
    public Object[][] InstructionIds() {
        int[] instr = {51198803, 51198037, 51199031};//задание, отчет, уведомление
        return new Object[][]{new Object[]{instr[0]}, new Object[]{instr[1]}, new Object[]{instr[2]}};

    }

    @Test(dataProvider = "InstructionIds")
    public void ListViewWithPreviewTest(int InstructionID) {

        inst.SideBar.goHome();
        inst.ActionPanel.PreviewIs("On");

    }

    @Test(dataProvider = "InstructionIds")
    public void ViewInstructionTest(int InstructionID) {

        ViewInstruction viewInstr = inst.openInstructionView(InstructionID);
        sleep(3000);

        for (int i = 0; i <= viewInstr.Blocks.size()-1; i++) {
            if (viewInstr.IsBlockActive(i)) {
                viewInstr.clickOnBlock(i);
                sleep(2000);
            }

        }

    }


}



