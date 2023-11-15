package christmas;

import christmas.domain.Order;

import christmas.validator.MenuValidator;
import java.util.HashMap;
import java.util.Map;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderTest {
    Order order;

    static Map<String, Integer> hasManyMenus = new HashMap<>() {{
        put("양송이수프", 1);
        put("시저샐러드", 1);
        put("티본스테이크", 2);
        put("바비큐립", 1);
        put("해산물파스타", 2);
        put("초코케이크", 2);
        put("아이스크림", 4);
        put("제로콜라", 3);
    }};

    static Map<String, Integer> hasLessMenus = new HashMap<>() {{
        put("양송이수프", 1);
        put("제로콜라", 3);
    }};

    @ParameterizedTest
    @DisplayName("혜택 전 총주문 금액을 계산한 결과가 옳은지 확인한다.")
    @MethodSource("generateMenuToGetTotalAmount")
    void calculateTotalAmountTest(Map<String, Integer> menu, int expected) {
        order = new Order(new MenuValidator(), menu);

        int totalAmount = order.calculateTotalAmountBeforeDiscount();

        Assertions.assertThat(totalAmount).isEqualTo(expected);
    }

    static Stream<Arguments> generateMenuToGetTotalAmount() {
        return Stream.of(
                Arguments.of(
                        hasManyMenus,
                        307_000
                ),
                Arguments.of(
                        hasLessMenus,
                        15_000
                )
        );
    }
}