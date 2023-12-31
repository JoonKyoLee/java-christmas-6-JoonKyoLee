package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.domain.customer.Date;
import christmas.exception.ErrorMessage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class DateTest {
    Date date;

    @DisplayName("범위를 벗어난 날짜를 입력하면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 32, 34, -1})
    void inValidDateTest(int dateNumber) {
        assertThatThrownBy(() -> new Date(dateNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.DATE_FORMAT_IS_NOT_VALID.getErrorMessage());
    }

    @DisplayName("범위에 맞는 날짜를 입력하면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 31, 25, 4})
    void validDateTest(int dateNumber) {
        assertDoesNotThrow(() -> new Date(dateNumber));
    }

    @DisplayName("날짜를 입력하면 날짜에 맞는 요일 인덱스를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,5", "25,1", "16,6", "31,7"}, delimiter = ',')
    void dayOfWeekTest(int dateNumber, int expected) {
        this.date = new Date(dateNumber);

        int dayOfWeek = date.calculateDayOfWeek();

        assertThat(dayOfWeek).isEqualTo(expected);
    }

    @DisplayName("날짜를 입력했을 때 크리스마스 디데이 할인이 적용되면 true, 적용되지 않으면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,true", "25,true", "16,true", "31,false"}, delimiter = ',')
    void applyDDayDiscountTest(int dateNumber, boolean expected) {
        this.date = new Date(dateNumber);

        boolean canApplyEvent = date.canApplyChristmasDDayDiscount();

        assertThat(canApplyEvent).isEqualTo(expected);
    }

    @DisplayName("날짜를 입력했을 때 요일이 평일이면 true, 아니면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,false", "25,true", "16,false", "22,false"}, delimiter = ',')
    void weekdayTest(int dateNumber, boolean expected) {
        this.date = new Date(dateNumber);

        boolean isWeekday = date.isWeekday();

        assertThat(isWeekday).isEqualTo(expected);
    }

    @DisplayName("날짜를 입력했을 때 요일이 주말이면 true, 아니면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,true", "25,false", "16,true", "22,true"}, delimiter = ',')
    void weekendTest(int dateNumber, boolean expected) {
        this.date = new Date(dateNumber);

        boolean isWeekend = date.isWeekend();

        assertThat(isWeekend).isEqualTo(expected);
    }

    @DisplayName("날짜를 입력했을 때 달력에 있는 그 날짜에 별이 존재하면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,false", "25,true", "16,false", "31,true"}, delimiter = ',')
    void hasStarTest(int dateNumber, boolean expected) {
        this.date = new Date(dateNumber);

        boolean hasStar = date.hasStarInCalendar();

        assertThat(hasStar).isEqualTo(expected);
    }
}