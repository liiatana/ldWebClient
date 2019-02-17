package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class ActionPanel {

    private SelenideElement PreviewOn =;
    private SelenideElement PreviewOff =;

    public void PreviewIs(String state) {

        switch (state) {
            case "On":
                if (PreviewOff.isSelected()) {
                    PreviewOn.click();
                }
            case "Off":
                if (PreviewOff.isSelected()) {
                    PreviewOff.click();
                }
                ;
        }
    }
}
