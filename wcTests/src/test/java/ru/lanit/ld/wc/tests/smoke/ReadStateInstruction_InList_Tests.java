package ru.lanit.ld.wc.tests.smoke;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.FolderList;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

public class ReadStateInstruction_InList_Tests extends TestBase {
    InstructionsSection instSection;
    FolderList folderList;
    //Instruction instruction;
    int focusedInstructionNum=0;// порядковый номер сообщения в папке, начиная с которого будем эксперементировать

    @BeforeClass
    public void before() {

        folderList = app.focusedUser.getUserApi().getFolderList(1999,focusedInstructionNum+2);

        //сообщение с порядковым номером=focusedInstructionNum делаем прочитанным
         app.focusedUser.getUserApi().setReaded(true,folderList.items.get(focusedInstructionNum).getInstructionId());

        //следующее за ним сообщение делаем непрочитанным
        app.focusedUser.getUserApi().setReaded(false,folderList.items.get(focusedInstructionNum+1).getInstructionId());

        //авторизоваться, перейти в папку Входящая
        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(app.focusedUser);// .goToFolder(1999);

        //установить режим отображения
        instSection.ActionPanel.setViewState("Off", false, "Дата создания", true);

    }

    @DataProvider
    public Object[][] Object() {

         return new Object[][] {
                 {app.focusedUser.getUserApi().getInstruction(folderList.items.get(focusedInstructionNum).getInstructionId()),focusedInstructionNum, "400", "Пометить как непрочитанное", false},
                 {app.focusedUser.getUserApi().getInstruction(folderList.items.get(focusedInstructionNum+1).getInstructionId()),focusedInstructionNum+1, "700", "Пометить как прочитанное", true},
         };
    }

    @Test(dataProvider = "Object", priority=3,description = "Проверка атрибута font-weight для текста сообщения")
    public void checkTextFont_InList(Instruction instr,int num, String expected_fontWeight, String expected_menuText, Boolean expected_redCircleState){

        instSection.cardView.getInstructionTextAsSE(num).shouldHave(Condition.cssValue("font-weight", expected_fontWeight));
    }

    @Test(dataProvider = "Object", priority = 3, description = "Проверка атрибута font-weight в поле Получатель/Отправитель ")
    public void checkReceiverFont_InList(Instruction instr,int num, String expected_fontWeight, String expected_menuText, Boolean expected_redCircleState) {

        instSection.cardView.getReceiverNameAsSE(num).shouldHave(Condition.cssValue("font-weight", expected_fontWeight));
    }

    @Test(dataProvider = "Object", priority = 3, description = "Проверка атрибута font-weight в поле Дата создания ")
    public void checkDateFont_InList(Instruction instr,int num, String expected_fontWeight, String expected_menuText, Boolean expected_redCircleState) {

        instSection.cardView.getCreationDateAsSE(num).shouldHave(Condition.cssValue("font-weight", expected_fontWeight));
    }

    @Test(dataProvider = "Object", priority = 3, description = "Проверка атрибута font-weight в поле Тип сообщения ")
    public void checkInstrTypeFont_InList(Instruction instr,int num, String expected_fontWeight, String expected_menuText, Boolean expected_redCircleState) {

        instSection.cardView.getInstructionTypeAsSe(num).shouldHave(Condition.cssValue("font-weight", expected_fontWeight));
    }

    @Test(dataProvider = "Object", priority = 3, description = "Проверка атрибута font-weight в поле Срок исполнения (при наличии такового)")
    public void checkInstrExecPeriodFont_InList(Instruction instr,int num, String expected_fontWeight, String expected_menuText, Boolean expected_redCircleState) {

        if (instr.getExecutionDate() == null) {
            throw new SkipException("Проверка атрибута font-weight в поле Срок исполнения пропущена, так как сообщение без срока исполнения ");
        } else {
            instSection.cardView.getExecutionDateAsSE(num).shouldHave(Condition.cssValue("font-weight", expected_fontWeight));
        }
    }

    @Test(dataProvider = "Object", priority = 3, description = "Проверка видимости признака Непрочитано(красного кружка)")
    public void checkInstrFlag_InList(Instruction instr,int num, String expected_fontWeight, String expected_menuText, Boolean expected_redCircleState) {

        Assert.assertEquals(instSection.cardView.getFlagState(num),expected_redCircleState);

    }


    @Test(dataProvider = "Object", priority = 3, description = "Проверка наличия обратной операции в меню")
    public void checkReverseOperationMenu_InList(Instruction instr,int num, String expected_fontWeight, String expected_menuText, Boolean expected_redCircleState) {

        Assert.assertEquals(instSection.cardView.ActionsMenu(num).filter(Condition.text(expected_menuText)).size(), 1);
        instSection.cardView.closeMenu(num);
    }

    @Test(dataProvider = "Object", priority = 3, description = "Проверка количества операций вида Пометить как *")
    public void checkMenuOperationsCount_InList(Instruction instr,int num, String expected_fontWeight, String expected_menuText, Boolean expected_redCircleState) {

        Assert.assertEquals(instSection.cardView.ActionsMenu(num).filter(Condition.text("Пометить как")).size(), 1);
        instSection.cardView.closeMenu(num);
    }


    @AfterClass
    public void after() {
    }


}
