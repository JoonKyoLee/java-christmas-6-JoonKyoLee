package christmas;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Customer;
import christmas.domain.Date;
import christmas.domain.Order;
import christmas.validator.MenuValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CustomerTest {
    Customer customer;

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
    @DisplayName("각 날짜와 총주문 금액에 맞게 할인이 적용된다.")
    @MethodSource("generateMenuToGetDiscountAmount")
    void calculateDiscountAmountTest(
            Map<String, Integer> menu, int date, Map<Integer, Integer> expected
    ) {
        customer = new Customer(new Date(date), new Order(new MenuValidator(), menu));

        Map<Integer, Integer> discount = customer.applyDiscountAmount();

        assertThat(discount).isEqualTo(expected);
    }

    static Stream<Arguments> generateMenuToGetDiscountAmount() {
        return Stream.of(
                Arguments.of(
                        hasManyMenus
                        , 1
                        , Map.of(
                                1, 1_000,
                                3, 10_115,
                                5, 25_000
                        )
                ),
                Arguments.of(
                        hasLessMenus
                        , 1
                        , Map.of(
                                1, 1_000,
                                3, 0
                        )
                ),
                Arguments.of(
                        hasManyMenus
                        , 24
                        , Map.of(
                                1, 3_300,
                                2, 12_138,
                                4, 1_000,
                                5, 25_000
                        )
                ),
                Arguments.of(
                        hasLessMenus
                        , 24
                        , Map.of(
                                1, 3_300,
                                2, 0,
                                4, 1_000
                        )
                )
        );
    }

    @ParameterizedTest
    @DisplayName("각 날짜와 총주문 금액에 맞게 총혜택 금액을 잘 계산한다.")
    @MethodSource("generateMenuToGetTotalDiscountAmount")
    void calculateTotalDiscountPriceTest(
            Map<String, Integer> menu, int date, int expected
    ) {
        customer = new Customer(new Date(date), new Order(new MenuValidator(), menu));

        Map<Integer, Integer> discount = customer.applyDiscountAmount();
        int totalDiscount = customer.makeTotalDiscountPrice(discount);

        assertThat(totalDiscount).isEqualTo(expected);
    }

    static Stream<Arguments> generateMenuToGetTotalDiscountAmount() {
        return Stream.of(
                Arguments.of(
                        hasManyMenus
                        , 1
                        , 36_115
                ),
                Arguments.of(
                        hasLessMenus
                        , 1
                        , 1_000
                ),
                Arguments.of(
                        hasManyMenus
                        , 24
                        , 41_438
                ),
                Arguments.of(
                        hasLessMenus
                        , 24
                        , 4_300
                )
        );
    }

    @ParameterizedTest
    @DisplayName("각 날짜와 총주문 금액에 맞게 총혜택 금액을 잘 계산한다.")
    @MethodSource("generateMenuToGetExpectedPrice")
    void calculateExpectedPriceTest(
            Map<String, Integer> menu, int date, int expected
    ) {
        customer = new Customer(new Date(date), new Order(new MenuValidator(), menu));

        Map<Integer, Integer> discount = customer.applyDiscountAmount();
        int totalDiscount = customer.makeTotalDiscountPrice(discount);

        int priceAfterApplyingDiscount =
                customer.makeExpectedPrice(customer.makeTotalPrice(), totalDiscount);

        assertThat(priceAfterApplyingDiscount).isEqualTo(expected);
    }

    static Stream<Arguments> generateMenuToGetExpectedPrice() {
        return Stream.of(
                Arguments.of(
                        hasManyMenus
                        , 1
                        , 295_885
                ),
                Arguments.of(
                        hasLessMenus
                        , 1
                        , 14_000
                ),
                Arguments.of(
                        hasManyMenus
                        , 24
                        , 290_562
                ),
                Arguments.of(
                        hasLessMenus
                        , 24
                        , 10_700
                )
        );
    }
}