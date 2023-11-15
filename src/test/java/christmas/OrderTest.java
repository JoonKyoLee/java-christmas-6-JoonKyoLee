package christmas;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Order;

import christmas.validator.MenuValidator;
import java.util.HashMap;
import java.util.Map;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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

        assertThat(totalAmount).isEqualTo(expected);
    }

    static Stream<Arguments> generateMenuToGetTotalAmount() {
        return Stream.of(
                Arguments.of(
                        hasManyMenus
                        , 307_000
                ),
                Arguments.of(
                        hasLessMenus
                        , 15_000
                )
        );
    }

    @ParameterizedTest
    @DisplayName("크리스마스 디데이 할인을 저장할 때 key의 값이 1이고 할인 금액을 잘 계산한다.")
    @MethodSource("generateMenuToGetChristmasDDayDiscount")
    void calculateChristmasDDayDiscountAmountTest(
            Map<String, Integer> menu,
            int dayElapsed,
            int expected
    ) {
        Map<Integer, Integer> christmasDDayDiscount = new HashMap<>();
        order = new Order(new MenuValidator(), menu);

        order.calculateChristmasDDayEventDiscount(christmasDDayDiscount, dayElapsed);

        assertThat(christmasDDayDiscount.get(1)).isEqualTo(expected);
    }

    static Stream<Arguments> generateMenuToGetChristmasDDayDiscount() {
        return Stream.of(
                Arguments.of(
                        hasManyMenus
                        , 24
                        , 3_400
                ),
                Arguments.of(
                        hasManyMenus
                        , 0
                        , 1_000
                ),
                Arguments.of(
                        hasLessMenus
                        , 3
                        , 1_300
                )
        );
    }

    @ParameterizedTest
    @DisplayName("평일 할인을 저장할 때 key의 값이 2이고 디저트의 개수만큼 할인 금액을 잘 계산한다.")
    @MethodSource("generateMenuToGetWeekdayDiscount")
    void calculateWeekdayDiscountAmountTest(
            Map<String, Integer> menu,
            int expected
    ) {
        Map<Integer, Integer> weekdayDiscount = new HashMap<>();
        order = new Order(new MenuValidator(), menu);

        order.weekdayDiscount(weekdayDiscount);

        assertThat(weekdayDiscount.get(2)).isEqualTo(expected);
    }

    static Stream<Arguments> generateMenuToGetWeekdayDiscount() {
        return Stream.of(
                Arguments.of(
                        hasManyMenus
                        , 6_000
                ),
                Arguments.of(
                        hasLessMenus
                        , 0
                )
        );
    }

    @ParameterizedTest
    @DisplayName("주말 할인을 저장할 때 key의 값이 3이고 메인의 개수만큼 할인 금액을 잘 계산한다.")
    @MethodSource("generateMenuToGetWeekendDiscount")
    void calculateWeekendDiscountAmountTest(
            Map<String, Integer> menu,
            int expected
    ) {
        Map<Integer, Integer> weekendDiscount = new HashMap<>();
        order = new Order(new MenuValidator(), menu);

        order.weekendDiscount(weekendDiscount);

        assertThat(weekendDiscount.get(3)).isEqualTo(expected);
    }

    static Stream<Arguments> generateMenuToGetWeekendDiscount() {
        return Stream.of(
                Arguments.of(
                        hasManyMenus
                        , 5_000
                ),
                Arguments.of(
                        hasLessMenus
                        , 0
                )
        );
    }

    @DisplayName("특별 할인의 경우에는 key의 값이 4이고 1,000원을 저장한다.")
    @Test
    void calculateSpecialDiscount() {
        order = new Order(new MenuValidator(), hasManyMenus);
        Map<Integer, Integer> specialDiscount = new HashMap<>();

        order.specialDiscount(specialDiscount);

        assertThat(specialDiscount.get(4)).isEqualTo(1_000);
    }

    @DisplayName("샴페인 증정의 경우에는 key의 값이 5이고 25,000원을 저장한다.")
    @Test
    void grantChampagneTest() {
        order = new Order(new MenuValidator(), hasManyMenus);
        Map<Integer, Integer> grantChampagne = new HashMap<>();

        order.grantChampagneOnOrder(grantChampagne);

        assertThat(grantChampagne.get(5)).isEqualTo(25_000);
    }

    @ParameterizedTest
    @DisplayName("혜택 금액을 계산한 결과가 옳은지 확인한다.")
    @MethodSource("generateMenuToGetTotalDiscountAmount")
    void calculateTotalDiscountAmountTest(
            Map<String, Integer> menu, Map<Integer, Integer> discountAmount, int expected) {
        order = new Order(new MenuValidator(), menu);

        int totalAmount = order.calculateTotalDiscount(discountAmount);

        assertThat(totalAmount).isEqualTo(expected);
    }

    static Stream<Arguments> generateMenuToGetTotalDiscountAmount() {
        return Stream.of(
                Arguments.of(
                        hasManyMenus
                        , Map.of(
                                1, 3_400,
                                2, 6_000,
                                4, 1_000,
                                5, 25_000
                        )
                        , 35_400
                ),
                Arguments.of(
                        hasLessMenus
                        , Map.of(
                                1, 2_400
                        )
                        , 2_400
                )
        );
    }

    @ParameterizedTest
    @DisplayName("혜택 금액을 계산한 결과가 옳은지 확인한다.")
    @CsvSource(value = {"154000,30000,124000", "9000,0,9000", "10000,2400,7600"}, delimiter = ',')
    void calculateExpectedPaymentTest(int totalAmount, int discountAmount, int expected) {
        order = new Order(new MenuValidator(), hasManyMenus);

        int totalDiscountAfterDiscount = order.calculateExpectedPayment(totalAmount, discountAmount);

        assertThat(totalDiscountAfterDiscount).isEqualTo(expected);
    }
}