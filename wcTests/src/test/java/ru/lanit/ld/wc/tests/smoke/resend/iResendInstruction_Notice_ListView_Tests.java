package ru.lanit.ld.wc.tests.smoke.resend;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.NewInstructionPage;
import ru.lanit.ld.wc.tests.TestBase;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class iResendInstruction_Notice_ListView_Tests extends TestBase {

    private InstructionsSection instSection;
    private Instruction instr;
    private List<Instruction> instrList;

    private UserInfo instructionInitiator, instructionReceiver;

    private  NewInstructionPage p= new NewInstructionPage();

    @BeforeClass
    public void before() {

        instructionReceiver = app.focusedUser; //получатель сообщений
        instructionInitiator = app.UserList.anyUserExcept(1, instructionReceiver).users.get(0); // инициатор=любой кроме ресеивера

         //lastURL=Сообщения/Входящая
        instructionReceiver.getUserApi().makeHomeAsLastUrl();

        //установить вид по умолчанию для папок
        instructionReceiver.getUserApi().setViewState(app.defaultViewState, "Instruction", 1999);

        // авторизация
        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(instructionReceiver);



    }

    @DataProvider
    public Object[][] InstructionTypes() {

        instrList=new ArrayList<>();

        for (int i = 0; i <= 2; i++) {
            instr = new Instruction(instructionInitiator.getUserTypes().getAnyNoticeType());
            instr
                    .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель
                    //  .withText(String.format("Уведомление %s ", i)) // текст сообщения
                    .withReceiverID(new int[]{instructionReceiver.getId()});// получатель

            instr = instructionReceiver.getUserApi().getInstruction(instructionInitiator.getUserApi().createInstruction(instr, true).getInstructionId());
            instrList.add(instr);
        }

        return new Object[][]{
                {instrList.get(0), false},
                {instrList.get(1), true}};
    }

    @Test(dataProvider = "InstructionTypes", priority = 1, description = "Перенаправить сообщение. Проверка доступных типов для перенаправления")
    public void resendNotice_check(Instruction focusedInstruction, boolean instructionTypeFlag) {

        //в меню выбрать действие "Перенаправить"
        instSection.cardView.ResendButtonClick(instSection.getFolderNumInList(focusedInstruction, app));

        //выбрать тип сообщения
        p=instSection.objectTypes_Dialog.chooseType( instructionInitiator.getUserTypes().getType(instructionTypeFlag));

        //проверить, что открыта страница заданного типа по URL
        assertThat(com.codeborne.selenide.WebDriverRunner.url().toString(), equalTo( String.format("%s/message/new/%s",app.baseUrl,focusedInstruction.getInstructionType())));

    }
}
