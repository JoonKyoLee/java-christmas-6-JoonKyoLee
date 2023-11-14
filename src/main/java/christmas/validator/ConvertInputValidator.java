package christmas.validator;

import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

import java.util.List;
import java.util.Map;

public class ConvertInputValidator {
    private static final String SEPARATE_BETWEEN_MENUS_DELIMITER = ",";
    private static final String SEPARATE_BETWEEN_MENU_AND_PRICE_DELIMITER = "-";
    private static final String SPACE_DELIMITER = " ";
    private static final String EMPTY_VALUE = "";
    private static final int DIFFERENCE_BETWEEN_DELIMITERS = 1;
    private static final int MENU_TO_DELIMITER_RATIO = 2;


    public ConvertInputValidator() {
    }

    public int convertDate(String input) {
        try {
            return parseInt(input);
        } catch (NumberFormatException exception) {
            throw new InputException(ErrorMessage.DATE_FORMAT_IS_NOT_VALID);
        }
    }

    public Map<String, Integer> convertOrder(String input) {
        List<String> deletedDelimiterMenu = deleteDelimiter(input);

        validateMenuInputFormat(deletedDelimiterMenu, input);
    }


    private int parseInt(String input) {
        return Integer.parseInt(input);
    }

    private void validateMenuInputFormat(List<String> deletedDelimiterMenu, String input) {
        int menuCount = deletedDelimiterMenu.size();
        int countDelimiterBetweenMenuAndPrice = countDelimiter(input, SEPARATE_BETWEEN_MENU_AND_PRICE_DELIMITER);
        int countDelimiterBetweenMenus = countDelimiter(input, SEPARATE_BETWEEN_MENUS_DELIMITER);

        if (hasSpaceBetweenInputFormat(input)) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }

        if (!validateDelimitersAndMenuCount(countDelimiterBetweenMenuAndPrice,
                countDelimiterBetweenMenus,
                menuCount)) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }
    }

    private List<String> deleteDelimiter(String input) {
        return List.of(input.replace(SEPARATE_BETWEEN_MENU_AND_PRICE_DELIMITER, SPACE_DELIMITER)
                .replace(SEPARATE_BETWEEN_MENUS_DELIMITER, SPACE_DELIMITER)
                .split(SPACE_DELIMITER));
    }

    private int countDelimiter(String input, String delimiter) {
        return input.length() - input.replace(delimiter, EMPTY_VALUE).length();
    }

    private boolean hasSpaceBetweenInputFormat(String input) {
        return input.contains(SPACE_DELIMITER);
    }

    private boolean validateDelimitersAndMenuCount(
            int delimiterCountBetweenMenuAndPrice,
            int delimiterCountBetweenMenus,
            int menuCount) {
        return validateDelimiterAndMenuCountRatio(delimiterCountBetweenMenuAndPrice, menuCount)
                && validateBetweenDelimitersDifference(delimiterCountBetweenMenuAndPrice, delimiterCountBetweenMenus);
    }

    private boolean validateDelimiterAndMenuCountRatio(
            int delimiterCountBetweenMenuAndPrice,
            int menuCount) {
        return menuCount / delimiterCountBetweenMenuAndPrice == MENU_TO_DELIMITER_RATIO;
    }

    private boolean validateBetweenDelimitersDifference(
            int delimiterCountBetweenMenuAndPrice,
            int delimiterCountBetweenMenus) {
        return delimiterCountBetweenMenuAndPrice - delimiterCountBetweenMenus == DIFFERENCE_BETWEEN_DELIMITERS;
    }
}