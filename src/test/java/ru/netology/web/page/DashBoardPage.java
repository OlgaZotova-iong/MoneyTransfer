package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class DashBoardPage {
    private ElementsCollection cards = $$(".list li");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    public DashBoardPage() {
        $("[data-test-id=dashboard]").shouldBe(visible, Duration.ofSeconds(5));
    }

    public int getCardBalance(String cardNumber) {
        SelenideElement card = cards.findBy(text(cardNumber.substring(cardNumber.length()-4)));
        card.shouldBe(visible, Duration.ofSeconds(5));
        String text = card.text();
        return extractBalance(text);
    }

    public MoneyTransferPage selectCardToTransfer(String cardNumber) {
        SelenideElement card = cards.findBy(text(cardNumber.substring(cardNumber.length()-4)));
        card.$("button").click();
        return new MoneyTransferPage();
    }

    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);
        String value = text.substring(start + balanceStart.length(), finish).replaceAll("\\s", "");
        return Integer.parseInt(value);
    }
}



