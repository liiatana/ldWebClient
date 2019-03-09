package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

public class InstructionCardView {

    public ElementsCollection cards = $$(By.xpath("//div[@class=\"layout list-item\"]"));


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

   public String getInstructionText(int instructionNumInFolder){
       return cards.get(instructionNumInFolder).$x(".//div[@class=\"layout body-1 ml-1 pop-up font-weight-regular\"]").getText();
   }

}
