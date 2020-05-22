package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private SelenideElement depositButton0001 = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"] [type='button']");
    private SelenideElement depositButton0002 = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"] [type='button']");
    private static SelenideElement balance0001 = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");
    private static SelenideElement balance0002 = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]");
    private SelenideElement error = $("[data-test-id=error-notification]");

    public RechargingPage deposit0001() {
        depositButton0001.click();
        return new RechargingPage();
    }

    public RechargingPage deposit0002() {
        depositButton0002.click();
        return new RechargingPage();
    }

    public static int getCurrentBalanceOfFirstCard() {
        String selectedValue = balance0001.getText();
        String balanceOfFirstCard = selectedValue.substring(29, selectedValue.indexOf(" ", 29));
        return Integer.parseInt(balanceOfFirstCard);
    }

    public static int getCurrentBalanceOfSecondCard() {
        String selectedValue = balance0002.getText();
        String balanceOfFirstCard = selectedValue.substring(29, selectedValue.indexOf(" ", 29));
        return Integer.parseInt(balanceOfFirstCard);
    }

    public void getError() {
        error.shouldBe(visible);
    }
}
