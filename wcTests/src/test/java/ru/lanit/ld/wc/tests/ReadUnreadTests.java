package ru.lanit.ld.wc.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.FolderList;
import ru.lanit.ld.wc.pages.Instructions;
import ru.lanit.ld.wc.pages.LoginPage;

import static com.codeborne.selenide.Selenide.sleep;

public class ReadUnreadTests extends TestBase {
    Instructions instSection;
    FolderList folderList;

    @BeforeClass
    public void before() {

        LoginPage lp = new LoginPage();
        instSection = lp.open("#/login").LoginAs(app.focusedUser).goToFolder(1999);
        instSection.ActionPanel.PreviewIs("Off");
        instSection.ActionPanel.viewOnlyNew(false);


    }

    @DataProvider
    public Object[][] Object() {

        folderList = app.focusedUser.getUserApi().getFolderList(1999);
        app.focusedUser.getUserApi().setReaded(true,folderList.items.get(0).getInstructionId());

        return new Object[][] {new Object[]{folderList.items.get(0).getInstructionId()}};
    }

    @Test(dataProvider = "Object", description = "Сделать непрочитанным и просмотреть непрочитанные")
    public void makeUnread(int InstructionId){
        instSection.ActionPanel.refreshButton.click();
        sleep(3000);

        instSection.cardView.menuReaded(folderList.getInstructionNumInFolder(InstructionId));
        instSection.ActionPanel.refreshButton.click();
        sleep(2000);

        instSection.ActionPanel.viewOnlyNew(true);
        sleep(3000);

    }


    @AfterClass
    public void after() {

        instSection.goToFolder(1999);
        instSection.ActionPanel.viewOnlyNew(false);
        //sleep(3000);

    }


}
