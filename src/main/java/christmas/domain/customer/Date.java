package christmas.domain.customer;

import christmas.constant.DateConstant;
import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

import java.time.LocalDate;

public class Date {
    private final int date;
    private final int dayOfWeek;

    public Date(int date) {
        validate(date);
        this.date = date;
        this.dayOfWeek = calculateDayOfWeek();
    }

    public int getDate() {
        return date;
    }

    public int calculateDayOfWeek() {
        LocalDate localDate = LocalDate.of(DateConstant.YEAR, DateConstant.MONTH, date);
        return localDate.getDayOfWeek().getValue();
    }

    public boolean canApplyChristmasDDayDiscount() {
        return date <= DateConstant.CHRISTMAS_DATE;
    }

    public int countDaysToApplyChristmasDDayDiscount() {
        return date - DateConstant.FIRST_DATE;
    }

    public boolean isWeekday() {
        return dayOfWeek == DateConstant.SUNDAY_INDEX || dayOfWeek < DateConstant.FRIDAY_INDEX;
    }

    public boolean isWeekend() {
        return DateConstant.FRIDAY_INDEX <= dayOfWeek && dayOfWeek < DateConstant.SUNDAY_INDEX;
    }

    public boolean hasStarInCalendar() {
        return date == DateConstant.CHRISTMAS_DATE || dayOfWeek == DateConstant.SUNDAY_INDEX;
    }

    private void validate(int date) {
        validateInputRange(date);
    }

    private void validateInputRange(int date) {
        if (isOutOfRange(date)) {
            throw new InputException(ErrorMessage.DATE_FORMAT_IS_NOT_VALID);
        }
    }

    private boolean isOutOfRange(int date) {
        return date < DateConstant.FIRST_DATE || date > DateConstant.LAST_DATE;
    }
}