package christmas.validator;

import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

public class NullAndEmptyValidator {
    public NullAndEmptyValidator() {
    }

    public void validateNullAndEmpty(String input) {
        validateNullValue(input);
        validateEmptyValue(input);
    }

    private void validateNullValue(String input) {
        if (hasNullValue(input)) {
            throw new InputException(ErrorMessage.INPUT_IS_EMPTY);
        }
    }

    private void validateEmptyValue(String input) {
        if (hasEmptyValue(input)) {
            throw new InputException(ErrorMessage.INPUT_IS_EMPTY);
        }
    }

    private boolean hasNullValue(String input) {
        return input == null;
    }

    private boolean hasEmptyValue(String input) {
        return input.isBlank();
    }
}