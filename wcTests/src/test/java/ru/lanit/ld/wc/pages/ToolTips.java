package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

public class ToolTips {

    private ElementsCollection toolTips=$$(By.xpath("//div[@class=\"toasted-container bottom-right\"]"));
     //class="toasted-container bottom-right"


    public ToolTips() {
    }

    public ElementsCollection getToolTips(){
        return toolTips;
    }
}
