package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RechargingPage {
    private SelenideElement sumField = $("[type=text]");
    private SelenideElement fromField = $("[type=tel]");
    private SelenideElement confirmButton = $$(".button__content").find(exactText("Пополнить"));

    public DashboardPage submitTransfer(int transfer, DataHelper.CardInfo info) {
        sumField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        sumField.setValue(String.valueOf(transfer));
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        fromField.setValue(info.getCardNumber());
        confirmButton.click();
        return new DashboardPage();
    }
}
