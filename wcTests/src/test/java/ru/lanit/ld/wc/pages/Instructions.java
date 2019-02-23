package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.page;

public class Instructions {
    public SideBar SideBar;
    public Header Header;
    public ActionPanel ActionPanel;


    public Instructions() {
        SideBar= new SideBar();
        Header= new Header();
        ActionPanel=new ActionPanel();

    }
    public ViewInstruction openInstructionView(int ID) {
        Selenide.open(String.format("#/instruction/%s",ID));
        return page(ViewInstruction.class);
    }

}
