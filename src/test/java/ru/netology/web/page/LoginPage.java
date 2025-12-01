// ru/netology/web/page/LoginPage.java
package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper; // Импортируем DataHelper для использования AuthInfo

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]"); //Добавил селектор

    // Метод для выполнения успешного входа
    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    // Метод для проверки видимости уведомления об ошибке авторизации
    public void checkAuthErrorNotificationVisible() {
        errorNotification.shouldBe(visible);
    }

    // Новый метод для проверки текста ошибки авторизации
    public void checkAuthErrorNotificationText(String expectedText) {
        errorNotification.shouldBe(visible).shouldHave(exactText(expectedText));
    }
}







