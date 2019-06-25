package ru.lanit.ld.wc.tests.smoke.iMake_reports;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.instructionType;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class iMakeReport_WithoutForm_ReportInListView_Tests extends TestBase {

    private InstructionsSection instSection;
    private Instruction instr;
    private List<Instruction> instrList;

    private UserInfo instructionInitiator,instructionReceiver;

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

        instructionInitiator = app.UserList.anyUserExcept(1,instructionReceiver).users.get(0); // инициатор

        List<instructionType> typeList = new ArrayList<instructionType>();
        typeList.add(instructionInitiator.getUserTypes().getControlTypeWithoutCheck(true)); // задание, для которого не производится проверка на кнопку Отчитаться
        typeList.add(instructionInitiator.getUserTypes().getControlTypeWithoutCheck(false)); // задание, для которого нет проверки на кнопку Отказать

        instrList = new ArrayList<Instruction>();

        for (int i = 0; i <= typeList.size() - 1; i++) {
            instr =new Instruction(typeList.get(i));
            instr
                    .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                    .withText( String.format("Сообщение %s для проверки создания отчета без открытия формы: %s",i,typeList.get(i).getName())) // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                    .withReceiverID(new int[]{instructionReceiver.getId()});// получатель = наш фокусный пользователь

            instr=instructionReceiver.getUserApi().getInstruction(instructionInitiator.getUserApi().createInstruction(instr, true).getInstructionId());
            instrList.add(instr);

        }


        return new Object[][]{
                {instrList.get(1), false, "Подготовлен (с отказом)"},
                {instrList.get(0), true, "Подготовлен (успешно)"}};
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
