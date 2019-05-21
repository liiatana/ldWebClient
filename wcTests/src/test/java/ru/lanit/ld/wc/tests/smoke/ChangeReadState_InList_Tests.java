package ru.lanit.ld.wc.tests.smoke;

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

        //lastURL=Сообщения/Входящая
        app.focusedUser.getUserApi().makeHomeAsLastUrl();
        //вид по умолчанию
        app.focusedUser.getUserApi().setViewState(app.defaultViewState, "Instruction", 1999);

        // порядковый номер сообщения в папке, над которым будем эксперементировать
        focusedInstructionNum = 0;

        //получаем ID этого сообщения и делаем его непрочитанным
        folderList = app.focusedUser.getUserApi().getFolderList(1999, focusedInstructionNum + 1);
        app.focusedUser.getUserApi().setReaded(false, folderList.items.get(focusedInstructionNum + 1).getInstructionId());

        // авторизуемся
        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(app.focusedUser);

    }

    @DataProvider
    public Object[][] Object() {

        return new Object[][]{
                {app.focusedUser.getUserApi().getInstruction(folderList.items.get(focusedInstructionNum).getInstructionId())},
        };
    }

    @Test(dataProvider = "Object", priority = 1, description = "Непрочитанное сообщение.Сделать прочитанным")
    public void setReaded(Instruction focusedInstruction) {

        //ElementsCollection menu = instSection.cardView.clickActionsMenu(focusedInstructionNum);
        //       Assert.assertTrue(menu.filter(Condition.text("Пометить непрочитаным")).size()==1);

         instSection.cardView.ActionsMenu(focusedInstructionNum);
    }


    @AfterClass
    public void after() {

    }


}
