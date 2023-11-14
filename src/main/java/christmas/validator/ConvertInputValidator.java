package christmas.validator;

import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

public class ConvertInputValidator {
    public ConvertInputValidator() {
    }

    public int convertDate(String input) {
        try {
            return parseInt(input);
        } catch (NumberFormatException exception) {
            throw new InputException(ErrorMessage.DATE_FORMAT_IS_NOT_VALID);
        }
    }

    private int parseInt(String input) {
        return Integer.parseInt(input);
    }
}