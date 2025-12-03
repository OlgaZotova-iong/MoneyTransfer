package ru.netology.web.test;

import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.*;

class MoneyTransferTest {
    DashBoardPage dashboardPage;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Успешный перевод с первой карты на вторую")
    void shouldTransferMoneyFromFirstToSecondCard() {
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();

        int amount = DataHelper.generateValidAmount(dashboardPage.getCardBalance(firstCard.getNumber()));
        int initialFirstBalance = dashboardPage.getCardBalance(firstCard.getNumber());
        int initialSecondBalance = dashboardPage.getCardBalance(secondCard.getNumber());

        var transferPage = dashboardPage.selectCardToTransfer(secondCard.getNumber());
        dashboardPage = transferPage.validTransfer(amount, firstCard.getNumber());

        int expectedFirstBalance = initialFirstBalance - amount;
        int expectedSecondBalance = initialSecondBalance + amount;

        Assertions.assertEquals(expectedFirstBalance, dashboardPage.getCardBalance(firstCard.getNumber()));
        Assertions.assertEquals(expectedSecondBalance, dashboardPage.getCardBalance(secondCard.getNumber()));
    }

    @Test
    @DisplayName("Перевод нулевой суммы невозможен")
    void shouldNotTransferZeroAmount() {
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();

        int initialFirstBalance = dashboardPage.getCardBalance(firstCard.getNumber());
        int initialSecondBalance = dashboardPage.getCardBalance(secondCard.getNumber());

        var transferPage = dashboardPage.selectCardToTransfer(secondCard.getNumber());
        dashboardPage = transferPage.validTransfer(0, firstCard.getNumber());

        // Балансы не должны измениться
        Assertions.assertEquals(initialFirstBalance, dashboardPage.getCardBalance(firstCard.getNumber()));
        Assertions.assertEquals(initialSecondBalance, dashboardPage.getCardBalance(secondCard.getNumber()));
    }


    @Test
    @DisplayName("Перевод суммы, превышающей баланс невозможен")
    void shouldNotTransferAmountMoreThanBalance() {
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();

        int firstBalance = dashboardPage.getCardBalance(firstCard.getNumber());
        int secondBalance = dashboardPage.getCardBalance(secondCard.getNumber());
        int invalidAmount = DataHelper.generateInvalidAmount(firstBalance);

        var transferPage = dashboardPage.selectCardToTransfer(secondCard.getNumber());
        dashboardPage = transferPage.validTransfer(invalidAmount, firstCard.getNumber());

        // Балансы не должны измениться
        Assertions.assertEquals(firstBalance, dashboardPage.getCardBalance(firstCard.getNumber()));
        Assertions.assertEquals(secondBalance, dashboardPage.getCardBalance(secondCard.getNumber()));
    }
}








