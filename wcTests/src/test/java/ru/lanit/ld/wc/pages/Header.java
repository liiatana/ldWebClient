package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.Instruction;

import static com.codeborne.selenide.Selenide.*;

public class Header {

    private SelenideElement FIO =
            //$(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Настройки пользователя'])[1]/following::button[4]"));
    $(By.xpath("//button[@class=\"deputy-btn v-btn v-btn--flat theme--light\"]"));

    private ElementsCollection FIOmenu=$$(By.xpath("//div[@class=\"v-menu__content theme--light menuable__content__active\"]/*"));
    //private SelenideElement exit =
    //$(By.);
    //        $(By.cssSelector("div.v-list:nth-child(3) > div:nth-child(1) > a:nth-child(1) > div:nth-child(1)"));
    //$(By.xpath("xpath=(.//*[normalize-space(text()) and normalize-space(.)='На резолюцию'])[2]/following::div[5]"));
//.filter(Condition.text("Создать отчет")).get(0).click();


    private SelenideElement createButton = $(By.xpath("(//div[@class=\"v-menu v-menu--inline\"])[1]"));

    private ElementsCollection menu = $$(By.xpath("//div[@class=\"v-list create-global-menu theme--light\"]/div"));
    //div[@class="v-list create-global-menu theme--light"] - это сама область с выпадающим списокм сообщение+Документ


    private ObjectTypes_Dialog ObjectTypes_Dialog;

    public Header() {
        ObjectTypes_Dialog = new ObjectTypes_Dialog();
    }

    public String getLastName() {
        //FIO.getText().split("\n")[1].split(" ")[0] - фамилия
        return FIO.getText().split("\n")[1].split(" ")[0];
    }

    public void exit() {
        FIO.click();
        FIOmenu.filter(Condition.text("Выход")).get(0).click();

        //exit.shouldBe(Condition.visible);
        //exit.click();
    }


    public NewInstructionPage CreateButtonClick(Instruction newInstruction, ApplicationManager app) {
        createButton.click();
        sleep(3000);

        menu.get(0).click();

        sleep(3000);

        ObjectTypes_Dialog.availableTypes.findBy(Condition.text(app.focusedUser.getUserTypes().getInstructionTypeNameById(newInstruction.getInstructionTypeId()))).click();

        sleep(5000);
        //InstructionTypes.findBy(Condition.text(app.focusedUser.getUserTypes().getInstructionTypeNameById(NewInstruction.getInstructionTypeId()))).click();
        return page(NewInstructionPage.class);
    }
}
