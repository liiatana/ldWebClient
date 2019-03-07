package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;

import static com.codeborne.selenide.Selenide.*;

public class NewInstructionPage {


    public CancelOK_Dialog dialog;
    public ChoosePersonForm choosePersonForm;

    private SelenideElement subject = $(By.xpath("//input[@placeholder=\"Тема сообщения\"]"));
    private SelenideElement text = $(By.xpath("//textarea[@name=\"input-10-1\"]"));

    private SelenideElement comment = $(By.xpath("//textarea[@name=\"input-2-1\"]"));

    private SelenideElement receiver = $(By.xpath("//input[@id=\"multiselect\"]"));

    private ElementsCollection receiversList = $$(By.xpath("//div[@class=\"multiselect__content-wrapper\"]/ul/li"));

    public SelenideElement cancelButton = $(By.xpath("(//div[@class=\"layout instruction-right-btn-panel edit-btn justify-end px-0 mt-3\"]/button)[1]"));

    public SelenideElement saveProjectButton = $(By.xpath("(//div[@class=\"layout instruction-right-btn-panel edit-btn justify-end px-0 mt-3\"]/button)[2]"));

    public SelenideElement sendButton = $(By.xpath("(//div[@class=\"layout instruction-right-btn-panel edit-btn justify-end px-0 mt-3\"]/button)[3]"));

    private ElementsCollection sendType = $$(By.xpath("//div[@class=\"flex pt-2 pl-1\"]/*//button"));

    private SelenideElement withExecutive = $(By.xpath("(//div[@class=\"v-input--selection-controls__input\"])[1]"));
    private SelenideElement reportToExecutive = $(By.xpath("(//div[@class=\"v-input--selection-controls__input\"])[2]"));


    private SelenideElement hasAuditor = $(By.xpath("(//input[@id=\"withExecutive\"])[2]"));
    private SelenideElement auditorArea = $(By.xpath("//div[@class=\"flex xl12 lg12 md12 sm12 xs12 pr-2 pl-1\"]"));

    private SelenideElement initiatorArea = $(By.xpath("//div[@class=\"flex pr-2 pl-1 xl12 lg12 md12 sm12 xs12\"]"));

    private SelenideElement reportReceiverArea = $(By.xpath("//div[@id=\"use-control\"]/div"));

    private SelenideElement choosePerson = $(By.xpath("//button[@id=\"addReceiver\"]"));


    public NewInstructionPage() {

        dialog = new CancelOK_Dialog();
        //choosePersonForm=new ChoosePersonForm();
    }

    public void fillForm(Instruction newInstruction, ApplicationManager app, boolean useChoosePersonForm) {

        sleep(2000);

        //текстовые поля
        setValueToTextFeild(subject, newInstruction.getSubject());
        setValueToTextFeild(text, newInstruction.getText());
        setValueToTextFeild(comment, newInstruction.getComment());


        if (newInstruction.isControl() == true) {
            //тип рассылки
            sendType.get(newInstruction.getSendType()).click();

            //ответ.исполнитель и отчеты ответственному
            setExecutiveOptions(newInstruction.isWithExecutive(), newInstruction.isReportToExecutive());

            //контролер
            if (setControl(newInstruction.getExecAuditorID())) {
                setEmployerToField(app.UserList.getUserById(newInstruction.getExecAuditorID()), auditorArea);
            }

            //получатель отчета
            setEmployerToField(app.UserList.getUserById(newInstruction.getReportReceiverID()), reportReceiverArea);

            //задание выдал
            setEmployerToField(app.UserList.getUserById(newInstruction.getInitiatorID()), initiatorArea);

        }

        //получатель
        //receiver.sendKeys(app.UserList.getUserById(newInstruction.getReceiverID()[0]).getLastName());
        if (!useChoosePersonForm) {
            setValueToTextFeild(receiver, app.UserList.getUserById(newInstruction.getReceiverID()[0]).getLastName());
            receiversList.findBy(Condition.text(app.UserList.getUserById(newInstruction.getReceiverID()[0]).getUserName())).click();
        } else {
            //openChoosePersonForm(newInstruction, app);
            choosePersonForm = openChoosePerson();
            choosePersonForm.insertPersons(newInstruction.getReceiverID(), app);
        }

    }


    private void setExecutiveOptions(boolean isExecutive, boolean isReportToExecutive) {

        boolean isExecutiveCurrentState, isReportToExecutiveCurrentState;

//        если чекбокс стоит
//                <i aria-hidden="true" class="v-icon material-icons theme--light primary--text">check_box</i>
//                если нет
//                <i aria-hidden="true" class="v-icon material-icons theme--light">check_box_outline_blank</i>
        if (withExecutive.lastChild().text().equals("check_box")) {
            isExecutiveCurrentState = true;
        } else {
            isExecutiveCurrentState = false;
        }

        if (reportToExecutive.lastChild().text().equals("check_box")) {
            isReportToExecutiveCurrentState = true;
        } else {
            isReportToExecutiveCurrentState = false;
        }

        if (isExecutive != isExecutiveCurrentState) {
            withExecutive.click();
            if (isReportToExecutive != isReportToExecutiveCurrentState) {
                reportToExecutive.click();
            }
        }

    }

    private void setValueToTextFeild(SelenideElement fieldName, String textToBeTyped) {
        fieldName.sendKeys(Keys.CONTROL + "a");
        fieldName.sendKeys(textToBeTyped);
    }

    private boolean setControl(int AuditorId) {

        boolean hasAuditorValue;
        if (AuditorId != 0) {
            hasAuditorValue = true;
        } else {
            hasAuditorValue = false;
        }

        Boolean state = Boolean.valueOf(hasAuditor.getAttribute("checked"));
        if (state != hasAuditorValue) {
            hasAuditor.doubleClick();
        }
        if (hasAuditorValue) {
            return true;
        } else {
            return false;
        }

    }

    private void setEmployerToField(UserInfo user, SelenideElement field) {

        if (field.lastChild().text().length() > 0) {
            field.lastChild().lastChild().lastChild().click();
        }

        field.lastChild().sendKeys(user.getLastName());
        sleep(3000);
        receiversList.findBy(Condition.text(user.getUserName())).click();
    }

    public ChoosePersonForm openChoosePerson() {
        choosePerson.click();
        return page(ChoosePersonForm.class);
    }

}
