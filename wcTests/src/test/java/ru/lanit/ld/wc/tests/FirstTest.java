package ru.lanit.ld.wc.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.appmanager.RestApiHelper;
import ru.lanit.ld.wc.model.Instruction;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.MatcherAssert.assertThat;

public class FirstTest extends TestBase {

    private RestApiHelper api;

    @BeforeMethod
    public void init() {
        api = new RestApiHelper(app.UserList.users.get(1), app);
    }


    @Test(enabled = true)
    public void firstTest() {
        //System.out.println(app.properties.getProperty("web.baseUrl"));
        //System.out.println(app.UserList.users.get(1).getLogin());
        //RestApiHelper api= new RestApiHelper(app.UserList.users.get(1),app);
        System.out.println(String.format("Id = %d", api.me()));
        assertThat(api.me(), equalToObject(app.UserList.users.get(1).getId()));

    }

    @Test(enabled = true)
    public void secondTest() {
        //System.out.println(app.properties.getProperty("web.baseUrl"));
        //System.out.println(app.UserList.users.get(1).getLogin());
        //RestApiHelper api= new RestApiHelper(app.UserList.users.get(1),app);
        // System.out.println(String.format("Id = %d", api.instructionTypesInfo()));
        Instruction instr = new Instruction(app.InstructionList.getType(2));


        int[] receivers={app.UserList.users.get(1).getId()};
        instr.withInitiatorID(app.UserList.users.get(0).getId())
                .withReceiverID(receivers);

        app.UserList.users.get(0).getUserApi().instructionSavePrj(instr);
    }


}
