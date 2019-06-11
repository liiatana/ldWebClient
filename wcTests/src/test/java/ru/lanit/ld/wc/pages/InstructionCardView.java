package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class InstructionCardView {

    public ElementsCollection cards = $$(By.xpath("//div[@class=\"layout list-item\"]"));

    //private ElementsCollection popUps ; //= $$(By.xpath("//div[@class=\"v-tooltip__content white\"]/span[@class=\"grey--text darken-4\"]"))
    //private SelenideElement popUp = $(By.cssSelector("div.v-tooltip__content.menuable__content__active.white"));

    private ElementsCollection actionsMenuItems;





    // --------Кнопки Отчитаться/Отказать-----

    public void possitiveReport(int instructionNumInFolder) {
        cards.get(instructionNumInFolder).$$x(".//button").get(0).click();
    }


    public void negativeReport(int instructionNumInFolder) {
        cards.get(instructionNumInFolder).$$x(".//button").get(1).click();
    }

    public void quickReport (boolean isPossitive, int instructionNumInFolder){
        if (isPossitive) {possitiveReport(instructionNumInFolder);}
        else {negativeReport(instructionNumInFolder);}
    }





    // --------Меню действий-----
    public void closeMenu (int instructionNumInFolder) {

        cards.get(instructionNumInFolder).lastChild().find("div.v-menu.menu-vert.v-menu--inline > div > button > div > i").click();
    }

    public ElementsCollection ActionsMenu(int instructionNumInFolder) {
        cards.get(instructionNumInFolder).lastChild().find("div.v-menu.menu-vert.v-menu--inline > div > button > div > i").click();
        return cards.get(instructionNumInFolder).$$x("//div[@role=\"listitem\"]");
    }

    public BigReportForm menuCreateReport(int instructionNumInFolder) {
        cards.get(instructionNumInFolder).lastChild().find("div.v-menu.menu-vert.v-menu--inline > div > button > div > i").click();
        //activeCard.$$x("div//[@role=\"listitem\"]").filter(Condition.text(item)).get(0).click();
        cards.get(instructionNumInFolder).$$x("//div[@role=\"listitem\"]").filter(Condition.text("Создать отчет")).get(0).click();
        return page(BigReportForm.class);
    }

    // --------Текст сообщения-----
    public SelenideElement getInstructionTextAsSE(int instructionNumInFolder) {
        return cards.get(instructionNumInFolder).$x(".//span[@class=\"nowrap-text\"]");
    }

    // --------Всплывающий текст-----
    public SelenideElement getInstructionPopUp(int instructionNumInFolder) {
        //SelenideElement cardText = cards.get(instructionNumInFolder).$x(".//div[@class=\"v-tooltip__content black\"]");
        SelenideElement popup=$$x("//div[@class=\"v-tooltip__content black\"]").last(10-instructionNumInFolder).last();
        //popup.hover();
        sleep(1000);
        return popup;
    }


    // --------Получатель-----
    public SelenideElement getReceiverNameAsSE(int instructionNumInFolder) {
        return cards.get(instructionNumInFolder).$x(".//*//span");
    }

    //-------Признак Прочитано----------
    public Boolean getFlagState(int instructionNumInFolder) {
        return cards.get(instructionNumInFolder).$x(".//*//div[@class=\"v-avatar ml-2 mt-1 error\"]").exists();
    }




    // --------Даты ----
    public LocalDateTime getCreationDateAsLocalDate (int instructionNumInFolder){

        String d=cards.get(instructionNumInFolder).$$x("./*//div[@class=\"layout align-baseline row\"]//span").get(7).getText();
        //.$$x(".//*//div[@class=\"grey--text darken text-no-wrap caption type-name hidden-xs-only font-weight-regular\"]/span").get(0).getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        return LocalDateTime.parse(d, formatter);
    }

    public SelenideElement getCreationDateAsSE (int instructionNumInFolder){
        return cards.get(instructionNumInFolder).$$x(".//*//div[@class=\"layout align-baseline row\"]/div/span").get(0);
    }

    public SelenideElement getExecutionDateAsSE (int instructionNumInFolder){
        return cards.get(instructionNumInFolder).$$x(".//*//div[@class=\"layout align-baseline row\"]/div/span").get(4);
    }



    // --------Тип сообщения-----
    public SelenideElement getInstructionTypeAsSe(int instructionNumInFolder) {
        return cards.get(instructionNumInFolder).$$x(".//*//div[@class=\"layout align-baseline row\"]/div/span").get(2);
    }

    // --------Статус сообщения-----
    public SelenideElement getInstructionStateAsSe(int instructionNumInFolder) {
        return cards.get(instructionNumInFolder).$$x("./*//div[@class=\"layout align-baseline row\"]//span").last();
        //.$$x(".//*//div[@class=\"v-chip ml-2 chip caption v-chip--label v-chip--outline v-chip--small theme--light info info--text\"").get(0);

    }



    // --------Открыть сообщение-----
    public ViewInstruction open(int instructionNumInFolder) {
        cards.get(instructionNumInFolder).$$x(".//*//i").findBy(Condition.text("exit_to_app")).click();
        return new ViewInstruction();
    }

    // --------Считать информацию о сообщении из списка-----
    public Instruction getIstructionInfo(int focusedInstructionNum, ApplicationManager app, UserInfo user, boolean viewAsReceiver) {
        Instruction inst=new Instruction();

        if(viewAsReceiver){
            inst.withInitiatorID(new int[]{app.UserList.getUserIdByFIO(getReceiverNameAsSE(focusedInstructionNum).getText())});
            //inst.withReportReceiverID(new int[]{});
        }
        else {

            inst.withReceiverID(new int[]{app.UserList.getUserIdByFIO(getReceiverNameAsSE(focusedInstructionNum).getText())});
        }
        inst.withText(getInstructionTextAsSE(focusedInstructionNum).getText())
                .withInstructionType( user.getUserTypes().getInstructionTypeIdByName( getInstructionTypeAsSe(focusedInstructionNum).getText()))
                .withStateName(getInstructionStateAsSe(focusedInstructionNum).getText())
                .withStartDate(getCreationDateAsLocalDate(focusedInstructionNum));
                //.withReportReceiverID(new int[] {-1});




        return inst;
    }
}
