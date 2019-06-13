package ru.lanit.ld.wc.tests.smoke;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.lanit.ld.wc.model.FolderList;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import java.lang.reflect.Method;

import static com.codeborne.selenide.Selenide.sleep;

public class iReadUnreadAction_ListView_Tests extends TestBase {
    InstructionsSection instSection;
    FolderList folderList;
    Instruction instruction, focusInstructionNewState;
    int focusedInstructionNum;

    @BeforeClass
    public void before() {

        //lastURL=Сообщения/Входящая
        app.focusedUser.getUserApi().makeHomeAsLastUrl();
        //установить вид по умолчанию
        app.focusedUser.getUserApi().setViewState(app.defaultViewState, "Instruction", 1999);

        // порядковый номер сообщения в папке, над которым будем эксперементировать
        focusedInstructionNum = 1;

        //получить ID этого сообщения
        folderList = app.focusedUser.getUserApi().getFolderList(1999, focusedInstructionNum + 1);
        instruction=folderList.items.get(focusedInstructionNum);
        //app.focusedUser.getUserApi().setReaded(false, instruction.getInstructionId());

        // авторизация
        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(app.focusedUser);

    }

    @DataProvider
    public Object[][] Object() {

                return new Object[][] {
                    {false,instruction, "Пометить как прочитанное","Пометить как непрочитанное"},
                    {true,instruction, "Пометить как непрочитанное","Пометить как прочитанное"} };

    }

    @Test(dataProvider = "Object", priority = 1, description = "Проверка изменения permission после выполнения действия.")
    public void checkInstructionPermission(boolean readFlagState,Instruction focusedInstruction, String action, String expectedNewMenuItemName) {

        //бэк:сделать сообщение как указано во флаге readFlagState
        app.focusedUser.getUserApi().setReaded(readFlagState, focusedInstruction.getInstructionId());
        //обновить список папки
        instSection.ActionPanel.refreshList();


        //в меню выбрать действие "action"
        //ElementsCollection menu=instSection.cardView.ActionsMenu(focusedInstructionNum);
        // menu.last().click();//       .findBy((Condition.text(action)). // .get(0).click();
        instSection.cardView.ActionsMenuOpen(focusedInstructionNum,action);

        sleep(1000);

        // бэк:считать новое состояние сообщения
        focusInstructionNewState =app.focusedUser.getUserApi().getInstruction(focusedInstruction.getInstructionId());

        // проверить permission на новом состоянии
        Assert.assertTrue(focusInstructionNewState.getPermissions().isCanUnreadInstruction()!=readFlagState);
    }


    @Test(dataProvider = "Object", priority = 1, description = "Проверка изменения пункта меню после выполнения действия.")
    public void checkMenuItemName(boolean readFlagState,Instruction focusedInstruction, String action, String expectedNewMenuItemName) {

        //бэк:сделать сообщение как указано во флаге readFlagState
        app.focusedUser.getUserApi().setReaded(readFlagState, instruction.getInstructionId());
        //обновить список папки
        instSection.ActionPanel.refreshList();

        //в меню выбрать действие "action"
        instSection.cardView.ActionsMenu(focusedInstructionNum).filter(Condition.text(action)).get(0).click();

        // проверить, что заговловок меню сменился
        //Assert.assertTrue(instSection.cardView.ActionsMenu(focusedInstructionNum).filter(Condition.text(expectedNewMenuItemName)).size()==1);
        Assert.assertEquals(instSection.cardView.ActionsMenu(focusedInstructionNum).last().getText().trim(),expectedNewMenuItemName);

    }


    /*@AfterMethod(alwaysRun = true) //@AfterMethod //после каждого меnода
    public void closeMenu() {
        instSection.cardView.ActionsMenu(focusedInstructionNum);
    }*/

    @AfterClass
    public void after() {

    }


}
