package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.Instruction;

import static com.codeborne.selenide.Selenide.*;

public class NewInstructionPage {


    public CancelOK_Dialog dialog;

    private SelenideElement subject = $(By.xpath("//input[@placeholder=\"Тема сообщения\"]"));
    private SelenideElement text = $(By.xpath("//textarea[@name=\"input-10-1\"]"));

    private SelenideElement comment = $(By.xpath("//textarea[@name=\"input-2-1\"]"));

    private SelenideElement receiver = $(By.xpath("//input[@id=\"multiselect\"]"));

    private ElementsCollection receiversList=$$(By.xpath("//div[@class=\"multiselect__content-wrapper\"]/ul/li"));

    public SelenideElement cancelButton=$(By.xpath("(//div[@class=\"layout instruction-right-btn-panel edit-btn justify-end px-0 mt-3\"]/button)[1]"));

    public SelenideElement saveProjectButton=$(By.xpath("(//div[@class=\"layout instruction-right-btn-panel edit-btn justify-end px-0 mt-3\"]/button)[2]"));

    public SelenideElement sendButton=$(By.xpath("(//div[@class=\"layout instruction-right-btn-panel edit-btn justify-end px-0 mt-3\"]/button)[3]"));

    public NewInstructionPage() {

        dialog= new CancelOK_Dialog();
    }

    public void fillForm(Instruction newInstruction, ApplicationManager app) {

        receiver.sendKeys(app.UserList.getUserById(newInstruction.getReceiverID()[0]).getLastName());
        sleep(10000);
        receiversList.findBy(Condition.text(app.UserList.getUserById(newInstruction.getReceiverID()[0]).getUserName())).click();

        subject.sendKeys(Keys.CONTROL + "a");
        subject.sendKeys(newInstruction.getSubject());


        text.sendKeys(Keys.CONTROL + "a");
        text.sendKeys(newInstruction.getText());

        comment.sendKeys(Keys.CONTROL + "a");
        comment.sendKeys(newInstruction.getComment());
    }
}
