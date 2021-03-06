package ru.lanit.ld.wc.tests.smoke.iMake_reports;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.*;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class iMakeReport_WithoutForm_CancelReporting_ListView_Tests extends TestBase {

    InstructionsSection instSection;
    FolderList folderList;
    Instruction instr1, instr2;
    //int focusedInstructionNum;
    UserInfo instructionInitiator;

    @BeforeClass
    public void before() {

        //lastURL=Сообщения/Входящая
        app.focusedUser.getUserApi().makeHomeAsLastUrl();
        //установить вид по умолчанию
        app.focusedUser.getUserApi().setViewState(app.defaultViewState, "Instruction", 1999);

        //instructionInitiator = app.UserList.anyUser(1).users.get(0); // инициатор
        instructionInitiator = app.UserList.anyUserExcept(1,app.focusedUser).users.get(0);// инициатор

        // отправить сообщение для создания положительного отчета
        instructionType type_positive = instructionInitiator.getUserTypes().getControlTypeWithoutCheck(true);
        instr1 = new Instruction(type_positive);
        instr1
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Для теста положительного отчета без открытия формы") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                .withReceiverID(new int[]{app.focusedUser.getId()});// получатель = наш фокусный пользователь

        instResponse instResponse = instructionInitiator.getUserApi().createInstruction(instr1, true);
        instr1.withInstructionId(instResponse.getInstructionId())
                .withCreationDate(app.focusedUser.getUserApi().getInstruction(instResponse.getInstructionId()).getCreationDate())
                .withStateName(app.focusedUser.getUserApi().getInstruction(instResponse.getInstructionId()).getStateName());


        // отправить сообщение для создания отчета с отказом
        instructionType type_negative = instructionInitiator.getUserTypes().getControlTypeWithoutCheck(false);
        instr2 = new Instruction(type_negative);
        instr2
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Для теста отчета с отказом без открытия формы") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                .withReceiverID(new int[]{app.focusedUser.getId()});// получатель = наш фокусный пользователь
        instResponse = instructionInitiator.getUserApi().createInstruction(instr2, true);
        instr2.withInstructionId(instResponse.getInstructionId())
                .withCreationDate(app.focusedUser.getUserApi().getInstruction(instResponse.getInstructionId()).getCreationDate())
                .withStateName(app.focusedUser.getUserApi().getInstruction(instResponse.getInstructionId()).getStateName());

        // авторизация
        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(app.focusedUser);

    }

    @DataProvider
    public Object[][] TaskWithoutCheck() {

        return new Object[][] {
                {instr1, 1, true},
                {instr2, 0,false} };
    }

    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне НЕ подтвердил отправку отчета. Проверка наличия отчета")
    public void cancelMakeReport_checkResultState(Instruction focusedInstruction, int focusedInstructionNum, boolean reportType) {

        //нажать кнопку Отчитаться/Отказать
        instSection.cardView.quickReport(reportType,focusedInstructionNum);
        //в диалоговом окне нажать кнопку Отмена
        instSection.Dialog.buttonCancel.click();

        //получить новое состояние сообщения
        Instruction focusInstructionNewState=app.focusedUser.getUserApi().getInstruction(focusedInstruction.getInstructionId());

        //проверить result по сообщению
        Assert.assertTrue(StringUtils.length(focusInstructionNewState.getResult())==0);
    }

    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне НЕ подтвердил отправку отчета. Проверка статуса сообщения")
    public void cancelMakeReport_checkInstructionState(Instruction focusedInstruction, int focusedInstructionNum, boolean reportType) {

        //нажать кнопку Отчитаться/Отказать
        instSection.cardView.quickReport(reportType,focusedInstructionNum);
        //в диалоговом окне нажать кнопку Отмена
        instSection.Dialog.buttonCancel.click();

        //получить новое состояние сообщения
        Instruction focusInstructionNewState=app.focusedUser.getUserApi().getInstruction(focusedInstruction.getInstructionId());

        //проверить наличие отчета по сообщению
        Assert.assertEquals(focusInstructionNewState.getStateName(),"Активно" );

    }

    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне НЕ подтвердил отправку отчета. Проверка папки сообщения(бэк)")
    public void cancelMakeReport_checkFolder(Instruction focusedInstruction, int focusedInstructionNum, boolean reportType) {

        //нажать кнопку Отчитаться/Отказать
        instSection.cardView.quickReport(reportType,focusedInstructionNum);
        //в диалоговом окне нажать кнопку Отмена
        instSection.Dialog.buttonCancel.click();

        //получить новое состояние сообщения
        Instruction focusInstructionNewState=app.focusedUser.getUserApi().getInstruction(focusedInstruction.getInstructionId());

        //проверить папку сообщения
        Assert.assertEquals(focusInstructionNewState.getFolder(),new instructionFolder(1999,"Входящая"));
    }

    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне НЕ подтвердил отправку отчета. Проверка, что сообщение по прежнему отображается в списке")
    public void cancelMakeReport_checkInstructionInList(Instruction focusedInstruction, int focusedInstructionNum, boolean reportType) {

        //нажать кнопку Отчитаться/Отказать
        instSection.cardView.quickReport(reportType,focusedInstructionNum);
        //в диалоговом окне нажать кнопку Отмена
        instSection.Dialog.buttonCancel.click();

        //считать сообщение
        Instruction focusInstructionNewState= instSection.cardView.getIstructionInfo(focusedInstructionNum,app,instructionInitiator,true);

        //сравнить исходный и текущий объект
        Assert.assertEquals(focusInstructionNewState,focusedInstruction.getOnlyListViewInformation(true));
    }




}
