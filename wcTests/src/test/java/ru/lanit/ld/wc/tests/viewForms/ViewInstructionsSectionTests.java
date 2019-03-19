package ru.lanit.ld.wc.tests.viewForms;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.FolderList;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.ViewInstruction;
import ru.lanit.ld.wc.tests.TestBase;

import static com.codeborne.selenide.Selenide.sleep;

public class ViewInstructionsSectionTests extends TestBase {
    InstructionsSection instSection;
    FolderList folderList;

    @BeforeClass
    public void before() {

        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(app.focusedUser).goToFolder(1999);
        folderList = app.focusedUser.getUserApi().getFolderList(1999,10);
        instSection.ActionPanel.PreviewIs("On");
        instSection.ActionPanel.viewOnlyNew(false);
        sleep(1000);

    }

    @DataProvider
    public Object[][] InstructionIds() {
        int[] instr = {51198803, 51198037, 51199031};//задание, отчет, уведомление
        return new Object[][]{new Object[]{instr[0]}, new Object[]{instr[1]}, new Object[]{instr[2]}};

    }

    @Test(dataProvider = "InstructionIds",description = "Открыть в Preview задание,отчет,сообщение")
    public void ListViewWithPreviewTest(int InstructionID) {

        //instSection.ActionPanel.PreviewIs("On");

        instSection.InstructionListWithPreview.get(folderList.getInstructionNumInFolder(InstructionID)).click();
        sleep(2000);

    }

    @Test(dataProvider = "InstructionIds",description = "Открыть на просмотр задание,отчет,сообщение")
    public void ViewInstructionTest(int InstructionID) {

        ViewInstruction viewInstr = instSection.openInstructionView(InstructionID);
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
        instSection.goToFolder(1999);
        instSection.ActionPanel.PreviewIs("Off");
        sleep(2000);
    }


}



