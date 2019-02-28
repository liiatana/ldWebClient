package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.Instruction;

import static com.codeborne.selenide.Selenide.*;

public class ActionPanel {

    private SelenideElement PreviewOn = $(By.xpath("(//button[@value='1'])[2]"));
    private SelenideElement PreviewOff = $(By.xpath("//button[@value='0']"));

    private SelenideElement PlusButton = $(By.xpath("(//div[@class=\"v-menu__activator\"]/button)[5]"));

    private ElementsCollection InstructionTypes = //$$(By.xpath("//div[@class=\"v-menu__content theme--light menuable__content__active\"]/div[@role=\"list\"]"));
$$(By.xpath("//div[@class=\"v-menu__content theme--light menuable__content__active\"]/div[@role=\"list\"]/*/a"));

    public SelenideElement refreshButton=$(By.xpath("(//span[@class=\"v-tooltip v-tooltip--bottom\"])[3]"));

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





    public NewInstructionPage createNewByPlusButton(Instruction NewInstruction, ApplicationManager app) {
        PlusButton.click();
        sleep(3000);
        InstructionTypes.findBy(Condition.text(app.focusedUser.getUserTypes().getInstructionTypeNameById(NewInstruction.getInstructionTypeId()))).click();
        return page(NewInstructionPage.class);
    }


}
