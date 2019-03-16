package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import static com.codeborne.selenide.Selenide.*;

public class InstructionCardView {

    public ElementsCollection cards = $$(By.xpath("//div[@class=\"layout list-item\"]"));

    //private ElementsCollection popUps ; //= $$(By.xpath("//div[@class=\"v-tooltip__content white\"]/span[@class=\"grey--text darken-4\"]"))
    SelenideElement popUp = $(By.cssSelector("div.v-tooltip__content.menuable__content__active.white"));

    public BigReportForm menuCreateReport(int instructionNumInFolder) {
        cards.get(instructionNumInFolder).lastChild().find("div.v-menu.menu-vert.v-menu--inline > div > button > div > i").click();
        //activeCard.$$x("div//[@role=\"listitem\"]").filter(Condition.text(item)).get(0).click();
        cards.get(instructionNumInFolder).$$x("//div[@role=\"listitem\"]").filter(Condition.text("Создать отчет")).get(0).click();
        return page(BigReportForm.class);
    }

    public void possitiveReport(int instructionNumInFolder) {
        cards.get(instructionNumInFolder).$$x("/*//div[@class=\"quick-access\"]").get(0).click();
    }


    public void negativeReport(int instructionNumInFolder) {
        cards.get(instructionNumInFolder).$$x("/*//div[@class=\"quick-access\"]").get(1).click();
    }

    public void menuReaded(int instructionNumInFolder) {
        cards.get(instructionNumInFolder).lastChild().find("div.v-menu.menu-vert.v-menu--inline > div > button > div > i").click();
        //activeCard.$$x("div//[@role=\"listitem\"]").filter(Condition.text(item)).get(0).click();
        cards.get(instructionNumInFolder).$$x("//div[@role=\"listitem\"]").filter(Condition.matchText("Пометить как.*")).get(0).click();

    }

    public String getInstructionText(int instructionNumInFolder) {
        return cards.get(instructionNumInFolder).$x(".//div[@class=\"layout body-1 ml-1 pop-up font-weight-regular\"]").getText();
    }

    public String getInstructionPopUpText(int instructionNumInFolder) {
        SelenideElement cardText = cards.get(instructionNumInFolder).$x(".//div[@class=\"layout body-1 ml-1 pop-up font-weight-regular\"]");
        cardText.hover();
        sleep(1000);
              return popUp.getText();
    }

    public String getReceiverName(int instructionNumInFolder) {
        return cards.get(instructionNumInFolder).$x(".//*//span").getText();
    }

    public String getCreationDate (int instructionNumInFolder){
        return cards.get(instructionNumInFolder).$$x(".//*//div[@class=\"grey--text darken text-no-wrap caption type-name hidden-xs-only font-weight-regular\"]").first().getText();
    }

    public String getTypeName(int instructionNumInFolder) {
        return cards.get(instructionNumInFolder).$$x(".//*//div[@class=\"grey--text darken text-no-wrap caption type-name hidden-xs-only font-weight-regular\"]/span").get(2).getText();
    }
}
