package ru.lanit.ld.wc.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.FolderList;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.ViewInstruction;

import static com.codeborne.selenide.Selenide.sleep;

public class ViewInstructionsTests extends TestBase {
    Instructions inst;
    FolderList list;

    @BeforeTest
    public void before() {

        list = app.focusedUser.getUserApi().getFolderList(1999);
        LoginPage lp = new LoginPage(app);
        inst = lp.open("#/login").LoginAs(app.focusedUser).goToFolder(1999);

    }

    @DataProvider
    public Object[][] InstructionIds() {
        int[] instr = {51198803, 51198037, 51199031};//задание, отчет, уведомление
        return new Object[][]{new Object[]{instr[0]}, new Object[]{instr[1]}, new Object[]{instr[2]}};

    }

    @Test(dataProvider = "InstructionIds")
    public void ListViewWithPreviewTest(int InstructionID) {


        inst.ActionPanel.PreviewIs("On");

        int nnum = list.getInstructionNumInFolder(InstructionID);

        inst.InstructionList.get(nnum).click();
        sleep(2000);

        inst.ActionPanel.PreviewIs("Off");

    }

    @Test(dataProvider = "InstructionIds")
    public void ViewInstructionTest(int InstructionID) {

        ViewInstruction viewInstr = inst.openInstructionView(InstructionID);
        sleep(2000);

        viewInstr.ThreePoints.click();
        sleep(1000);

        for (int i = 0; i <= viewInstr.Blocks.size() - 1; i++) {
            viewInstr.Blocks.get(i).scrollIntoView(true);

            if (viewInstr.IsBlockActive(i)) {
                viewInstr.clickOnBlock(i);
                sleep(5000);

            }

        }

    }


}



