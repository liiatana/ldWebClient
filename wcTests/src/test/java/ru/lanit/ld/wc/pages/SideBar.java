package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SideBar {



    private SelenideElement HomeButton = $(By.xpath("(//div[@class=\"my-3\"])[1]"));
    private SelenideElement InstructionSection = $(By.xpath("//div[@class=\"my-3 menu-item-active primary\"]"));
    private SelenideElement DocsSection = $(By.xpath("(//div[@class=\"my-3\"])[2]"));

    private SelenideElement ReportsSection = $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Главная'])[1]/following::i[4]"));
    private SelenideElement HistorySection = $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Главная'])[1]/following::i[5]"));
    private SelenideElement FavoritesSection = $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Главная'])[1]/following::i[6]"));
    private SelenideElement ArchiveSection = $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Главная'])[1]/following::i[7]"));
    private SelenideElement OptionsSection = $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Главная'])[1]/following::i[8]"));
    private SelenideElement SearchDocsSection = $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Главная'])[1]/following::i[9]"));

    public void goHome() {
        HomeButton.click();
    }

    public InstructionsSection goToInstructions() {
        InstructionSection.click();
        return new InstructionsSection();
    }

    public void goToDocs() {
        DocsSection.click();
    }

    public void goToReports() {
        ReportsSection.click();
    }

    public void goToHistory() {
        HistorySection.click();
    }

    public void goToFavorites() {
        FavoritesSection.click();
    }

    public void goToArchive() {
        ArchiveSection.click();
    }

    public void goToOptions() {
        OptionsSection.click();
    }

    public void goToSearchDocs() {
        SearchDocsSection.click();
    }

}
