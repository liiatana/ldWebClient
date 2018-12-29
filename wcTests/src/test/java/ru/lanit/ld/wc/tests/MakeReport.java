package ru.lanit.ld.wc.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.Users;
import ru.lanit.ld.wc.model.instResponse;
import ru.lanit.ld.wc.model.instructionType;

import java.time.LocalDateTime;

public class MakeReport extends TestBase {

    private instructionType type=new instructionType();
    private Instruction instr;
    private Users receivers= new Users();
    private instResponse response=new instResponse();

    @BeforeTest
    public void Task() {

        type = app.InstructionList.getAnyTaskType(true); // если withClericalType=true, то тип с ДПО; если false= любой контрольный тип
        receivers=app.UserList.anyUser(2); // получатель = любые пользователи (число = кол-во получателей)(обязательный)
        instr = new Instruction(type);
        instr
                .withInitiatorID(new int[] {app.focusedUser.getId()}) //отправитель=focusedUser (обязательный)
                //.withText("Контрольное сообщение, текст") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                //.withComment("Ваш комментарий ...") // комментарий. Если не задано по умолчанию = не заполнено
                //.withSubject("Ваша тема...") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                //.withExecAuditorID(app.UserList.anyUser(1).Ids()) // контролер=любой один пользователь. Если не задано , то по умолчанию контроль выключен
                //.setWithExecutive(true) // ответственный исполнитель=Да(true). По умолчанию = false.
                //.setReportToExecutive(true) // отчеты ответственному исполнителю=Да. По умолчанию = false.
                //.withSendType(1) // 0= паралелльная (веер)( по умолчанию), 1=последовательная (цепочка)
                //.withReportReceiverID(app.UserList.anyUser(1).Ids()) // получаетль отчета=любой пользователь. Если не задано получатель отчета= инициатору.
                //.withExecutionDate( LocalDateTime.of(2019,1,06,17,00),1)
                .withReceiverID(receivers.Ids());

        response = app.focusedUser.getUserApi().send(instr);
    }


}
