package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public DashBoardPage validVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        return new DashBoardPage();
    }
}







