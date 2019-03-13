package ru.lanit.ld.wc.tests.smoke;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Flaky;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.Users;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.NewInstructionPage;
import ru.lanit.ld.wc.tests.TestBase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.sleep;

public class InstructionTextTests extends TestBase {

    Instructions instSection;
    UserInfo instructionInitiator ; // app.UserList.anyUser(1); //или любой
    Users instructionReceivers ;// получатель = любые пользователи (число = кол-во получателей)(обязательный)

    @BeforeClass
    public void before() {

        instructionInitiator = app.focusedUser; // app.UserList.anyUser(1); //или любой
        instructionReceivers =app.UserList.anyUser(1);// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        LoginPage lp = new LoginPage();
        instSection = lp.open("#/login").LoginAs(instructionInitiator).goToFolder(2101);
        //sleep(2000);

    }

    @DataProvider
    public Object[][] Notice() {



        //UserInfo instructionInitiator = app.focusedUser; // app.UserList.anyUser(1); //или любой
        Instruction newInstruction = new Instruction(instructionInitiator.getUserTypes().getAnyNoticeType());
        newInstruction
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Пример уведомления. Создано: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))) // текст сообщения + тек.время. Если не задано по умолчанию = текст из типа сообщения.
                //.withComment("Ваш комментарий") // комментарий. Если не задано по умолчанию = не заполнено
                //.withSubject("Ваша тема") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                .withReceiverID(instructionReceivers.Ids());// получатель = любые пользователи (число = кол-во получателей)(обязательный)


        NewInstructionPage newInstructionPage = instSection.ActionPanel.createNewByPlusButton(newInstruction, app);
        sleep(5000);

        newInstructionPage.fillForm(newInstruction, app, true);
        newInstructionPage.sendButton.click();
        sleep(4000);

        return new Object[][]{new Object[]{newInstruction}};
    }


    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить текст сообщения в режиме Список")
    public void instructionTextInList(Instruction newInstruction) {

        instSection.goToFolder(2101);
        instSection.ActionPanel.PreviewIs("Off");
        instSection.ActionPanel.viewOnlyNew(false);
        instSection.ActionPanel.sortBy("Дата создания", true);

        logger.info(instSection.cardView.getInstructionText(0));
        Assert.assertEquals(instSection.cardView.getInstructionText(0),newInstruction.getText());

    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Проверить текст сообщения во всплывающей подсказке в режиме Список")
    public void instructionPopUpTextInList(Instruction newInstruction) {

        instSection.goToFolder(2101);

        instSection.ActionPanel.PreviewIs("Off");
        instSection.ActionPanel.viewOnlyNew(false);
        instSection.ActionPanel.sortBy("Дата создания", true);

        logger.info(instSection.cardView.getInstructionText(0));
        Assert.assertEquals(instSection.cardView.getInstructionPopUpText(0),newInstruction.getText());

    }


}
