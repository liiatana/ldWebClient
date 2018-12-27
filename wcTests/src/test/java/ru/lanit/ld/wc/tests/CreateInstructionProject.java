package ru.lanit.ld.wc.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.appmanager.RestApiHelper;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.instResponse;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateInstructionProject extends TestBase {

    private RestApiHelper api;

    @BeforeMethod
    public void init() {
        api = new RestApiHelper(app.UserList.users.get(1), app);
    }


    @Test(enabled = false)
    public void firstTest() {
        //System.out.println(app.properties.getProperty("web.baseUrl"));
        //System.out.println(app.UserList.users.get(1).getLogin());
        //RestApiHelper api= new RestApiHelper(app.UserList.users.get(1),app);
        System.out.println(String.format("Id = %d", api.me()));
        assertThat(api.me(), equalToObject(app.UserList.users.get(1).getId()));

    }

    @Test() // он падает
    public void secondTest() {
        //System.out.println(app.properties.getProperty("web.baseUrl"));
        //System.out.println(app.UserList.users.get(1).getLogin());
        //RestApiHelper api= new RestApiHelper(app.UserList.users.get(1),app);
        // System.out.println(String.format("Id = %d", api.instructionTypesInfo()));
        Instruction instr = new Instruction(app.InstructionList.getType(2));


        int[] receivers = {app.UserList.users.get(1).getId()};
        instr.withInitiatorID(app.UserList.users.get(0).getId())
                .withReceiverID(receivers);

        instResponse response = app.UserList.users.get(0).getUserApi().instructionSavePrj(instr);
        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId>0);
    }

    @Test()
    public void createOutcomingNoticeProjectOneReceiver() {

        Instruction instr = new Instruction(app.InstructionList.getAnyTaskType());
        instr.withInitiatorID(app.focusedUser.getId())
             .withReceiverID(app.UserList.anyUser(3).Ids());

        instResponse response = app.focusedUser.getUserApi().instructionSavePrj(instr);

        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId>0);
    }

    @Test()
    public void createOutcomingNoticePrjSeveralReceivers() {

        Instruction instr = new Instruction(app.InstructionList.getAnyNoticeType());
        instr.withInitiatorID(app.focusedUser.getId())
                .withReceiverID(app.UserList.anyUser(3).Ids());

        instResponse response = app.focusedUser.getUserApi().instructionSavePrj(instr);

        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId>0);
    }

    @Test()
    public void createOutcomingTaskPrjSeveralReceivers() {

        Instruction instr = new Instruction(app.InstructionList.getAnyTaskType());
        instr.withInitiatorID(app.focusedUser.getId())
                .withReceiverID(app.UserList.anyUser(3).Ids());

        instResponse response = app.focusedUser.getUserApi().instructionSavePrj(instr);

        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId>0);
    }

    @Test()
    public void outTaskPrjWithExecutive() {//проект сообщения с ответствееным исполнителем, отчеты ответственному=да

        Instruction instr = new Instruction(app.InstructionList.getAnyTaskType());
        instr.withInitiatorID(app.focusedUser.getId())
                .withReceiverID(app.UserList.anyUser(3).Ids())
                .setWithExecutive(true)
                .setReportToExecutive(true);

        instResponse response = app.focusedUser.getUserApi().instructionSavePrj(instr);

        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId>0);
    }

    @Test()
    public void outTaskPrjWithExecutiveWithReportReceiver() {

        Instruction instr = new Instruction(app.InstructionList.getAnyTaskType());
        instr.withInitiatorID(app.focusedUser.getId())
                .withSendType(1) //последовательная
                .withReceiverID(app.UserList.anyUser(2).Ids()) //2 random получателя
                .setWithExecutive(true) //ответственный исполнитель=да
                .withReportReceiverID(app.UserList.anyUser().getId()); //получатель отчета=random

        instResponse response = app.focusedUser.getUserApi().instructionSavePrj(instr);

        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId>0);
    }
}
