package ru.lanit.ld.wc.tests.smoke.iMake_reports;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.*;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class iMakeReport_WithoutForm_ListView_Tests extends TestBase {

    private InstructionsSection instSection;
    private Instruction focusInstructionNewState,reportInstruction,instr;
    private List<Instruction> instrList;

    private UserInfo instructionInitiator,instructionReceiver;

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

        List<instructionType> typeList = new ArrayList<instructionType>();
        typeList.add(instructionInitiator.getUserTypes().getControlTypeWithoutCheck(true)); // задание, для которого не производится проверка на кнопку Отчитаться
        typeList.add(instructionInitiator.getUserTypes().getControlTypeWithoutCheck(false)); // задание, для которого нет проверки на кнопку Отказать

        instrList = new ArrayList<Instruction>();

        for (int i = 0; i <= typeList.size() - 1; i++) {
            instr =new Instruction(typeList.get(i));
            instr
                    .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                    .withText( String.format("Сообщение %s для проверки создания отчета без открытия формы",i)) // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                    .withReceiverID(new int[]{instructionReceiver.getId()});// получатель = наш фокусный пользователь

            instr=instructionReceiver.getUserApi().getInstruction(instructionInitiator.getUserApi().createInstruction(instr, true).getInstructionId());
            instrList.add(instr);

        }

        return new Object[][] {
                {instrList.get(1), false, "Завершено с отказом"},
                {instrList.get(0), true,"Завершено успешно"} };
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
            "а затем в диалоговом окне подтвердил отправку отчета. Проверка текущей папки исходного сообщения")
    public void MakeReport_checkInstructionNewFolder(Instruction focusedInstruction, boolean reportType, String expectedResult) {

        //Нажать кнопку Отчитаться/Отказать и подтвердить отправку отчета
        instSection.clickOnReportButton(focusedInstruction, reportType,app);

        //получить новое состояние сообщения
        focusInstructionNewState=instructionReceiver.getUserApi().getInstruction(focusedInstruction.getInstructionId());

        //проверка, что сообщение перемещено в Архив/Входящая
        assertThat(focusInstructionNewState.getFolder(), equalTo(new instructionFolder(2107,"Входящая")));

    }


    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне подтвердил отправку отчета. Проверка текущей папки созданного отчета")
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

    @Test(dataProvider = "TaskWithoutCheck", priority = 1, description = "Сценарий: пользователь нажал кнопку Отчитаться/Отказать, " +
            "а затем в диалоговом окне НЕ подтвердил отправку отчета. Проверка, что сообщение уже выкушено")
    public void MakeReport_checkInstructionInList(Instruction focusedInstruction, boolean reportType, String expectedResult) {

        //Нажать кнопку Отчитаться/Отказать и подтвердить отправку отчета
        int i = instSection.clickOnReportButton(focusedInstruction, reportType, app);

        //считать сообщение из списка
        Instruction newInstruction= instSection.cardView.getIstructionInfo(i,app,instructionInitiator,true);

        //сравнить исходный и текущий объект
        assertThat(newInstruction,not (focusedInstruction.getOnlyListViewInformation(true)));

    }







}
