// src/test/java/ru/netology/web/page/DashBoardPage.java
package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DashBoardPage {
    private final SelenideElement dashboardHeading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list li"); // Находим все элементы карточек li с классом .list__item
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashBoardPage() {
        dashboardHeading.shouldBe(visible, Duration.ofSeconds(10)); // Явное ожидание дашборда
    }

    // Метод для получения баланса карты по ее последним 4 цифрам
    public int getCardBalance(String lastFourDigits) {
        String balanceText = cards.findBy(text(lastFourDigits)).text();
        return extractBalance(balanceText);
    }

    // Метод для выбора карты для пополнения (перехода на TransferPage)
    public TransferPage selectCardToTransfer(String lastFourDigitsOfDestinationCard) {
        // Находим элемент кнопки "Пополнить" для нужной карты
        cards.findBy(text(lastFourDigitsOfDestinationCard))
                .$("[data-test-id='action-deposit']") // Кнопка "Пополнить"
                .click();
        return new TransferPage();
    }

    // Вспомогательный метод для извлечения баланса из строки
    private int extractBalance(String text) {
        Matcher matcher = Pattern.compile(balanceStart + "(-?\\d+)" + balanceFinish).matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalStateException("Не удалось извлечь баланс из текста: " + text);
    }

    // Метод для получения информации о карте по ее индексу (для удобства)
    public SelenideElement getCardElementByIndex(int index) {
        return cards.get(index);
    }
}



