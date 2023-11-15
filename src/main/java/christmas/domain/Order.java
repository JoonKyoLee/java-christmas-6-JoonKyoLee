package christmas.domain;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuType;
import christmas.validator.MenuValidator;

import java.util.Map;

public class Order {
    private static final int GRANT_CHAMPAGNE_AMOUNT = 120_000;
    private static final int DEFAULT_DISCOUNT_AMOUNT = 1_000;
    private static final int CHRISTMAS_D_DAY_DISCOUNT_INCREASE_PER_DAY = 100;
    private static final int CHRISTMAS_D_DAY_EVENT_INDEX = 1;
    private static final int WEEKDAY_EVENT_INDEX = 2;
    private static final int WEEKEND_EVENT_INDEX = 3;
    private static final int SPECIAL_EVENT_INDEX = 4;
    private static final int GRANT_CHAMPAGNE_INDEX = 5;

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
        return order.entrySet()
                .stream()
                .mapToInt(entry -> {
                    String menuName = entry.getKey();
                    int quantity = entry.getValue();

                    return Menu.getMenu(menuName).getPrice() * quantity;
                })
                .sum();
    }

    public void calculateChristmasDDayEventDiscount(Map<Integer, Integer> appliedDiscount,
                                                    int elapsedDaysFromDDayEvent) {
        appliedDiscount.put(
                CHRISTMAS_D_DAY_EVENT_INDEX
                , DEFAULT_DISCOUNT_AMOUNT + CHRISTMAS_D_DAY_DISCOUNT_INCREASE_PER_DAY * elapsedDaysFromDDayEvent
        );
    }

    public void weekdayDiscount(Map<Integer, Integer> appliedDiscount) {
        appliedDiscount.put(
                WEEKDAY_EVENT_INDEX
                , menuCount(MenuType.DESSERT) * DEFAULT_DISCOUNT_AMOUNT
        );
    }

    public void weekendDiscount(Map<Integer, Integer> appliedDiscount) {
        appliedDiscount.put(
                WEEKEND_EVENT_INDEX
                , menuCount(MenuType.MAIN) * DEFAULT_DISCOUNT_AMOUNT
        );
    }

    private int menuCount(MenuType menuType) {
        return order.keySet()
                .stream()
                .filter(menuName -> {
                    Menu menuItem = Menu.getMenu(menuName);
                    return menuItem.getMenuType().equals(menuType);
                })
                .mapToInt(order::get)
                .sum();
    }

    public void specialDiscount(Map<Integer, Integer> appliedDiscount) {
        appliedDiscount.put(
                SPECIAL_EVENT_INDEX
                , DEFAULT_DISCOUNT_AMOUNT
        );
    }

    public void grantChampagneOnOrder(Map<Integer, Integer> appliedDiscount) {
        appliedDiscount.put(
                GRANT_CHAMPAGNE_INDEX
                , Menu.CHAMPAGNE.getPrice()
        );
    }

    public boolean canGrantChampagne(int totalAmount) {
        return totalAmount >= GRANT_CHAMPAGNE_AMOUNT;
    }

    public int calculateTotalDiscount(Map<Integer, Integer> appliedDiscount) {
        return appliedDiscount.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int calculateExpectedPayment(int totalAmount, int discountAmount) {
        if (totalAmount >= GRANT_CHAMPAGNE_AMOUNT) {
            return totalAmount - discountAmount + Menu.CHAMPAGNE.getPrice();
        }
        return totalAmount - discountAmount;
    }
}