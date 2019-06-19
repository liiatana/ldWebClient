package ru.lanit.ld.wc.tests.smoke.iMake_reports;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.*;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class iMakeReport_WithoutForm_ListView_Tests extends TestBase {

    InstructionsSection instSection;
    Instruction instr1, instr2,focusInstructionNewState,reportInstruction;

    UserInfo instructionInitiator,instructionReceiver;

    @BeforeClass
    public void before() {

        instructionReceiver=app.focusedUser;

        //lastURL=Сообщения/Входящая
        instructionReceiver.getUserApi().makeHomeAsLastUrl();
        //установить вид по умолчанию
        instructionReceiver.getUserApi().setViewState(app.defaultViewState, "Instruction", 1999);

        // авторизация
        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(instructionReceiver);

    }

    @DataProvider
    public Object[][] TaskWithoutCheck() {

        instructionInitiator = app.UserList.anyUserExcept(1,instructionReceiver).users.get(0); // инициатор

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

        return new Object[][] {
                {instr2, false, "Завершено с отказом"},
                {instr1, true,"Завершено успешно"} };
    }

    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне подтвердил отправку отчета. Проверка инфы об отчете у сообщения")
    public void MakeReport_checkReportInfoInInstruction(Instruction focusedInstruction, boolean reportType, String expectedResult) {

        //Нажать кнопку Отчитаться/Отказать и подтвердить отправку отчета
        instSection.clickOnReportButton(focusedInstruction, reportType,app);

        //получить новое состояние сообщения
        focusInstructionNewState=instructionReceiver.getUserApi().getInstruction(focusedInstruction.getInstructionId());

        //делаем так чтобы обнулить текст и тему, потому как в сообщении без отчета для них возвращается значение по умолчанию
        focusInstructionNewState.getReport().withSubject(null);
        focusInstructionNewState.getReport().withText(null);


        //объект=предполагаемый отчет
        Report expectedReportInfo= new Report();
        expectedReportInfo.withInitiatorID(instructionReceiver.getId())
                .withInstructionId(focusedInstruction.getInstructionId())
                .withReceiverId(focusedInstruction.getReportReceiverID())
                .withRepodtId(focusInstructionNewState.getReport().getReportId());

        // сравниваем фактический результат с ожидаемым
        assertThat(focusInstructionNewState.getReport(), equalTo(focusedInstruction.withReport(expectedReportInfo).getReport()));

    }

    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне подтвердил отправку отчета. Проверка текущей папки сообщения")
    public void MakeReport_checkInstructionNewFolder(Instruction focusedInstruction, boolean reportType, String expectedResult) {

        //Нажать кнопку Отчитаться/Отказать и подтвердить отправку отчета
        instSection.clickOnReportButton(focusedInstruction, reportType,app);

        //получить новое состояние сообщения
        focusInstructionNewState=instructionReceiver.getUserApi().getInstruction(focusedInstruction.getInstructionId());

        //проверка, что сообщение перемещено в Архив/Входящая
        assertThat(focusInstructionNewState.getFolder(), equalTo(new instructionFolder(2107,"Входящая")));

    }


    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне подтвердил отправку отчета. Проверка текущей папки сообщения")
    public void MakeReport_checkReportNewFolder(Instruction focusedInstruction, boolean reportType, String expectedResult) {

        //Нажать кнопку Отчитаться/Отказать и подтвердить отправку отчета
        instSection.clickOnReportButton(focusedInstruction, reportType,app);

        //получить новое состояние сообщения
        focusInstructionNewState=instructionReceiver.getUserApi().getInstruction(focusedInstruction.getInstructionId());

        //получить информацию об отчете
        reportInstruction=instructionReceiver.getUserApi().getInstruction(focusInstructionNewState.getReport().getReportId());

        //проверка, что отчет в папке Исходящая
        assertThat(reportInstruction.getFolder(), equalTo(new instructionFolder(2101,"Исходящая")));

    }



    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне подтвердил отправку отчета. Проверка результата")
    public void MakeReport_checkResultState(Instruction focusedInstruction, boolean reportType, String expectedResult) {

        //Нажать кнопку Отчитаться/Отказать и подтвердить отправку отчета
        instSection.clickOnReportButton(focusedInstruction, reportType,app);

        //получить новое состояние сообщения
        focusInstructionNewState=app.focusedUser.getUserApi().getInstruction(focusedInstruction.getInstructionId());

        //проверить статус исходного сообщения
        assertThat(focusInstructionNewState.getResult().trim(),equalTo(expectedResult));


    }




   /* private void clickOnReportButton(Instruction focusedInstruction, boolean reportType) {
        //обновить список папки
        instSection.ActionPanel.refreshList();

        //получить список сообщений папки
        folderList = instructionReceiver.getUserApi().getFolderList(1999, 10);

        //нажать кнопку Отчитаться/Отказать для сообщения
        instSection.cardView.quickReport(reportType, folderList.getInstructionNumInFolder(focusedInstruction.getInstructionId()));
        //в диалоговом окне нажать кнопку ОК
        instSection.Dialog.buttonOK.click();

        sleep(5000);
    }*/


}
