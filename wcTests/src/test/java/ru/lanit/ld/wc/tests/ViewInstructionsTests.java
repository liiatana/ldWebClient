package ru.lanit.ld.wc.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.ViewInstruction;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.sleep;

public class ViewInstructionsTests extends TestBase {
    Instructions inst;
    List<Instruction> folderList= new ArrayList<>();

    @BeforeTest
    public void before() {

        folderList=app.focusedUser.getUserApi().getFolderList(1999);
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

        viewInstr.ThreePoints.click();
        sleep(3000);

        for (int i = 0; i <= viewInstr.Blocks.size()-1; i++) {
            viewInstr.Blocks.get(i).scrollIntoView(true) ;

            if (viewInstr.IsBlockActive(i)) {
                viewInstr.clickOnBlock(i);
                sleep(5000);

            }

        }

    }


}



