package ru.lanit.ld.wc.tests.viewForms;

import io.qameta.allure.Flaky;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.instructionType;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.NewInstructionPage;
import ru.lanit.ld.wc.tests.TestBase;

import static com.codeborne.selenide.Selenide.sleep;

public class NewInstructionTests extends TestBase {
    InstructionsSection inst;


    @BeforeClass
    public void before() {

        app.focusedUser.getUserApi().makeHomeAsLastUrl();
        LoginPage lp = new LoginPage();
        inst = lp.open().LoginAs(app.focusedUser);
        sleep(6000);

    }

    @DataProvider
    public Object[][] Notice() {
        UserInfo instructionInitiator= app.focusedUser; // app.UserList.anyUser(1); //или любой
        Instruction instr = new Instruction(instructionInitiator.getUserTypes().getAnyNoticeType());
        instr
                .withInitiatorID(new int[] {instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Пример текста сообщения") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения.
                .withComment("Ваш комментарий") // комментарий. Если не задано по умолчанию = не заполнено
                .withSubject("Ваша тема") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                .withReceiverID(app.UserList.anyUser(2).Ids());// получатель = любые пользователи (число = кол-во получателей)(обязательный)


        return new Object[][] {new Object[]{instr}};
    }

    @Flaky
    @Test(dataProvider = "Notice", invocationCount = 1,description = "Сохранить проект уведомления")
    public void saveProject(Instruction newInstruction) {


        NewInstructionPage newP=inst.ActionPanel.createNewByPlusButton(newInstruction,app);
        sleep(10000);// нужно сделать УМНОЕ ожидание

        newP.fillForm(newInstruction,app,false);
        newP.saveProjectButton.click();
        sleep(10000); // нужно сделать УМНОЕ ожидание

        newP.dialog.buttonOK.click();

        sleep(8000); //// нужно сделать УМНОЕ ожидание

        newP.cancelButton.click();

    }

    @DataProvider
    public Object[][] Task() {

        UserInfo instructionInitiator= app.focusedUser; // app.UserList.anyUser(1); //или любой
        instructionType type = instructionInitiator.getUserTypes().getAnyTaskType ();
        // если withClericalType=true, то тип с ДПО; если false= без ДПО; если без прараметра - то все контрольные типы

        Instruction instr = new Instruction(type);
        instr
                .withInitiatorID(new int[] {instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Контрольное сообщение, текст") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения.
                .withComment("Ваш комментарий к заданию...") // комментарий. Если не задано по умолчанию = не заполнено
                .withSubject("Срочно выполните!") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                .withExecAuditorID(app.UserList.anyUser(1).Ids()) // контролер=любой один пользователь. Если не задано , то по умолчанию контроль выключен
                              //.withExecAuditorID(new int[] {app.UserList.users.get(2).getId()})
                .setWithExecutive(true) // ответственный исполнитель=Да(true). По умолчанию = false.
                //.setReportToExecutive(true) // отчеты ответственному исполнителю=Да. По умолчанию = false.
                .withSendType(0) // 0= паралелльная (веер)( по умолчанию), 1=последовательная (цепочка)
                .withReportReceiverID(app.UserList.anyUser(1).Ids()) // получаетль отчета=любой пользователь. Если не задано,то получатель отчета= инициатору.
                //.withExecutionDate( LocalDateTime.of(2019,5,06,17,00),1)// execIntervalType=1- в календарных, 0- в рабочих
                .withReceiverID(new int[] {app.UserList.users.get(1).getId()});// получатель = здесь всегда второй
                                                            // получатель = любые пользователи (число = кол-во получателей)(обязательный)

        return new Object[][] {new Object[]{instr}};
    }

    @Flaky
    @Test(dataProvider = "Task", invocationCount = 1,description = "Отправить задание")
    public void sendInstruction(Instruction newInstruction) {

        NewInstructionPage newP=inst.Header.CreateButtonClick(newInstruction,app);
        sleep(2000);

        newP.fillForm(newInstruction,app,true);
        newP.sendButton.click();



    }

    @AfterClass
    public void after() {
        //inst.goToFolder(1999);
    }

}
