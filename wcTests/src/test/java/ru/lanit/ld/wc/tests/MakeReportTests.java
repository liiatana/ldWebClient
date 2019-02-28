package ru.lanit.ld.wc.tests;

import org.testng.annotations.*;
import ru.lanit.ld.wc.model.*;
import ru.lanit.ld.wc.pages.BigReportForm;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;

import static com.codeborne.selenide.Selenide.sleep;

public class MakeReportTests extends TestBase {


    Instructions inst;
    FolderList folderList;

    @BeforeClass
    public void before() {

        LoginPage lp = new LoginPage();
        inst = lp.open("#/login").LoginAs(app.focusedUser).goToFolder(1999);
        inst.ActionPanel.PreviewIs("Off");

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] TaskWithoutCheck() {

        UserInfo instructionInitiator = app.UserList.anyUser(3).users.get(0); // инициатор=пользователь3
        instructionType type = instructionInitiator.getUserTypes().getControlTypeWithoutCheck(true);

        Instruction instr = new Instruction(type);
        instr
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Для теста отчета без открытия формы") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                .withReceiverID(new int[]{app.focusedUser.getId()});// получатель = наш фокусный пользователь

        instResponse instResponse = instructionInitiator.getUserApi().createInstruction(instr, true);
        instr.withInstructionId(instResponse.getInstructionId());

        logger.info("instruction : " + instr.toString());



        return new Object[][]{new Object[]{instr}};
    }

    @Test(dataProvider = "TaskWithoutCheck", invocationCount = 1)
    public void makeQuickPossitiveReport(Instruction instruction) {


        inst.ActionPanel.refreshButton.click();
        sleep(3000);
        folderList = app.focusedUser.getUserApi().getFolderList(1999);

        inst.cardView.possitiveReport(folderList.getInstructionNumInFolder(instruction.getInstructionId()));
        inst.Dialog.buttonOK.click();

        sleep(3000);

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] TaskWithTextCheck() {

        UserInfo instructionInitiator = app.UserList.anyUser(3).users.get(0); // инициатор=пользователь3
        instructionType type = instructionInitiator.getUserTypes().getControlTypeWithTextCheck(false);

        Instruction instr = new Instruction(type);
        instr
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Для теста отчета с краткой формы") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                .withReceiverID(new int[]{app.focusedUser.getId()});// получатель = наш фокусный пользователь

        instResponse instResponse = instructionInitiator.getUserApi().createInstruction(instr, true);
        instr.withInstructionId(instResponse.getInstructionId());

        logger.info("instruction : " + instr.toString());



        return new Object[][]{new Object[]{instr}};
    }


    @Test(dataProvider = "TaskWithTextCheck", invocationCount = 1)
    public void makeNegativeReportBySmallForm(Instruction instruction) {

        inst.ActionPanel.refreshButton.click();
        sleep(3000);
        folderList = app.focusedUser.getUserApi().getFolderList(1999);

        inst.cardView.negativeReport(folderList.getInstructionNumInFolder(instruction.getInstructionId()));
        sleep(3000);

        inst.SmallReport.clickButton(1);//1= полож,2= отказ,3=сохранить проект
        sleep(2000);

        inst.SmallReport.text.sendKeys("Вот текст отчета, чтоб не ругалось");
        sleep(3000);
        inst.SmallReport.clickButton(1);//1= полож,2= отказ,3=сохранить проект


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] AnyTask() {

        UserInfo instructionInitiator = app.UserList.anyUser(3).users.get(0); // инициатор=пользователь3
        instructionType type = instructionInitiator.getUserTypes().getAnyTaskType();

        Instruction instr = new Instruction(type);
        instr
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Для теста отчета с ПОЛНОЙ формы") // текст сообщения. Если не задано по умолчанию = текст из типа сообщения. Всегда к тексту добавляется + timestamp
                .withReceiverID(new int[]{app.focusedUser.getId()});// получатель = наш фокусный пользователь

        instResponse instResponse = instructionInitiator.getUserApi().createInstruction(instr, true);
        instr.withInstructionId(instResponse.getInstructionId());

        logger.info("instruction : " + instr.toString());



        return new Object[][]{new Object[]{instr}};
    }


    @Test(dataProvider = "AnyTask", invocationCount = 1)
    public void makeReportByBigForm(Instruction instruction) {

        inst.ActionPanel.refreshButton.click();
        folderList = app.focusedUser.getUserApi().getFolderList(1999);
        sleep(2000);

        BigReportForm report=inst.cardView.menuCreateReport(folderList.getInstructionNumInFolder(instruction.getInstructionId()));
        sleep(3000);

        report.text.sendKeys("отчет с большой формы");
        report.refuse(); // .save();

    }

    @AfterClass
    public void after() {
        inst.goToFolder(1999);
    }

}


