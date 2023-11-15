package christmas.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Customer {
    private static final int MINIMUM_AMOUNT_FOR_EVENT = 10_000;

    private final Date date;
    private final Order order;

    public Customer(Date date, Order order) {
        this.date = date;
        this.order = order;
    }

    public Map<Integer, Integer> applyDiscountAmount() {
        Map<Integer, Integer> appliedDiscount = new LinkedHashMap<>();

        if (order.calculateTotalAmountBeforeDiscount() >= MINIMUM_AMOUNT_FOR_EVENT) {
            applyDDayDiscount(appliedDiscount);
            applyWeekdayDiscount(appliedDiscount);
            applyWeekendDiscount(appliedDiscount);
            applySpecialDiscount(appliedDiscount);
        }

        return appliedDiscount;
    }

    private void applyDDayDiscount(Map<Integer, Integer> appliedDiscount) {
        if (date.canApplyChristmasDDayDiscount()) {
            order.calculateChristmasDDayEventDiscount(appliedDiscount, date.countDaysToApplyChristmasDDayDiscount());
        }
    }

    private void applyWeekdayDiscount(Map<Integer, Integer> appliedDiscount) {
        if (date.isWeekday()) {
            order.weekdayDiscount(appliedDiscount);
        }
    }

    private void applyWeekendDiscount(Map<Integer, Integer> appliedDiscount) {
        if (date.isWeekend()) {
            order.weekendDiscount(appliedDiscount);
        }
    }

    private void applySpecialDiscount(Map<Integer, Integer> appliedDiscount) {
        if (date.hasStarInCalendar()) {
            order.specialDiscount(appliedDiscount);
        }
    }
}