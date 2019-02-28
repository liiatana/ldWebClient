package ru.lanit.ld.wc.tests;

import org.testng.annotations.*;
import ru.lanit.ld.wc.model.FolderList;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.ViewInstruction;

import static com.codeborne.selenide.Selenide.sleep;

public class ViewInstructionsTests extends TestBase {
    Instructions inst;
    FolderList folderList;

    @BeforeClass
    public void before() {

        LoginPage lp = new LoginPage();
        inst = lp.open("#/login").LoginAs(app.focusedUser).goToFolder(1999);
        folderList = app.focusedUser.getUserApi().getFolderList(1999);
        inst.ActionPanel.PreviewIs("Off");
        sleep(3000);

    }

    @DataProvider
    public Object[][] InstructionIds() {
        int[] instr = {51198803, 51198037, 51199031};//задание, отчет, уведомление
        return new Object[][]{new Object[]{instr[0]}, new Object[]{instr[1]}, new Object[]{instr[2]}};

    }

    @Test(dataProvider = "InstructionIds")
    public void ListViewWithPreviewTest(int InstructionID) {

        inst.ActionPanel.PreviewIs("On");

        inst.InstructionListWithPreview.get(folderList.getInstructionNumInFolder(InstructionID)).click();
        sleep(2000);

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

    @AfterClass
    public void after() {

        inst.goToFolder(1999);
        //sleep(3000);

    }


}



