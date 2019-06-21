package ru.lanit.ld.wc.tests.smoke.iMake_reports;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.*;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class iMakeReport_WithoutForm_ReportInListView_Tests extends TestBase {

    InstructionsSection instSection;
    Instruction instr1, instr2, focusInstructionNewState, reportInstruction;

    UserInfo instructionInitiator, instructionReceiver;

    @BeforeClass
    public void before() {

        instructionReceiver = app.focusedUser;

        //установить вид по умолчанию для папок
        instructionReceiver.getUserApi().setViewState(app.defaultViewState, "Instruction", 1999);
        instructionReceiver.getUserApi().setViewState(app.defaultViewState, "Instruction", 2101);

        // авторизация
        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(instructionReceiver);

    }

    @DataProvider
    public Object[][] TaskWithoutCheck() {

        instructionInitiator = app.UserList.anyUserExcept(1, instructionReceiver).users.get(0); // инициатор

        // отправить сообщение для создания положительного отчета
        instructionType type_positive = instructionInitiator.getUserTypes().getControlTypeWithoutCheck(true);
        instr1 = new Instruction(type_positive);
        instr1
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Для теста положительного отчета без открытия формы") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                .withReceiverID(new int[]{instructionReceiver.getId()});// получатель = наш фокусный пользователь

        instResponse instResponse = instructionInitiator.getUserApi().createInstruction(instr1, true);
        instr1.withInstructionId(instResponse.getInstructionId())
                .withStartDate(instructionReceiver.getUserApi().getInstruction(instResponse.getInstructionId()).getCreationDate())
                .withStateName(instructionReceiver.getUserApi().getInstruction(instResponse.getInstructionId()).getStateName());


        // отправить сообщение для создания отчета с отказом
        instructionType type_negative = instructionInitiator.getUserTypes().getControlTypeWithoutCheck(false);
        instr2 = new Instruction(type_negative);
        instr2
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Для теста отчета с отказом без открытия формы") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                .withReceiverID(new int[]{instructionReceiver.getId()});// получатель = наш фокусный пользователь
        instResponse = instructionInitiator.getUserApi().createInstruction(instr2, true);
        instr2.withInstructionId(instResponse.getInstructionId())
                .withStartDate(instructionReceiver.getUserApi().getInstruction(instResponse.getInstructionId()).getCreationDate())
                .withStateName(instructionReceiver.getUserApi().getInstruction(instResponse.getInstructionId()).getStateName());


        return new Object[][]{
                {instr2, false, "Подготовлен (с отказом)"},
                {instr1, true, "Подготовлен (успешно)"}};
    }

    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне НЕ подтвердил отправку отчета. Проверка, что отчета в папке Исходящая")
    public void MakeReport_checkReportInOutcomingList(Instruction focusedInstruction, boolean reportType, String expectedStatus) {

        //перейтив папку Входящая
        instSection.SideBar.goTo("mail", "Входящая", "");

        //Нажать кнопку Отчитаться/Отказать и подтвердить отправку отчета
        instSection.clickOnReportButton(focusedInstruction, reportType, app);



        //ожидаемый отчет
        Instruction expectedReport=new Instruction();
        expectedReport.withReceiverID(new int[]{ focusedInstruction.getReportReceiverID()})
                .withStateName(expectedStatus)
                .withInstructionType(instructionReceiver.getUserTypes().getInstructionTypeIdByName( "Отчет"))
                .withCreationDate(instructionReceiver.getUserApi().getInstruction(instructionReceiver.getUserApi().getInstruction(focusedInstruction.getInstructionId()).getReport().getReportId()).getCreationDate());

        //перейти в папку Исходящая и считать данные по отчету из списка
        instSection.SideBar.goTo("mail", "Исходящая", "");
        Instruction report = instSection.cardView.getIstructionInfo(0, app, instructionInitiator, false);

        //сравнить предполагаемый отчет с фактическим
        assertThat(report, equalTo(expectedReport));
    }

    @AfterClass
    public void afterClass() {
        //lastURL=Сообщения/Входящая
        instructionReceiver.getUserApi().makeHomeAsLastUrl();

    }
}
