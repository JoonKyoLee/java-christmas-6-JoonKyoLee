package christmas.domain;

import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

import java.time.LocalDate;

public class Date {
    private static final int YEAR = 2023;
    private static final int MONTH = 12;
    private static final int FIRST_DATE = 1;
    private static final int LAST_DATE = 31;
    private static final int CHRISTMAS_DATE = 25;
    private static final int FRIDAY_INDEX = 5;
    private static final int SUNDAY_INDEX = 7;
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
        LocalDate localDate = LocalDate.of(YEAR, MONTH, date);
        return localDate.getDayOfWeek().getValue();
    }

    public boolean canApplyChristmasDDayDiscount() {
        return date <= CHRISTMAS_DATE;
    }

    public int countDaysToApplyChristmasDDayDiscount() {
        return date - FIRST_DATE;
    }

    public boolean isWeekday() {
        return dayOfWeek == SUNDAY_INDEX || dayOfWeek < FRIDAY_INDEX;
    }

    public boolean isWeekend() {
        return FRIDAY_INDEX <= dayOfWeek && dayOfWeek < SUNDAY_INDEX;
    }

    public boolean hasStarInCalendar() {
        return date == CHRISTMAS_DATE || dayOfWeek == SUNDAY_INDEX;
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
        return date < FIRST_DATE || date > LAST_DATE;
    }
}