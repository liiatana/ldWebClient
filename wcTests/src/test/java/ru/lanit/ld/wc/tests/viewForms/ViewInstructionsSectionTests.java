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

        app.focusedUser.getUserApi().makeHomeAsLastUrl();
        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(app.focusedUser);
        folderList = app.focusedUser.getUserApi().getFolderList(1999,10);
        instSection.ActionPanel.setViewState("Off",false,"Дата создания",true);
        sleep(8000);

    }

    @DataProvider
    public Object[][] InstructionIds() {
        int[] instr = {51199861, 51204124, 51199031};//задание, отчет, уведомление
        return new Object[][]{new Object[]{instr[0]}, new Object[]{instr[1]}, new Object[]{instr[2]}};

    }

    @Test(dataProvider = "InstructionIds",description = "Открыть в Preview задание,отчет,сообщение")
    public void ListViewWithPreviewTest(int InstructionID) {

        instSection.ActionPanel.PreviewIs("On");

        instSection.InstructionListWithPreview.get(folderList.getInstructionNumInFolder(InstructionID)).click();
        sleep(5000);

        instSection.ActionPanel.PreviewIs("Off");

    }

    @Test(dataProvider = "InstructionIds",description = "Открыть на просмотр задание,отчет,сообщение")
    public void ViewInstructionTest(int InstructionID) {

        //ViewInstruction viewInstr = instSection.openInstructionView(InstructionID);
        ViewInstruction viewInstr=instSection.cardView.open(folderList.getInstructionNumInFolder(InstructionID));

        sleep(2000);

        viewInstr.ThreePoints.click();
        sleep(1000);

        for (int i = 0; i <= viewInstr.Blocks.size() - 1; i++) {
            viewInstr.Blocks.get(i).scrollIntoView(true);

            if (viewInstr.IsBlockActive(i)) {
                viewInstr.clickOnBlock(i);
                sleep(5000);
                viewInstr.Blocks.get(i).scrollIntoView(true);

            }

        }

        viewInstr.BackStep();

    }

    @AfterClass
    public void after() {

    }


}



