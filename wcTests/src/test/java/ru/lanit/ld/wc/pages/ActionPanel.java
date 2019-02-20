package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class ActionPanel {

    private SelenideElement PreviewOn =$(By.xpath("(//button[@value='1'])[2]"));
    private SelenideElement PreviewOff =$(By.xpath("//button[@value='0']"));

    public void PreviewIs(String state) {

        switch (state) {
            case "On":
                //if (PreviewOff.isSelected()) {
                    PreviewOn.click();
                //}
            case "Off":
                //if (PreviewOff.isSelected()) {
                    PreviewOff.click();
                //}
                ;
        }
        sleep(4000);
    }
}
