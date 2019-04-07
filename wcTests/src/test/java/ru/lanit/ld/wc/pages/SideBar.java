package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SideBar {


    private ElementsCollection sections = $$(By.xpath("//div[@class=\"flex secondary main-menu-panel\"]/div/*"));

    public void goToSection(String section, String rootFolder, String additionalFolder) {
        sections.findBy(Condition.text())

    }


}
