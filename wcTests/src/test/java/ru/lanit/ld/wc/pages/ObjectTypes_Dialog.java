package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;

public class ObjectTypes_Dialog {

    private SelenideElement area = $(By.xpath("//div[@class=\"v-dialog modal v-dialog--active v-dialog--persistent\"]"));

    public SelenideElement caption = area.$(By.xpath("//*/div[@class=\"v-card__title title font-weight-bold pb-0\""));

    public ElementsCollection availableTypes = area.$$(By.xpath("/*//ul[@class=\"pl-0\"]/li"));

    public ObjectTypes_Dialog() {
    }

    public Set<String> getTypesList() {

        sleep(1000);
        Set<String> collect1 = new HashSet<>();

        collect1 = Arrays.stream(availableTypes.texts().toString().substring(1, availableTypes.texts().toString().length() - 1).toUpperCase(). split(", "))
                .collect(Collectors.toSet());

        return collect1;
    }

    public void close() {
        area.$x("./*//i").click();
    }
}
