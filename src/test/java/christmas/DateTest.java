package christmas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.domain.Date;
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
}