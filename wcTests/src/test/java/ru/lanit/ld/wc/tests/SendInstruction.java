package ru.lanit.ld.wc.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.instResponse;

import static org.hamcrest.MatcherAssert.assertThat;

public class SendInstruction extends TestBase {

    @BeforeMethod
    public void init() {
        System.out.println("SendInstructionTests");
    }

    @Test()
    public void sendNotice() {
        Instruction instr = new Instruction(app.InstructionList.getAnyNoticeType());
        instr.withInitiatorID(app.focusedUser.getId())
             .withReceiverID(app.UserList.anyUser(3).Ids());

        instResponse response = app.focusedUser.getUserApi().send(instr);

        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId > 0);

    }

    @Test(invocationCount = 1)
    public void sendTask() {
        Instruction instr = new Instruction(app.InstructionList.getAnyTaskType());
        instr.withInitiatorID(app.focusedUser.getId())
             .withReceiverID(app.UserList.anyUser(2).Ids());

        instResponse response = app.focusedUser.getUserApi().send(instr);

        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId > 0);

    }

    @Test(invocationCount = 1)
    public void sendTaskWithAuditor() {
        Instruction instr = new Instruction(app.InstructionList.getAnyClericalTaskType());
        instr.withInitiatorID(app.focusedUser.getId())
             .withExecAuditorID(app.UserList.anyUser().getId())
             .withReceiverID(app.UserList.anyUser(2).Ids());

        instResponse response = app.focusedUser.getUserApi().send(instr);

        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId > 0);

    }

    @Test(invocationCount = 1)
    public void sendTaskAll() {
        Instruction instr = new Instruction(app.InstructionList.getAnyClericalTaskType());
        instr.withInitiatorID(app.focusedUser.getId())
                .withExecAuditorID(app.UserList.anyUser().getId())
                .setWithExecutive(true)
               // .setReportToExecutive(true)
                .withSendType(1)
                .withReceiverID(app.UserList.anyUser(3).Ids());

        instResponse response = app.focusedUser.getUserApi().send(instr);

        Assert.assertEquals(response.message, "");
        Assert.assertTrue(response.instructionId > 0);

    }
}
