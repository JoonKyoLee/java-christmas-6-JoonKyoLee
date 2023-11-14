package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.validator.NullAndEmptyValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class InputViewTest {
    private static final String NULL_VALUE = null;
    private static final String EMPTY_VALUE = "";
    private static final String SPACE_VALUE = " ";

    NullAndEmptyValidator nullAndEmptyValidator = new NullAndEmptyValidator();

    @Test
    @DisplayName("플레이어가 null 값을 입력한 경우 예외를 던진다.")
    void nullValueTest() {
        assertThatThrownBy(() -> nullAndEmptyValidator.validateNullAndEmpty(NULL_VALUE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("플레이어가 비어있는 값을 입력한 경우 예외를 던진다.")
    @CsvSource({EMPTY_VALUE, SPACE_VALUE})
    @ParameterizedTest
    void emptyValueTest(String input) {
        assertThatThrownBy(() -> nullAndEmptyValidator.validateNullAndEmpty(input))
                .isInstanceOf(IllegalArgumentException.class);

    }
}