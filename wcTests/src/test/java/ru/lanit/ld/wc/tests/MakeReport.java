package ru.lanit.ld.wc.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.*;

import java.time.LocalDateTime;

public class MakeReport extends TestBase {

    /*private instructionType type=new instructionType();
    private Instruction instr;
    private Users instReceivers = new Users();

    private UserInfo instInitiator = new UserInfo();*/
    private instResponse instResponse = new instResponse();

    @DataProvider
    public Object[][] Reports() {

        UserInfo instructionInitiator = app.focusedUser; // app.UserList.anyUser(1);

        instructionType type = instructionInitiator.getUserTypes().getAnyTaskType(true);
        // если withClericalType=true, то тип с ДПО; если false= без ДПО; если без прараметра - то все контрольные типы

        Instruction instr = new Instruction(type);
        instr
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                //.withText("Контрольное сообщение, текст") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                .withComment("Ваш комментарий ...") // комментарий. Если не задано по умолчанию = не заполнено
                //.withSubject("Ваша тема...") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                //.withExecAuditorID(app.UserList.anyUser(1).Ids()) // контролер=любой один пользователь. Если не задано , то по умолчанию контроль выключен
                .setWithExecutive(true) // ответственный исполнитель=Да(true). По умолчанию = false.
                .setReportToExecutive(true) // отчеты ответственному исполнителю=Да. По умолчанию = false.
                //.withSendType(1) // 0= паралелльная (веер)( по умолчанию), 1=последовательная (цепочка)
                //.withReportReceiverID(app.UserList.anyUser(1).Ids()) // получаетль отчета=любой пользователь. Если не задано получатель отчета= инициатору.
                .withExecutionDate(LocalDateTime.of(2019, 10, 26, 17, 00), 1)// execIntervalType=1- в календарных, 0- в рабочих
                .withReceiverID(app.UserList.anyUser(1).Ids());// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        instResponse = instructionInitiator.getUserApi().createInstruction(instr, true);
        instr.withInstructionId(instResponse.getInstructionId());

        logger.info("instruction : " + instr.toString());

        Reports newReports = new Reports(instr);

        return new Object[][]{new Object[]{newReports}};
    }

    @Test(dataProvider = "Reports")
    public void makeReport(Reports newReports) {

        int reportNumber = 0;
        newReports.reports.get(reportNumber)

                //.withComment("Мой коммент к отчету, отличный от коммента по умолчанию") // если не задать, то комментарий отчета=комментарию исходного сообщения
                //.withSubject("И вот тема, отличная от темы по умолчанию")// если не задать, то тема отчета="Отчет:"+ тема исходного
                //.withCompletionTypeId(false),// если true- позитивный( это значение по умолчанию), false = отчет с отказом
                .withText("Текст моего отчета будет вот такой"); //Если текст отчета не задать, то по умолчанию текст отчета ,будет пустой

        //logger.info("report : " +  newReports.reports.get(reportNumber).toString());

        UserInfo reportIitiator = app.UserList.getUserById(newReports.reports.get(reportNumber).getInitiatorID());

        reportResponse responseReport = reportIitiator.getUserApi().createReport(newReports.reports.get(reportNumber), true);
        logger.info("response : " + responseReport.toString());

        Assert.assertEquals(responseReport.message, "");
        Assert.assertTrue(responseReport.reportId > 0);
    }

}

