package christmas.domain;

import christmas.validator.MenuValidator;
import java.util.Map;

public class Order {
    private static final int GRANT_CHAMPAGNE_AMOUNT = 120_000;

    private final MenuValidator menuValidator;
    private final Map<String, Integer> order;

    public Order(MenuValidator menuValidator, Map<String, Integer> order) {
        this.menuValidator = menuValidator;
        validate(order);

        this.order = order;
    }

    private void validate(Map<String, Integer> order) {
        menuValidator.validate(order);
    }

    public int calculateTotalAmountBeforeDiscount() {
        return order.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public boolean grantChampagneOnOrder(int totalAmount) {
        return totalAmount >= GRANT_CHAMPAGNE_AMOUNT;
    }
}