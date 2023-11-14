package christmas.validator;

import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

import java.util.List;

public class ConvertInputValidator {
    private static final String SEPARATE_BETWEEN_MENUS_DELIMITER = ",";
    private static final String SEPARATE_BETWEEN_MENU_AND_PRICE_DELIMITER = "-";
    private static final String SPACE_DELIMITER = " ";

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

    private List<String> deleteDelimiter(String input) {
        return List.of(input.replace(SEPARATE_BETWEEN_MENU_AND_PRICE_DELIMITER, SPACE_DELIMITER)
                .replace(SEPARATE_BETWEEN_MENUS_DELIMITER, SPACE_DELIMITER)
                .split(SPACE_DELIMITER));
    }
}