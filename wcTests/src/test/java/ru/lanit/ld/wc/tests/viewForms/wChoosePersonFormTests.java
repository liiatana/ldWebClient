package ru.lanit.ld.wc.tests.viewForms;

import io.qameta.allure.Flaky;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.instructionType;
import ru.lanit.ld.wc.pages.ChoosePersonForm;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.NewInstructionPage;
import ru.lanit.ld.wc.tests.TestBase;

import static com.codeborne.selenide.Selenide.sleep;

public class wChoosePersonFormTests extends TestBase {
    Instructions inst;


    @BeforeClass
    public void before() {

        LoginPage lp = new LoginPage();
        inst = lp.open("#/login").LoginAs(app.focusedUser).goToFolder(1999);
        //sleep(6000);

    }

    @DataProvider
    public Object[][] Notice() {
        UserInfo instructionInitiator = app.focusedUser; // app.UserList.anyUser(1); //или любой
        Instruction instr = new Instruction(instructionInitiator.getUserTypes().getAnyNoticeType());
        instr
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Сообщение создано для проверки формы выбора получателей через форму") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения.
                .withComment("Без комментариев") // комментарий. Если не задано по умолчанию = не заполнено
                //.withSubject("Ваша тема") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                .withReceiverID(app.UserList.anyUser(3).Ids());// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        return new Object[][]{new Object[]{instr}};
    }

    @Test(dataProvider = "Notice", invocationCount = 1, description = "Выюор нескольких получателей через форму ChoosePerson")
    public void personsManipulation(Instruction newInstruction) {


        NewInstructionPage newP = inst.Header.CreateButtonClick(newInstruction, app);

        ChoosePersonForm form;
        form = newP.openChoosePerson();

        form.insertPersons(newInstruction.getReceiverID(), app);
        sleep(2000);

        form = newP.openChoosePerson();
        form.clearAllPersons();
        sleep(2000);

    }


}
