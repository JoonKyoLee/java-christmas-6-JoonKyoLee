package christmas.domain;

import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

public class Date {
    private static final int FIRST_DATE = 1;
    private static final int LAST_DATE = 31;
    private static final int CHRISTMAS_DATE = 25;
    private final int date;

    public Date(int date) {
        validate(date);
        this.date = date;
    }

    private void validate(int date) {
        validateInputRange(date);
    }

    private void validateInputRange(int date) {
        if(isOutOfRange(date)) {
            throw new InputException(ErrorMessage.DATE_FORMAT_IS_NOT_VALID);
        }
    }

    private boolean isOutOfRange(int date) {
        return date < FIRST_DATE || date > LAST_DATE;
    }
}