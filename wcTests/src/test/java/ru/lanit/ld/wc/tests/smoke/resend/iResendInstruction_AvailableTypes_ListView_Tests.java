package ru.lanit.ld.wc.tests.smoke.resend;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.instructionType;
import ru.lanit.ld.wc.pages.InstructionsSection;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;



public class iResendInstruction_AvailableTypes_ListView_Tests extends TestBase {

    InstructionsSection instSection;
    Instruction instr;
    List<Instruction> instrList = new ArrayList<Instruction>();

    UserInfo instructionInitiator, instructionReceiver;

    @BeforeClass
    public void before() {

        instructionReceiver = app.focusedUser; //получатель сообщений
        instructionInitiator = app.UserList.anyUser(1).users.get(0); // инициатор

        List<instructionType> typeList = new ArrayList<instructionType>();
        typeList.add(instructionInitiator.getUserTypes().getAnyNoticeType()); // входящее уведомление для instructionReceiver
        typeList.add(instructionInitiator.getUserTypes().getAnyWithRedirectedAsControl(false)); // входящее задание для instructionReceiver, которое можно перенаправить только как контрольное
        typeList.add(instructionInitiator.getUserTypes().getAnyWithRedirectedAsControl(true)); // входящее задание для instructionReceiver, которое можно перенаправить только как контрольное

        for (int i = 0; i <= typeList.size() - 1; i++) {
            instr =new Instruction(typeList.get(i));
            instr
                    .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                    .withText( String.format("Сообщение %s ",i)) // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                    .withReceiverID(new int[]{instructionReceiver.getId()});// получатель = наш фокусный пользователь

            instr=instructionReceiver.getUserApi().getInstruction(instructionInitiator.getUserApi().createInstruction(instr, true).getInstructionId());
            instrList.add(instr);

        }

        //lastURL=Сообщения/Входящая
        app.focusedUser.getUserApi().makeHomeAsLastUrl();

        //установить вид по умолчанию для папок
        instructionReceiver.getUserApi().setViewState(app.defaultViewState, "Instruction", 1999);

        // авторизация
        LoginPage lp = new LoginPage();
        instSection = lp.open().LoginAs(instructionReceiver);

    }

    @DataProvider
    public Object[][] InstructionTypes() {

        return new Object[][]{
                {instrList.get(0), false},
                {instrList.get(1), false},
                {instrList.get(2), true}};
    }

    @Test(dataProvider = "InstructionTypes", priority = 1, description = "Перенаправить сообщение. Проверка доступных типов для перенаправления")
    public void resendInstruction_checkAvailableInstructionList(Instruction focusedInstruction, boolean isExpectedOnlyControlTypes) {

        //в меню выбрать действие "Перенаправить"
        instSection.cardView.ResendButtonClick( instSection.getFolderNumInList(focusedInstruction,app) ) ;

        //сравнить предполагаемый отчет с фактическим
        assertThat(instSection.objectTypes_Dialog.getTypesList() , equalTo(instructionReceiver.getUserTypes().getInstructionTypesListAsString(isExpectedOnlyControlTypes)));

    }

    @AfterMethod() //@AfterMethod //после каждого меnода
    public void closeobjectTypes_Dialog() {
        instSection.objectTypes_Dialog.close();
    }

}
