package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.validator.NullAndEmptyValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NullAndEmptyValidatorTest {
    private static final String NULL_VALUE = null;
    private static final String EMPTY_VALUE = "";
    private static final String SPACE_VALUE = " ";

    NullAndEmptyValidator nullAndEmptyValidator = new NullAndEmptyValidator();

    @Test
    @DisplayName("고객이 null 값을 입력한 경우 예외를 던진다.")
    void nullValueTest() {
        assertThatThrownBy(() -> nullAndEmptyValidator.validateNullAndEmpty(NULL_VALUE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("고객이 비어있는 값을 입력한 경우 예외를 던진다.")
    @ValueSource(strings = {EMPTY_VALUE, SPACE_VALUE})
    @ParameterizedTest
    void emptyValueTest(String input) {
        assertThatThrownBy(() -> nullAndEmptyValidator.validateNullAndEmpty(input))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("고객이 null 값 또는 비어있지 않은 값을 입력할 경우 예외가 발생하지 않는다.")
    @ValueSource(strings = {"초코케이크-1", "아이스크림-2", "초코케이크", "3"})
    @ParameterizedTest
    void notNullAndNotEmptyValueTest(String input) {
        assertDoesNotThrow(() -> nullAndEmptyValidator.validateNullAndEmpty(input));
    }
}