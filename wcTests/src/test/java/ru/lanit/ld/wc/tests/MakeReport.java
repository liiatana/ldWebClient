package ru.lanit.ld.wc.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.*;

import java.time.LocalDateTime;
import java.util.Arrays;

public class MakeReport extends TestBase {

    /*private instructionType type=new instructionType();
    private Instruction instr;
    private Users instReceivers = new Users();

    private UserInfo instInitiator = new UserInfo();*/
    private instResponse instResponse = new instResponse();

    @DataProvider
    public Object[][] Reports() {

        instructionType type = app.InstructionList.getAnyTaskType(true); // если withClericalType=true, то тип с ДПО; если false= любой контрольный тип

        Instruction instr = new Instruction(type);
        instr
                .withInitiatorID(new int[]{app.focusedUser.getId()}) //отправитель=focusedUser (обязательный)
                //.withText("Контрольное сообщение, текст") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                .withComment("Ваш комментарий ...") // комментарий. Если не задано по умолчанию = не заполнено
                //.withSubject("Ваша тема...") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                //.withExecAuditorID(app.UserList.anyUser(1).Ids()) // контролер=любой один пользователь. Если не задано , то по умолчанию контроль выключен
                .setWithExecutive(true) // ответственный исполнитель=Да(true). По умолчанию = false.
                .setReportToExecutive(true) // отчеты ответственному исполнителю=Да. По умолчанию = false.
                //.withSendType(1) // 0= паралелльная (веер)( по умолчанию), 1=последовательная (цепочка)
                //.withReportReceiverID(app.UserList.anyUser(1).Ids()) // получаетль отчета=любой пользователь. Если не задано получатель отчета= инициатору.
                .withExecutionDate(LocalDateTime.of(2019, 1, 26, 17, 00), 1)
                .withReceiverID(app.UserList.anyUser(1).Ids());// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        instResponse = app.focusedUser.getUserApi().send(instr);
        instr.withInstructionId(instResponse.getInstructionId());

        logger.info("instruction : " + instr.toString());

        Reports newReports = new Reports(instr);

        return new Object[][]{new Object[]{newReports}};
    }

    @Test(dataProvider = "Reports")
    public void createReportProject(Reports newReports) {

        //Reports newReports = new Reports(instr);

        int reportNumber=0;

        newReports.reports.get(reportNumber)
                .withText("Текст моего отчета будет вот такой")
                .withComment("Мой коммент к отчету, отличный от коммента по умолчанию")
                .withSubject("И вот тема, отличная от темы по умолчанию");

        //logger.info("report : " +  newReports.reports.get(reportNumber).toString());

        UserInfo reportIitiator= app.UserList.getUserById(newReports.reports.get(reportNumber).getInitiatorID());

        reportResponse responseReport = reportIitiator.getUserApi().createReportProject(newReports.reports.get(reportNumber)) ;
        logger.info("response : " +  responseReport.toString());

        Assert.assertEquals(responseReport.message, "");
        Assert.assertTrue(responseReport.reportId>0);



    }
}

