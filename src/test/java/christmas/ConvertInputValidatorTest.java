package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.validator.ConvertInputValidator;
import christmas.exception.ErrorMessage;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ConvertInputValidatorTest {
    ConvertInputValidator convertInputValidator = new ConvertInputValidator();

    @DisplayName("날짜 입력 형식이 정수가 아닌 경우에 예외를 던진다.")
    @ValueSource(strings = {"a", "1e", "애피타이저-1", "우테코", "프리코스가", "끝이라니"})
    @ParameterizedTest
    void nonIntegerTypeDateTest(String input) {
        assertThatThrownBy(() -> convertInputValidator.convertDate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.DATE_FORMAT_IS_NOT_VALID.getErrorMessage());
    }

    @DisplayName("날짜 입력 형식이 정수일 경우에는 예외가 발생하지 않는다.")
    @ValueSource(strings = {"1", "0", "3", "32", "4", "10"})
    @ParameterizedTest
    void integerTypeDateTest(String input) {
        assertDoesNotThrow(() -> convertInputValidator.convertDate(input));
    }

    @ParameterizedTest
    @DisplayName("주문 입력 형식이 올바르지 않을 경우에 예외를 던진다.")
    @MethodSource("generateInvalidFormOfOrderInput")
    void inValidOrderInputTypeTest(String input) {
        assertThatThrownBy(() -> convertInputValidator.convertOrder(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID.getErrorMessage());
    }

    static Stream<Arguments> generateInvalidFormOfOrderInput() {
        return Stream.of(
                Arguments.of("딸기케이크-1,,1"),
                Arguments.of("초코케이크!1,바비큐립-1"),
                Arguments.of("--티본스테이크1,딸기케이크2"),
                Arguments.of("초코케이크|2,크리스마스파스타|3"),
                Arguments.of("초코케이크-1, 타파스-10"),
                Arguments.of("양송이수프|1|해산물파스타|1|티본스테이크|1|아이스크림|1"),
                Arguments.of(",양송이수프-1,해산물파스타-1"),
                Arguments.of("양송이수프,1-해산물파스타-2")
        );
    }

    @ParameterizedTest
    @DisplayName("주문 입력 형식이 올바른 경우에는 예외를 발생하지 않는다.")
    @MethodSource("generateValidFormOfOrderInput")
    void ValidOrderInputTypeTest(String input) {
        assertDoesNotThrow(() -> convertInputValidator.convertOrder(input));
    }

    static Stream<Arguments> generateValidFormOfOrderInput() {
        return Stream.of(
                Arguments.of("딸기케이크-1,아이스크림-1"),
                Arguments.of("바비큐립-1"),
                Arguments.of("티본스테이크-1,딸기케이크-2"),
                Arguments.of("초코케이크-2,크리스마스파스타-3"),
                Arguments.of("초코케이크-1,타파스-10"),
                Arguments.of("양송이수프-1,해산물파스타-1,티본스테이크-1,아이스크림-1")
        );
    }

    @ParameterizedTest
    @DisplayName("주문 중 메뉴의 개수가 정수가 아닌 경우에는 예외를 던진다.")
    @MethodSource("generateNonIntegerQuantityOfMenuInput")
    void nonIntegerQuantityOfMenuTest(String input) {
        assertThatThrownBy(() -> convertInputValidator.convertOrder(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID.getErrorMessage());
    }

    static Stream<Arguments> generateNonIntegerQuantityOfMenuInput() {
        return Stream.of(
                Arguments.of("우테코프리코스-마지막,미션-끝"),
                Arguments.of("바비큐립-a"),
                Arguments.of("티본스테이크-three,딸기케이크-2"),
                Arguments.of("초코케이크-2d,크리스마스파스타-3"),
                Arguments.of("초코케이크-one,타파스-ten"),
                Arguments.of("양송이수프-1,해산물파스타-1,티본스테이크-1,아이스크림-맛있겠다")
        );
    }

    @ParameterizedTest
    @DisplayName("주문 중 메뉴의 이름이 중복되는 경우에는 예외를 던진다.")
    @MethodSource("generateDuplicatedMenuNameInput")
    void DuplicatedMenuNameTest(String input) {
        assertThatThrownBy(() -> convertInputValidator.convertOrder(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID.getErrorMessage());
    }

    static Stream<Arguments> generateDuplicatedMenuNameInput() {
        return Stream.of(
                Arguments.of("티본스테이크-3,티본스테이크-4"),
                Arguments.of("바비큐립-1,타파스-3,바비큐립-2,타파스-4"),
                Arguments.of("초코케이크-2,크리스마스파스타-3,초코케이크-5"),
                Arguments.of("양송이수프-1,해산물파스타-1,티본스테이크-1,양송이수프-2")
        );
    }
}