package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.FolderList;
import ru.lanit.ld.wc.model.Instruction;

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
        sleep(10000);
        return page(ViewInstruction.class);
    }

    public InstructionsSection goToFolder(int Folder_ID){
        Selenide.open(String.format("instructions/%s",Folder_ID));

        sleep(10000);
        return this;
    }

    public InstructionsSection goTo(String section,String rootFolder, String additionalPath){
        ActionPanel.openObjectTree();
        SideBar.goToSection(section,rootFolder, additionalPath);
        return this;
    }





    public void clickOnReportButton(Instruction focusedInstruction, boolean reportType, ApplicationManager app) {
        //обновить список папки
        this.ActionPanel.refreshList();

        FolderList folderList;

        //получить список сообщений папки
        folderList =  app.UserList.getUserById(focusedInstruction.getReceiverID()[0])  .getUserApi().getFolderList(1999, 10);

        //нажать кнопку Отчитаться/Отказать для сообщения
        this.cardView.quickReport(reportType, folderList.getInstructionNumInFolder(focusedInstruction.getInstructionId()));
        //в диалоговом окне нажать кнопку ОК
        this.Dialog.buttonOK.click();

        sleep(7000);
    }

}
