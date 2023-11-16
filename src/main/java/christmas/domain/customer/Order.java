package christmas.domain.customer;

import christmas.constant.OrderConstant;
import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuType;
import christmas.validator.MenuValidator;

import java.util.Map;

public class Order {
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

    public Map<String, Integer> getOrder() {
        return order;
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
                OrderConstant.CHRISTMAS_D_DAY_EVENT_INDEX
                , OrderConstant.DEFAULT_DISCOUNT_AMOUNT
                        + OrderConstant.CHRISTMAS_D_DAY_DISCOUNT_INCREASE_PER_DAY * elapsedDaysFromDDayEvent
        );
    }

    public void weekdayDiscount(Map<Integer, Integer> appliedDiscount) {
        appliedDiscount.put(
                OrderConstant.WEEKDAY_EVENT_INDEX
                , menuCount(MenuType.DESSERT) * OrderConstant.WEEK_DISCOUNT_AMOUNT
        );
    }

    public void weekendDiscount(Map<Integer, Integer> appliedDiscount) {
        appliedDiscount.put(
                OrderConstant.WEEKEND_EVENT_INDEX
                , menuCount(MenuType.MAIN) * OrderConstant.WEEK_DISCOUNT_AMOUNT
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
                OrderConstant.SPECIAL_EVENT_INDEX
                , OrderConstant.DEFAULT_DISCOUNT_AMOUNT
        );
    }

    public void grantChampagneOnOrder(Map<Integer, Integer> appliedDiscount) {
        if(canGrantChampagne(calculateTotalAmountBeforeDiscount())) {
            appliedDiscount.put(
                    OrderConstant.GRANT_CHAMPAGNE_INDEX
                    , Menu.CHAMPAGNE.getPrice()
            );
        }
    }

    public boolean canGrantChampagne(int totalAmount) {
        return totalAmount >= OrderConstant.GRANT_CHAMPAGNE_AMOUNT;
    }

    public int calculateTotalDiscount(Map<Integer, Integer> appliedDiscount) {
        return appliedDiscount.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int calculateExpectedPayment(int totalAmount, int discountAmount) {
        if (totalAmount >= OrderConstant.GRANT_CHAMPAGNE_AMOUNT) {
            return totalAmount - discountAmount + Menu.CHAMPAGNE.getPrice();
        }
        return totalAmount - discountAmount;
    }
}