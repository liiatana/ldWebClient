package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SmallReportForm {

    public SelenideElement subject = $(By.xpath("//input[@placeholder=\"Тема сообщения\"]"));
    public SelenideElement text = $(By.xpath("(//textarea[@name=\"input-10-1\"])[1]"));
    public SelenideElement comment = $(By.xpath("(//textarea[@name=\"input-10-1\"])[2]"));

    private ElementsCollection buttons = $$(By.xpath("//div[@class=\"report-footer secondary\"]/button"));




    private SelenideElement openInNewWindow = $(By.xpath("//div[@class=\"layout right-panel grey--border lighten-4 column\"]")).$("exit_to_app");

    public void clickButton(int i) {
        buttons.get(i).click();

    }


}
