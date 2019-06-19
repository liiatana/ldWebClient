package ru.lanit.ld.wc.tests.smoke.make_reports;

import io.qameta.allure.Step;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.*;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import static com.codeborne.selenide.Selenide.sleep;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class iMakeReport_WithoutForm_ListView_Tests extends TestBase {

    InstructionsSection instSection;
    FolderList folderList;
    Instruction instr1, instr2,focusInstructionNewState;
    //int focusedInstructionNum;
    UserInfo instructionInitiator;

    @BeforeClass
    public void before() {

        //lastURL=Сообщения/Входящая
        app.focusedUser.getUserApi().makeHomeAsLastUrl();
        //установить вид по умолчанию
        app.focusedUser.getUserApi().setViewState(app.defaultViewState, "Instruction", 1999);

        // авторизация
        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(app.focusedUser);

    }

    @DataProvider
    public Object[][] TaskWithoutCheck() {

        instructionInitiator = app.UserList.anyUser(1).users.get(0); // инициатор

        // отправить сообщение для создания положительного отчета
        instructionType type_positive = instructionInitiator.getUserTypes().getControlTypeWithoutCheck(true);
        instr1 = new Instruction(type_positive);
        instr1
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Для теста положительного отчета без открытия формы") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                .withReceiverID(new int[]{app.focusedUser.getId()});// получатель = наш фокусный пользователь

        instResponse instResponse = instructionInitiator.getUserApi().createInstruction(instr1, true);
        instr1.withInstructionId(instResponse.getInstructionId())
                .withStartDate(app.focusedUser.getUserApi().getInstruction(instResponse.getInstructionId()).getCreationDate())
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
                .withStartDate(app.focusedUser.getUserApi().getInstruction(instResponse.getInstructionId()).getCreationDate())
                .withStateName(app.focusedUser.getUserApi().getInstruction(instResponse.getInstructionId()).getStateName());

        return new Object[][] {
                {instr2, false, "Завершено с отказом"},
                {instr1, true,"Завершено успешно"} };
    }

    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне подтвердил отправку отчета. Проверка результата")
    public void MakeReport_checkResultState(Instruction focusedInstruction, boolean reportType, String expectedResult) {

        //обновить список папки
        instSection.ActionPanel.refreshList();

        //получить список сообщений папки
        folderList = app.focusedUser.getUserApi().getFolderList(1999, 10);

        //нажать кнопку Отчитаться/Отказать для сообщения
        instSection.cardView.quickReport(reportType,folderList.getInstructionNumInFolder(focusedInstruction.getInstructionId()));
        //в диалоговом окне нажать кнопку ОК
        instSection.Dialog.buttonOK.click();

        sleep(5000);

        //получить новое состояние сообщения
        focusInstructionNewState=app.focusedUser.getUserApi().getInstruction(focusedInstruction.getInstructionId());

        //проверить наличие отчета по сообщению


        String abc=new String(expectedResult);
        String fact=new String(focusInstructionNewState.getResult().trim());
        //Assert.assertTrue(focusInstructionNewState.getResult().trim().equals(expectedResult));
        assertThat(focusInstructionNewState.getResult().trim(), equalTo(abc));
        //assertThat(focusInstructionNewState.getResult().trim(), equalTo(expectedResult));
        assertThat(focusInstructionNewState.getResult().trim(), CoreMatchers.startsWith(expectedResult));
        //focusInstructionNewState.getResult().equals("Завершено с отказом")
        Assert.assertTrue(focusInstructionNewState.getResult().trim().equals(abc));

        Assert.assertTrue(focusInstructionNewState.getResult().trim().equals(new String(expectedResult)));
    }






}
