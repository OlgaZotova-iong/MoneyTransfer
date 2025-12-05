package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {}

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String getVerificationCodeFor(AuthInfo authInfo) {
        return "12345";
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559 0000 0000 0001", "id:1");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559 0000 0000 0002", "id:2");
    }

    public static int generateValidAmount(int max) {
        int absMax = Math.abs(max); // Используем модуль max
        if (absMax < 1) {
            absMax = 1; // Если max < 1, задаём минимум как 1
        }
        Faker faker = new Faker(new Locale("en"));
        // Нельзя перевести 0, поэтому нижняя граница 1.
        return faker.number().numberBetween(1, absMax);
    }

    public static int generateInvalidAmount(int max) {
        int absMax = Math.abs(max); // Используем модуль max
        if (absMax < 1) {
            absMax = 1; // Если max < 1, задаём минимум как 1
        }
        Faker faker = new Faker(new Locale("en"));
        return faker.number().numberBetween(absMax + 1, absMax + 1000);
    }

    public static AuthInfo getInvalidAuthInfo() {
        return new AuthInfo("invalid", "wrongpassword");
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class CardInfo {
        String number;
        String id;
    }
}












