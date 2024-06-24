package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    String generateDate(int daysToAdd, String pattern) {
        return LocalDate.now().plusDays(daysToAdd)
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSubmittingDeliveryCardApplicationform() {
        $x("//input[@type='text']").setValue("Уфа");
        $x("//input[@type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@type='tel']").setValue(generateDate(3, "dd.MM.yyyy"));
        $x("//input[@name='name']").setValue("Щеглов Иван");
        $x("//input[@name='phone']").setValue("+79278883388");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $x("//div[@data-test-id='notification']/div[@class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно забронирована на "
                        + generateDate(3, "dd.MM.yyyy")), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void  shouldValidationCityFieldDeliveryCardApplicationForm() {
        $("[data-test-id='city'] .input__control").setValue("Денпасар");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] [name='name']").setValue("Щеглов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("+79278883388");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
        $("[data-test-id='city'] .input__sub")
                .shouldHave(Condition.text("Доставка в выбранный город недоступна"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldValidationDateFieldDeliveryCardApplicationForm() {
        $("[data-test-id='city'] .input__control").setValue("Уфа");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(2, "dd.MM.yyyy"));
        $("[data-test-id='name'] [name='name']").setValue("Щеглов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("+79278883388");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub")
                .shouldHave(Condition.text("Заказ на выбранную дату невозможен"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldValidationLastFirstNameFieldDeliveryCardApplicationForm() {
        $("[data-test-id='city'] .input__control").setValue("Уфа");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] [name='name']").setValue("Shcheglov Ivan");
        $("[data-test-id='phone'] [name='phone']").setValue("+79278883388");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub")
                .shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldValidationEmptyLastFirstNameFieldDeliveryCardApplicationForm() {
        $("[data-test-id='city'] .input__control").setValue("Уфа");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] [name='name']").setValue("");
        $("[data-test-id='phone'] [name='phone']").setValue("+79278883388");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldValidationTelephoneFieldDeliveryCardApplicationForm() {
        $("[data-test-id='city'] .input__control").setValue("Уфа");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] [name='name']").setValue("Щеглов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("++9278883388");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldValidationEmptyTelephoneFieldDeliveryCardApplicationForm() {
        $("[data-test-id='city'] .input__control").setValue("Уфа");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] [name='name']").setValue("Щеглов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldValidationCheckboxFieldDeliveryCardApplicationForm() {
        $("[data-test-id='city'] .input__control").setValue("Уфа");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] [name='name']").setValue("Щеглов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("+79278883388");
        $(".button").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text")
                .shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"))
                .shouldBe(Condition.visible);
    }
}
