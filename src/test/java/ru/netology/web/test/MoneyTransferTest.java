package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashBoardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    private LoginPage loginPage;
    private VerificationPage verificationPage;
    private DashBoardPage dashboardPage;

    @BeforeEach
    void setup() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;
        Configuration.timeout = 10000;

        open("http://localhost:9999");
        loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        verificationPage = loginPage.validLogin(authInfo);

        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstCard() {
        DataHelper.CardInfo firstCard = DataHelper.getFirstCardInfo();
        DataHelper.CardInfo secondCard = DataHelper.getSecondCardInfo();

        int balanceFirstBefore = dashboardPage.getCardBalance(firstCard.getLastFourDigits());
        int balanceSecondBefore = dashboardPage.getCardBalance(secondCard.getLastFourDigits());

        var transferPage = dashboardPage.selectCardToTransfer(firstCard.getLastFourDigits());

        int amount = 100;
        transferPage.transferMoney(amount, secondCard.getFullCardNumber());

        // После successful transfer ожидается возврат на DashboardPage,
        // создаём новый объект DashboardPage для получения актуальных балансов
        dashboardPage = new DashBoardPage();

        int balanceFirstAfter = dashboardPage.getCardBalance(firstCard.getLastFourDigits());
        int balanceSecondAfter = dashboardPage.getCardBalance(secondCard.getLastFourDigits());

        assertEquals(balanceFirstBefore + amount, balanceFirstAfter, "Баланс первой карты должен увеличиться на " + amount);
        assertEquals(balanceSecondBefore - amount, balanceSecondAfter, "Баланс второй карты должен уменьшиться на " + amount);
    }

    @Test
    void shouldNotTransferMoneyIfAmountExceedsCardBalanceAndRedirectToLogin() {
        DataHelper.CardInfo firstCard = DataHelper.getFirstCardInfo();
        DataHelper.CardInfo secondCard = DataHelper.getSecondCardInfo();

        int balanceFirstBefore = dashboardPage.getCardBalance(firstCard.getLastFourDigits());
        int balanceSecondBefore = dashboardPage.getCardBalance(secondCard.getLastFourDigits());

        int amount = balanceSecondBefore + 100; // Сумма больше баланса карты на 100

        var transferPage = dashboardPage.selectCardToTransfer(firstCard.getLastFourDigits());
        transferPage.transferMoney(amount, secondCard.getFullCardNumber());

        // Проверяем редирект на страницу логина и текст ошибки.
        LoginPage loginPageAfterError = new LoginPage();
        loginPageAfterError.checkAuthErrorNotificationText("Ошибка! Неверно указан логин или пароль");

        // Так как произошел редирект на страницу логина, проверить балансы без повторного входа.

        // Re-login
        loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);

        int balanceFirstAfter = dashboardPage.getCardBalance(firstCard.getLastFourDigits());
        int balanceSecondAfter = dashboardPage.getCardBalance(secondCard.getLastFourDigits());

        assertEquals(balanceFirstBefore, balanceFirstAfter, "Баланс первой карты не должен измениться");
        assertEquals(balanceSecondBefore, balanceSecondAfter, "Баланс второй карты не должен измениться");
    }

    @Test
    void shouldNotTransferMoneyIfAmountIsZeroAndRedirectToLogin() {
        DataHelper.CardInfo firstCard = DataHelper.getFirstCardInfo();
        DataHelper.CardInfo secondCard = DataHelper.getSecondCardInfo();

        int balanceFirstBefore = dashboardPage.getCardBalance(firstCard.getLastFourDigits());
        int balanceSecondBefore = dashboardPage.getCardBalance(secondCard.getLastFourDigits());

        int amount = 0; // Нулевая сумма

        var transferPage = dashboardPage.selectCardToTransfer(firstCard.getLastFourDigits());
        transferPage.transferMoney(amount, secondCard.getFullCardNumber());

        // Проверяем редирект на страницу логина и текст ошибки.
        LoginPage loginPageAfterError = new LoginPage();
        loginPageAfterError.checkAuthErrorNotificationText("Ошибка! Неверно указан логин или пароль");

        // Re-login
        loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);


        int balanceFirstAfter = dashboardPage.getCardBalance(firstCard.getLastFourDigits());
        int balanceSecondAfter = dashboardPage.getCardBalance(secondCard.getLastFourDigits());

        assertEquals(balanceFirstBefore, balanceFirstAfter, "Баланс первой карты не должен измениться");
        assertEquals(balanceSecondBefore, balanceSecondAfter, "Баланс второй карты не должен измениться");

    }

    @Test
    void shouldNotTransferMoneyIfAmountIsNegativeAndRedirectToLogin() {
        DataHelper.CardInfo firstCard = DataHelper.getFirstCardInfo();
        DataHelper.CardInfo secondCard = DataHelper.getSecondCardInfo();

        int balanceFirstBefore = dashboardPage.getCardBalance(firstCard.getLastFourDigits());
        int balanceSecondBefore = dashboardPage.getCardBalance(secondCard.getLastFourDigits());

        int amount = -100; // Отрицательная сумма

        var transferPage = dashboardPage.selectCardToTransfer(firstCard.getLastFourDigits());
        transferPage.transferMoney(amount, secondCard.getFullCardNumber());

        // Проверяем редирект на страницу логина и текст ошибки.
        LoginPage loginPageAfterError = new LoginPage();
        loginPageAfterError.checkAuthErrorNotificationText("Ошибка! Неверно указан логин или пароль");

        // Re-login
        loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);

        int balanceFirstAfter = dashboardPage.getCardBalance(firstCard.getLastFourDigits());
        int balanceSecondAfter = dashboardPage.getCardBalance(secondCard.getLastFourDigits());

        assertEquals(balanceFirstBefore, balanceFirstAfter, "Баланс первой карты не должен измениться");
        assertEquals(balanceSecondBefore, balanceSecondAfter, "Баланс второй карты не должен измениться");
    }
}








