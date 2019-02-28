package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ObjectTypes_Dialog {

    private SelenideElement area  = $ (By.xpath("//div[@class=\"v-dialog modal v-dialog--active v-dialog--persistent\"]"));

    public SelenideElement caption = area.$(By.xpath("//*/div[@class=\"v-card__title title font-weight-bold pb-0\""));

    public ElementsCollection availableTypes = area.$$ (By.xpath("/*//ul[@class=\"pl-0\"]/li"));


}
