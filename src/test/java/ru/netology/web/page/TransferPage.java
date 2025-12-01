// src/test/java/ru/netology/web/page/TransferPage.java
package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement amountInput = $("[data-test-id=amount] input");
    private final SelenideElement fromCardInput = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']"); // Селектор для ошибки

    public TransferPage() {
        amountInput.shouldBe(visible, Duration.ofSeconds(10));
        fromCardInput.shouldBe(visible, Duration.ofSeconds(10));
        transferButton.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void transferMoney(int amount, String fromCardNumber) { // Изменил fromCard на fromCardNumber для ясности
        amountInput.setValue(String.valueOf(amount));
        // Для поля "Откуда" нужно передавать *полный* номер карты, если UI этого требует.
        // Или только последние 4 цифры, если UI сам подставляет.
        // В вашем тесте "5559 **** **** **** 0001" - это формат из UI.
        fromCardInput.setValue(fromCardNumber);
        transferButton.click();
    }

    public void checkErrorNotificationVisability() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(5));
    }
}





