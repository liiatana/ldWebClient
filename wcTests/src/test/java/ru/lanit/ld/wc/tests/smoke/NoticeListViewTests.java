package ru.lanit.ld.wc.tests.smoke;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.Users;
import ru.lanit.ld.wc.model.instResponse;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NoticeListViewTests extends TestBase {

    InstructionsSection instSection;
    UserInfo instructionInitiator; // app.UserList.anyUser(1); //или любой
    Users instructionReceivers;// получатель = любые пользователи (число = кол-во получателей)(обязательный)
    Instruction newInstruction;


    @BeforeClass
    public void before() {

        instructionInitiator = app.focusedUser; // app.UserList.anyUser(1); //или любой
        instructionReceivers = app.UserList.anyUser(1);// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        newInstruction = new Instruction(instructionInitiator.getUserTypes().getAnyNoticeType());
        newInstruction
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Пример уведомления. Создано: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))) // текст сообщения + тек.время. Если не задано по умолчанию = текст из типа сообщения.
                .withComment("Пример комментария!") // комментарий. Если не задано по умолчанию = не заполнено
                .withSubject("Автотест") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                .withReceiverID(instructionReceivers.Ids());// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        instResponse instResponse = instructionInitiator.getUserApi().createInstruction(newInstruction, true);

        Instruction instruction = instructionInitiator.getUserApi().getInstruction(instResponse.instructionId);


        newInstruction.withCreationDate(instruction.getCreationDate())
                .withState(instruction.getState())
                .withStateName(instruction.getStateName())
                .withInstructionId(instResponse.getInstructionId());

        logger.info("instruction : " + newInstruction.toString());

        LoginPage lp = new LoginPage();
        instSection = lp.open("login").LoginAs(instructionInitiator).goToFolder(2101);
        instSection.ActionPanel.setViewState("Off", false, "Дата создания", true);
        //sleep(2000);

    }

    @DataProvider
    public Object[][] Notice() {
        return new Object[][]{new Object[]{newInstruction}};
    }


    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить текст сообщения в режиме Список")
    public void instructionTextInList(Instruction newInstruction) {

        Assert.assertEquals(instSection.cardView.getInstructionText(0), newInstruction.getText());
    }


    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить текст сообщения во всплывающей подсказке в режиме Список")
    public void instructionPopUpTextInList(Instruction newInstruction) {

        Assert.assertEquals(instSection.cardView.getInstructionPopUpText(0), newInstruction.getText());
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить ФИО получателя в режиме Список")
    public void instructionReceiverNameInList(Instruction newInstruction) {

        Assert.assertEquals(instSection.cardView.getReceiverName(0), app.UserList.getUserById(newInstruction.getReceiverID()[0]).getUserName());
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить дату создания в режиме Список")
    public void instructionCreationDateInList(Instruction newInstruction) {

        Assert.assertEquals(instSection.cardView.getCreationDateAsLocalDate(0), newInstruction.getCreationDate());
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить тип сообщения в режиме Список")
    public void instructionTypeNameInList(Instruction newInstruction) {

        Assert.assertEquals(instSection.cardView.getTypeName(0), newInstruction.getInstructionType().getName());
    }


}
