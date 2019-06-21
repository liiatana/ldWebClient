package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class SideBar {


    private SelenideElement allSection=$(By.xpath("(//aside"));

    private ElementsCollection sections = $$(By.xpath("//aside/*//div[@role=\"listitem\"]"));

    private SelenideElement tree=$(By.xpath("(//aside//div)[2]"));

    private SelenideElement treeControl=$(By.xpath("(//nav//button)[1]"));


private ElementsCollection root    ;


    public void goTo(String section, String rootFolder, String additionalFolder) {
        sections.filter( Condition.text(section)).get(0).click();

        if(!rootFolder.equals("")){
            if (tree.getAttribute("style").contains("display: none;")){
                treeControl.click();
            }

            //root=tree.$$x("*//div[@class=\"tree-view\"]/ul/li");
            //root.filterBy(Condition.matchText(rootFolder)).get(0).click();
            //tree.$$x("/*//div[@class=\"tree-view\"]/ul/li").filterBy(Condition.visible).filterBy(Condition.matchText(rootFolder)).get(0).click();
            if(additionalFolder.equals("")) {
                tree.$$x("/*//div[@class=\"tree-view\"]/ul/li/span").filterBy(Condition.visible).filterBy(Condition.matchText(rootFolder)).get(0).click();
            } // else и .... тут надо разобраться как переходить в дочерние папки

            sleep(3000);
        }



    }


}
