// src/test/java/ru/netology/web/data/DataHelper.java
package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {
        // Приватный конструктор, чтобы нельзя было создать экземпляр утильного класса
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    // ИСПРАВЛЕНО: Теперь метод называется "getVerificationCodeFor" и принимает AuthInfo.
    // Класс VerificationCode тоже переименован и соответствует AuthInfo.
    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        // Здесь обычно был бы запрос к тестовому API, базе данных
        // или чтение из конфига. Для примера - хардкод.
        if ("vasya".equals(authInfo.getLogin()) && "qwerty123".equals(authInfo.getPassword())) {
            return new VerificationCode("12345"); // Возвращаем код верификации для этих учетных данных
        }
        return new VerificationCode("00000"); // Код по умолчанию или для других пользователей
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559 0000 0000 0001", "0001");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559 0000 0000 0002", "0002");
    }

    public static String generateValidAmount(int max) {
        Faker faker = new Faker(new Locale("en"));
        // Нельзя перевести 0, поэтому нижняя граница 1.
        return String.valueOf(faker.number().numberBetween(1, max));
    }

    public static String generateInvalidAmount(int max) {
        Faker faker = new Faker(new Locale("en"));
        return String.valueOf(faker.number().numberBetween(max + 1, max + 1000));
    }

    @Value // Lombok аннотация
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value // Lombok аннотация
    // ИСПРАВЛЕНО: Переименовано VerifikationCode на VerificationCode
    public static class VerificationCode {
        String code;
    }

    @Value // Lombok аннотация
    public static class CardInfo {
        String fullCardNumber; // Полный номер карты
        String lastFourDigits; // Последние 4 цифры для поиска
    }
}












