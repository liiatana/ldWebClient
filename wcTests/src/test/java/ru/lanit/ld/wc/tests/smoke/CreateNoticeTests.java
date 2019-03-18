package ru.lanit.ld.wc.tests.smoke;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.FolderList;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.Users;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.NewInstructionPage;
import ru.lanit.ld.wc.tests.TestBase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.sleep;

public class CreateNoticeTests extends TestBase {

    InstructionsSection instSection;
    UserInfo instructionInitiator ; // app.UserList.anyUser(1); //или любой
    Users instructionReceivers ;// получатель = любые пользователи (число = кол-во получателей)(обязательный)
    Instruction sendedInstruction,newInstruction;

    @BeforeClass
    public void before() {

        instructionInitiator = app.focusedUser; // app.UserList.anyUser(1); //или любой
        instructionReceivers =app.UserList.anyUser(2);// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(instructionInitiator);
        instSection.ActionPanel.setViewState("Off",false,"Дата создания",true);

        newInstruction = new Instruction(instructionInitiator.getUserTypes().getAnyNoticeType());
        newInstruction
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Пример уведомления. Создано в UI: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))) // текст сообщения + тек.время. Если не задано по умолчанию = текст из типа сообщения.
                .withComment("Без комментариев...") // комментарий. Если не задано по умолчанию = не заполнено
                .withSubject("UI автотест") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                .withReceiverID(instructionReceivers.Ids());// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        NewInstructionPage newInstructionPage = instSection.ActionPanel.createNewByPlusButton(newInstruction, app);
        sleep(5000);

        newInstructionPage.fillForm(newInstruction, app, true);
        newInstructionPage.sendButton.click();
        //sleep(4000);

        instSection.goToFolder(2101);

        FolderList fl=instructionInitiator.getUserApi().getFolderList(2101,10);
        sendedInstruction=fl.items.get(0);

        sendedInstruction=instructionInitiator.getUserApi().getInstruction(sendedInstruction.getInstructionId());
        logger.info("instruction : " + newInstruction.toString());

        //sleep(2000);

    }

    @DataProvider
    public Object[][] Notice() {

        return new Object[][]{new Object[]{newInstruction}};
    }


    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить текст сообщения")
    public void apiNoticeText(Instruction newInstruction) {

        Assert.assertEquals(sendedInstruction.getText(),newInstruction.getText());
    }


    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить комментарий")
    public void apiNoticeComment(Instruction newInstruction) {

        Assert.assertEquals(sendedInstruction.getComment(),newInstruction.getComment());
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить тему")
    public void apiNoticeSubject(Instruction newInstruction) {

        Assert.assertEquals(sendedInstruction.getSubject(),newInstruction.getSubject());
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить инициатора")
    public void apiInitiator(Instruction newInstruction) {

        Assert.assertEquals(sendedInstruction.getInitiatorID(),newInstruction.getInitiatorID());
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить получателя")
    public void apiReceiver(Instruction newInstruction) {

        Assert.assertEquals(sendedInstruction.getReceiverID(),newInstruction.getReceiverID());
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить ID тип уведомления")
    public void apiInstructionTypeId(Instruction newInstruction) {

        Assert.assertEquals(sendedInstruction.getInstructionTypeId(),newInstruction.getInstructionTypeId());
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить ID статуса")
    public void apiInstructionState(Instruction newInstruction) {

        Assert.assertEquals(sendedInstruction.getState(),2);
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить название статуса")
    public void apiInstructionStateName(Instruction newInstruction) {

        Assert.assertEquals(sendedInstruction.getStateName(),"Активно");
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "aaaaaaaaaaaaaaaaaaaa")
    public void qqqqqqqqqqqqqqqqqqqqqqqq(Instruction newInstruction) {

        Assert.assertEquals(instSection.cardView.getTypeName(0), newInstruction.getInstructionType().getName());
    }

}
