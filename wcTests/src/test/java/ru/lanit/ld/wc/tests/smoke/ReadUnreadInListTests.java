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

public class ReadUnreadInListTests extends TestBase {
    InstructionsSection instSection;
    FolderList folderList;
    Instruction instruction;
    int focusedInstructionNum;

    @BeforeClass
    public void before() {

        focusedInstructionNum=0; // порядковый номер сообщения в папке, над которым будем эксперементировать
        folderList = app.focusedUser.getUserApi().getFolderList(1999,focusedInstructionNum+1);

        instruction =app.focusedUser.getUserApi().getInstruction(folderList.items.get(0).getInstructionId());

        app.focusedUser.getUserApi().setReaded(true,instruction.getInstructionId());

        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(app.focusedUser).goToFolder(1999);

        instSection.ActionPanel.setViewState("Off", false, "Дата создания", true);

    }

    @DataProvider
    public Object[][] Object() {

         return new Object[][] {new Object[]{instruction.getInstructionId()}};
    }

    @Test(dataProvider = "Object", priority=1,description = "Непрочитанное сообщение.Шрифт НЕ жирный")
    public void checkFontOnUnreadedInList(int InstructionId){

        String attribute = instSection.cardView.getInstructionTextAsSE(focusedInstructionNum).getAttribute("font-weight");
        Assert.assertEquals(attribute,"700" ); ;

    }

    @Test(dataProvider = "Object", priority=2,description = "Непрочитанное сообщение.Проверить, что в меню доступна операция Сделать непрочитамм")
    public void checkMenuOnUnreadedInstruction(int InstructionId){

        ElementsCollection menu = instSection.cardView.clickActionsMenu(focusedInstructionNum);
               Assert.assertTrue(menu.filter(Condition.text("Пометить непрочитаным")).size()==1);

        instSection.cardView.clickActionsMenu(focusedInstructionNum);
     }



    @Test(dataProvider = "Object", priority=3,description = "Сделать сообщение НЕ прочитанным.Проверить, что в меню доступна операция Сделать прочитанным")
    public void makeUnreaded(int InstructionId){

        ElementsCollection menu = instSection.cardView.clickActionsMenu(focusedInstructionNum);
        Assert.assertTrue(menu.filter(Condition.text("Пометить непрочитаным")).size()==1);

    }




    @AfterClass
    public void after() {

        instSection.ActionPanel.setViewState("Off", false, "Дата создания", true);
      //sleep(3000);

    }


}