package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.Instruction;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class Header {

    private SelenideElement FIO =
            $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Настройки пользователя'])[1]/following::button[4]"));

    private SelenideElement exit = $ (By.cssSelector("div.v-list:nth-child(3) > div:nth-child(1) > a:nth-child(1) > div:nth-child(1)"));
    //$(By.xpath("xpath=(.//*[normalize-space(text()) and normalize-space(.)='На резолюцию'])[2]/following::div[5]"));

    private SelenideElement createButton = $(By.xpath("(//div[@class=\"v-menu__activator\"]/*/div)[1]"));


    public String getLastName() {
        //FIO.getText().split("\n")[1].split(" ")[0] - фамилия
        return FIO.getText().split("\n")[1].split(" ")[0];
    }

    public void exit() {
        FIO.click();
        exit.shouldBe(Condition.visible);
        exit.click();
    }


    public NewInstructionPage CreateButtonClick(Instruction newInstruction, ApplicationManager app) {
        createButton.click();
        sleep(3000);

        InstructionTypes.findBy(Condition.text(app.focusedUser.getUserTypes().getInstructionTypeNameById(NewInstruction.getInstructionTypeId()))).click();
        return page(NewInstructionPage.class);
    }
}
