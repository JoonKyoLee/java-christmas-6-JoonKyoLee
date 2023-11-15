package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.exception.ErrorMessage;
import christmas.validator.ConvertInputValidator;
import christmas.validator.MenuValidator;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MenuValidatorTest {
    ConvertInputValidator convertInputValidator = new ConvertInputValidator();
    MenuValidator menuValidator = new MenuValidator();
    Map<String, Integer> menus;

    @ParameterizedTest
    @DisplayName("주문 중 메뉴의 개수가 1보다 작은 경우에는 예외를 던진다.")
    @MethodSource("generateLessThanUnitQuantityOfEachMenuInput")
    void inLessThanUnitQuantityOfEachMenuTest(String input) {
        menus = convertInputValidator.convertOrder(input);

        assertThatThrownBy(() -> menuValidator.validate(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID.getErrorMessage());
    }

    static Stream<Arguments> generateLessThanUnitQuantityOfEachMenuInput() {
        return Stream.of(
                Arguments.of("딸기케이크-0,아이스크림-1"),
                Arguments.of("바비큐립-0"),
                Arguments.of("티본스테이크-0,딸기케이크-2"),
                Arguments.of("초코케이크-2,크리스마스파스타-0"),
                Arguments.of("초코케이크-1,타파스-0"),
                Arguments.of("양송이수프-1,해산물파스타-0,티본스테이크-1,아이스크림-0")
        );
    }

    @ParameterizedTest
    @DisplayName("주문 중 총 메뉴의 개수가 20보다 클 경우에는 예외를 던진다.")
    @MethodSource("generateOverThanUnitQuantityOfTotalMenuInput")
    void inOverThanUnitQuantityOfTotalMenuTest(String input) {
        menus = convertInputValidator.convertOrder(input);

        assertThatThrownBy(() -> menuValidator.validate(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID.getErrorMessage());
    }

    static Stream<Arguments> generateOverThanUnitQuantityOfTotalMenuInput() {
        return Stream.of(
                Arguments.of("딸기케이크-20,아이스크림-1"),
                Arguments.of("바비큐립-30"),
                Arguments.of("티본스테이크-19,딸기케이크-2"),
                Arguments.of("초코케이크-2,크리스마스파스타-23"),
                Arguments.of("초코케이크-21,타파스-10"),
                Arguments.of("양송이수프-1,해산물파스타-20,티본스테이크-1,아이스크림-10")
        );
    }

    @ParameterizedTest
    @DisplayName("주문 중 메뉴의 이름이 메뉴판에 존재하지 않으면 예외를 던진다.")
    @MethodSource("generateInputMenuDoesNotAppearInMenuListInput")
    void menuDoesNotAppearInMenuListTest(String input) {
        menus = convertInputValidator.convertOrder(input);

        assertThatThrownBy(() -> menuValidator.validate(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID.getErrorMessage());
    }

    static Stream<Arguments> generateInputMenuDoesNotAppearInMenuListInput() {
        return Stream.of(
                Arguments.of("딸기케이크-1,아이스크림-1"),
                Arguments.of("폭립-1"),
                Arguments.of("티본스테이크-1,딸기케이크-2"),
                Arguments.of("코카콜라-2,크리스마스파스타-3")
        );
    }

    @ParameterizedTest
    @DisplayName("주문이 음료로만 구성되어 있다면 예외를 던진다.")
    @MethodSource("generateOnlyBeverageInput")
    void menuConsistsOnlyBeverageTest(String input) {
        menus = convertInputValidator.convertOrder(input);

        assertThatThrownBy(() -> menuValidator.validate(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID.getErrorMessage());
    }

    static Stream<Arguments> generateOnlyBeverageInput() {
        return Stream.of(
                Arguments.of("제로콜라-1,레드와인-1"),
                Arguments.of("레드와인-1"),
                Arguments.of("샴페인-1,제로콜라-2,레드와인-3")
        );
    }

    @ParameterizedTest
    @DisplayName("주문이 올바른 경우에는 예외를 발생하지 않는다.")
    @MethodSource("generateValidOrderInput")
    void ValidOrderInputTypeTest(String input) {
        menus = convertInputValidator.convertOrder(input);

        assertDoesNotThrow(() -> menuValidator.validate(menus));
    }

    static Stream<Arguments> generateValidOrderInput() {
        return Stream.of(
                Arguments.of("아이스크림-2"),
                Arguments.of("바비큐립-3"),
                Arguments.of("티본스테이크-1,초코케이크-2"),
                Arguments.of("초코케이크-2,크리스마스파스타-3"),
                Arguments.of("초코케이크-1,타파스-10"),
                Arguments.of("양송이수프-1,해산물파스타-1,티본스테이크-1,아이스크림-1")
        );
    }
}