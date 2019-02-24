package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class ActionPanel {

    private SelenideElement PreviewOn = $(By.xpath("(//button[@value='1'])[2]"));
    private SelenideElement PreviewOff = $(By.xpath("//button[@value='0']"));

    public void PreviewIs(String state) {

        switch (state) {
            case "On":

                PreviewOn.click();
                sleep(1000);
                break;

            case "Off":

                PreviewOff.click();
                sleep(3000);
                break;


        }

    }
}
