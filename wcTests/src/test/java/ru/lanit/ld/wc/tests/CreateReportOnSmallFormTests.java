package ru.lanit.ld.wc.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.*;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;

import static com.codeborne.selenide.Selenide.sleep;

public class CreateReportOnSmallFormTests extends TestBase {
    Instructions inst;

    @BeforeTest
    public void before() {

        LoginPage lp = new LoginPage(app);
        inst = lp.open("#/login").LoginAs(app.focusedUser);


    }

    @DataProvider
    public Object[][] Task() {

        UserInfo instructionInitiator = app.UserList.anyUser(1).users.get(0); // инициатор=любой чувак
        instructionType type = instructionInitiator.getUserTypes().getControlTypeWithTextCheck(false);
        // если withClericalType=true, то тип с ДПО; если false= без ДПО; если без прараметра - то все контрольные типы

        Instruction instr = new Instruction(type);
        instr
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Для теста отчета с краткой формы") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                //.withComment("Ваш комментарий ...") // комментарий. Если не задано по умолчанию = не заполнено
                //.withSubject("Ваша тема...") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                //.withExecAuditorID(app.UserList.anyUser(1).Ids()) // контролер=любой один пользователь. Если не задано , то по умолчанию контроль выключен
                //.setWithExecutive(true) // ответственный исполнитель=Да(true). По умолчанию = false.
                //.setReportToExecutive(true) // отчеты ответственному исполнителю=Да. По умолчанию = false.
                //.withSendType(1) // 0= паралелльная (веер)( по умолчанию), 1=последовательная (цепочка)
                //.withReportReceiverID(app.UserList.anyUser(1).Ids()) // получаетль отчета=любой пользователь. Если не задано,то получатель отчета= инициатору.
                //.withExecutionDate( LocalDateTime.of(2019,5,06,17,00),1)// execIntervalType=1- в календарных, 0- в рабочих
                .withReceiverID(new int[]{app.focusedUser.getId()});// получатель = наш фокусный пользователь

        instResponse instResponse = instructionInitiator.getUserApi().createInstruction(instr, true);
        instr.withInstructionId(instResponse.getInstructionId());

        logger.info("instruction : " + instr.toString());

        return new Object[][]{new Object[]{instr}};
    }

    @Test(dataProvider = "Task", invocationCount = 1)
    public void makeReportBySmallForm(Instruction instruction) {

        inst.goToFolder(1999);
        inst.ActionPanel.PreviewIs("Off");
        inst.ActionPanel.refreshButton.click();

        FolderList list = app.focusedUser.getUserApi().getFolderList(1999);
        int nnum = list.getInstructionNumInFolder(instruction.getInstructionId());

        inst.InstructionListWithoutPreview.get(nnum).$$x("/*//div[@class=\"quick-access\"]").get(1).click();
        sleep(3000);

        inst.SmallReport.clickButton(1);//1= полож,2= отказ,3=сохранить проект
        sleep(2000);

        
        inst.SmallReport.text.sendKeys("Вот текст отчета, чтоб не ругалось");
        sleep(3000);
        inst.SmallReport.clickButton(1);//1= полож,2= отказ,3=сохранить проект


    }
}
