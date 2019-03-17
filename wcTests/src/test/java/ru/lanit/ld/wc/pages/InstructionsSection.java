package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class InstructionsSection {
    public SideBar SideBar;
    public Header Header;
    public ActionPanel ActionPanel;
    public CancelOK_Dialog Dialog;
    public SmallReportForm SmallReport;
    public InstructionCardView cardView;

    public ElementsCollection InstructionListWithPreview = $$(By.xpath("//div[@class=\"data-iteraror marginless-list\"]/*"));

    //public ElementsCollection InstructionListWithoutPreview=$$(By.xpath("//div[@class=\"layout list-item\"]"));

    public String threePoints= "div.v-menu.menu-vert.v-menu--inline > div > button > div > i";

    public InstructionsSection() {
        SideBar= new SideBar();
        Header= new Header();
        ActionPanel=new ActionPanel();
        Dialog=new CancelOK_Dialog();
        SmallReport=new SmallReportForm();
        cardView=new InstructionCardView();
    }

    public ViewInstruction openInstructionView(int ID) {
        Selenide.open(String.format("instruction/%s",ID));
        sleep(3000);
        return page(ViewInstruction.class);
    }

    public InstructionsSection goToFolder(int Folder_ID){
        Selenide.open(String.format("instructions/%s",Folder_ID));

        sleep(10000);
        return this;
    }



}
