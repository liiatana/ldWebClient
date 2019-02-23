package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ViewInstruction {


    public ElementsCollection Blocks = $$(By.xpath("//*[@id=\"expansion-wrapper\"]/ul/li"));

    public SelenideElement ThreePoints =$(By.xpath("(//div[@class=\"v-menu__activator\"])[4]"));

    public SelenideElement Area=$(By.xpath("//*[@id=\"insrtuction-common-info\"]"));


    public boolean IsBlockActive(int BlockId) {
        if ( Blocks.get(BlockId).$(By.xpath("div[1]/div[2]")).lastChild().exists()){
            return true;}
        else {return false;}
    }

    public void clickOnBlock(int BlockId)
    {
        Blocks.get(BlockId).click();
    }




}
