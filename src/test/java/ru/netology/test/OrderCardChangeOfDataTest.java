package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGeneratorForCard;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGeneratorForCard.getCardInfo;

public class OrderCardChangeOfDataTest {
    DataGeneratorForCard.CardInfo user = getCardInfo();

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulDateChange() {

        $("[placeholder='Город']").setValue(user.getCity());
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(user.getDateOfMeeting());
        $("[name='name']").setValue(user.getFullName());
        $("[name='phone']").setValue(user.getPhone());
        $(".checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification'] > .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldBe(Condition.visible, Duration.ofMillis(5000));
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + user.getDateOfMeeting()));
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(user.getRescheduledDate());
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] > .notification__title")
                .shouldHave(exactText("Необходимо подтверждение"))
                .shouldBe(Condition.visible);
        $("[data-test-id='replan-notification'] > .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible, Duration.ofMillis(5000));
        $(withText("Перепланировать")).click();
        $("[data-test-id='success-notification'] > .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldBe(Condition.visible, Duration.ofMillis(5000));
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + user.getRescheduledDate()))
                .shouldBe(Condition.visible,Duration.ofMillis(5000));
    }
}
