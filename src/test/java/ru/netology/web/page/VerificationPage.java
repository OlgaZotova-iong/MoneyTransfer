// ru/netology/web/page/VerificationPage.java
package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement invalidCodeNotification = $("[data-test-id=error-notification]"); // Добавлен селектор для сообщения об ошибке

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashBoardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashBoardPage();
    }


    public void verify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
    }

    public void shouldBeVisibleErrorNotification() {
        invalidCodeNotification.shouldBe(visible);
    }
}







