package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class Instructions {
    public SideBar SideBar;
    public Header Header;
    public ActionPanel ActionPanel;
    public  CancelOK_Dialog Dialog;

    public ElementsCollection InstructionListWithPreview = $$(By.xpath("//div[@class=\"data-iteraror marginless-list\"]/*"));

    public ElementsCollection InstructionListWithoutPreview=$$(By.xpath("//div[@class=\"layout list-item\"]"));



    public Instructions() {
        SideBar= new SideBar();
        Header= new Header();
        ActionPanel=new ActionPanel();
        Dialog=new CancelOK_Dialog();

    }
    public ViewInstruction openInstructionView(int ID) {
        Selenide.open(String.format("#/instruction/%s",ID));
        sleep(3000);
        return page(ViewInstruction.class);
    }

    public Instructions goToFolder(int Folder_ID){
        Selenide.open(String.format("#/instructions/%s",Folder_ID));
        sleep(4000);
        return this;
    }



}
