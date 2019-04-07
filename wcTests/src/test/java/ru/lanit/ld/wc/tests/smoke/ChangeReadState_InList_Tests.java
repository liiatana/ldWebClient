package ru.lanit.ld.wc.tests.smoke;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.FolderList;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

public class ChangeReadState_InList_Tests extends TestBase {
    InstructionsSection instSection;
    FolderList folderList;
    Instruction instruction;
    int focusedInstructionNum;

    @BeforeClass
    public void before() {

        focusedInstructionNum=0; // порядковый номер сообщения в папке, над которым будем эксперементировать
        folderList = app.focusedUser.getUserApi().getFolderList(1999,focusedInstructionNum+1);

        app.focusedUser.getUserApi().setReaded(false,folderList.items.get(focusedInstructionNum+1).getInstructionId());

        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(app.focusedUser).goToFolder(1999);

        instSection.ActionPanel.setViewState("Off", false, "Дата создания", true);

    }

    @DataProvider
    public Object[][] Object() {

        return new Object[][] {
                {app.focusedUser.getUserApi().getInstruction(folderList.items.get(focusedInstructionNum).getInstructionId()),focusedInstructionNum},
         };
    }

    @Test(dataProvider = "Object", priority=3, description = "Непрочитанное сообщение.Сделать прочитанным")
    public void setReaded(Instruction instr,int num, String expected_fontWeight, String expected_menuText, Boolean expected_redCircleState){

        //ElementsCollection menu = instSection.cardView.clickActionsMenu(focusedInstructionNum);
        //       Assert.assertTrue(menu.filter(Condition.text("Пометить непрочитаным")).size()==1);

       // instSection.cardView.clickActionsMenu(focusedInstructionNum);
     }



    @AfterClass
    public void after() {

    }


}
