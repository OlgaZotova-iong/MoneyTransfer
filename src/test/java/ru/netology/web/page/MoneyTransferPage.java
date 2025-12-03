package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

public class MoneyTransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public DashBoardPage validTransfer(int amount, String fromCardNumber) {
        amountField.clear(); // ОЧИЩАЕМ поле перед вводом!
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(fromCardNumber);
        transferButton.click();
        return new DashBoardPage();
    }

    public void shouldShowError(String expectedText) {
        errorNotification.shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text(expectedText), Duration.ofSeconds(5));
    }
}
