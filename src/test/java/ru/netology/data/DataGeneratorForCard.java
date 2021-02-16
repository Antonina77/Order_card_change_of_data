package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGeneratorForCard {
    private DataGeneratorForCard() {
    }

    @Data
    @RequiredArgsConstructor
    public static class CardInfo {
        private final String fullName;
        private final String city;
        private final String Phone;
        private final String DateOfMeeting;
        private final String RescheduledDate;
    }

    public static CardInfo getCardInfo() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Faker faker = new Faker(new Locale("ru"));
        return new CardInfo(
                faker.name().fullName(),
                faker.address().city(),
                faker.phoneNumber().phoneNumber(),
                LocalDate.now().plusDays(3).format(df),
                LocalDate.now().plusDays(5).format(df));
    }
}

