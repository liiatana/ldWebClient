package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class BigReportForm {

    private ElementsCollection buttons =$$(By.xpath("//div[@class=\"layout report column\"]/*//button")); //0- выбрать док 1-сочитаться 2-отказать 3- сохранить проект

    public SelenideElement subject = $(By.xpath("//input[@placeholder=\"Тема сообщения\"]"));
    public SelenideElement text = $(By.xpath("(//textarea[@name=\"input-10-1\"])[1]"));
    public SelenideElement comment = $(By.xpath("(//textarea[@name=\"input-10-1\"])[2]"));

    public CancelOK_Dialog dialog;

    public BigReportForm() {
        dialog=new CancelOK_Dialog();
    }

    public void report(){
        buttons.get(1).click();
    }

    public void refuse(){
        buttons.get(2).click();
    }

    public void save(){
        buttons.get(3).click();
        sleep(1000);
        dialog.buttonOK.click();
    }
}
