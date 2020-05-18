package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private ElementsCollection depositButtons = $$("[data-test-id=action-deposit]");
    private SelenideElement depositButton0001 = depositButtons.first();
    private SelenideElement depositButton0002 = depositButtons.last();

    private SelenideElement reloadButton = $("[data-test-id=action-reload]");

    private ElementsCollection accounts = $$("[class='list__item']");
    private SelenideElement balance0001 = accounts.first();
    private SelenideElement balance0002 = accounts.last();

    private SelenideElement sumField = $("[type=text]");
    private SelenideElement fromField = $("[type=tel]");
    private SelenideElement confirmButton = $$(".button__content").find(exactText("Пополнить"));
    private SelenideElement backButton = $("[data-test-id=action-cancel]");

    private SelenideElement error = $("[data-test-id=error-notification]");


    public DashboardPage deposit0001() {
        depositButton0001.click();
        return new DashboardPage();
    }

    public DashboardPage deposit0002() {
        depositButton0002.click();
        return new DashboardPage();
    }

    public DashboardPage submitTransfer(int transfer, DataHelper.CardInfo info) {
        sumField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        sumField.setValue(String.valueOf(transfer));
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        fromField.setValue(info.getCardNumber());
        confirmButton.click();
        return new DashboardPage();
    }

    public DashboardPage reload() {
        reloadButton.click();
        return new DashboardPage();
    }

    public DashboardPage getBalance(int balance1, int balance2) {
        reload();
        balance0001.shouldHave(Condition.text(String.valueOf(balance1)));
        balance0002.shouldHave(Condition.text(String.valueOf(balance2)));
        return new DashboardPage();
    }

    public DashboardPage getBack() {
        backButton.click();
        return new DashboardPage();
    }

    public DashboardPage getError() {
        error.shouldBe(visible);
        return new DashboardPage();
    }
}
