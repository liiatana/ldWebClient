package ru.lanit.ld.wc.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.appmanager.RestApiHelper;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.instResponse;
import ru.lanit.ld.wc.model.instructionType;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateInstructionProject extends TestBase {

    @DataProvider
    public Object[][] Notice() {
        UserInfo instructionInitiator= app.focusedUser; // app.UserList.anyUser(1); //или любой
        Instruction instr = new Instruction(instructionInitiator.getUserTypes().getAnyNoticeType());
        instr
                .withInitiatorID(new int[] {instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                //.withText("Тест текст") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                //.withComment("Ваш комментарий") // комментарий. Если не задано по умолчанию = не заполнено
                //.withSubject("Ваша тема") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                .withReceiverID(app.UserList.anyUser(2).Ids());// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        return new Object[][] {new Object[]{instr}};
    }

    @DataProvider
    public Object[][] Task() {

        UserInfo instructionInitiator= app.focusedUser; // app.UserList.anyUser(1); //или любой
        instructionType type = instructionInitiator.getUserTypes().getAnyTaskType ();
        // если withClericalType=true, то тип с ДПО; если false= без ДПО; если без прараметра - то все контрольные типы

        Instruction instr = new Instruction(type);
        instr
                .withInitiatorID(new int[] {instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                //.withText("Контрольное сообщение, текст") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                //.withComment("Ваш комментарий ...") // комментарий. Если не задано по умолчанию = не заполнено
                //.withSubject("Ваша тема...") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                //.withExecAuditorID(app.UserList.anyUser(1).Ids()) // контролер=любой один пользователь. Если не задано , то по умолчанию контроль выключен
                //.setWithExecutive(true) // ответственный исполнитель=Да(true). По умолчанию = false.
                //.setReportToExecutive(true) // отчеты ответственному исполнителю=Да. По умолчанию = false.
                //.withSendType(1) // 0= паралелльная (веер)( по умолчанию), 1=последовательная (цепочка)
                //.withReportReceiverID(app.UserList.anyUser(1).Ids()) // получаетль отчета=любой пользователь. Если не задано,то получатель отчета= инициатору.
                .withExecutionDate( LocalDateTime.of(2019,5,06,17,00),1)// execIntervalType=1- в календарных, 0- в рабочих
                .withReceiverID(app.UserList.anyUser(1).Ids());// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        return new Object[][] {new Object[]{instr}};
    }


    @Test(dataProvider = "Task", invocationCount = 1)
    public void createInstructionProject(Instruction newInstruction) {

        /*Instruction instr = new Instruction(app.InstructionList.getAnyTaskType(false));
        instr.withInitiatorID(new int[] {app.focusedUser.getId()})
             .withReceiverID(app.UserList.anyUser(3).Ids());*/

        //instResponse response =    app.focusedUser.getUserApi().instructionSavePrj(newInstruction);

        instResponse response=app.UserList.getUserById(newInstruction.getInitiatorID()).getUserApi().instructionSavePrj(newInstruction);

        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId>0);
    }


}
