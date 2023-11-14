package christmas.validator;

import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class ConvertInputValidator {
    private static final String SEPARATE_BETWEEN_MENUS_DELIMITER = ",";
    private static final String SEPARATE_BETWEEN_MENU_AND_QUANTITY_DELIMITER = "-";
    private static final String SPACE_DELIMITER = " ";
    private static final String EMPTY_VALUE = "";
    private static final int DIFFERENCE_BETWEEN_DELIMITERS = 1;
    private static final int MENU_TO_DELIMITER_RATIO = 2;
    private static final int MENU_MANE_INDEX = 0;
    private static final int MENU_QUANTITY_INDEX = 1;


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

        List<String> menuName = extractEachValue(deletedDelimiterMenu, MENU_MANE_INDEX);
        List<Integer> menuQuantity = convertQuantity(extractEachValue(deletedDelimiterMenu, MENU_QUANTITY_INDEX));

        validateDuplicatedMenuName(menuName);

        return makeMap(menuName, menuQuantity);
    }


    private int parseInt(String input) {
        return Integer.parseInt(input);
    }

    private void validateMenuInputFormat(List<String> deletedDelimiterMenu, String input) {
        int menuCount = deletedDelimiterMenu.size();
        int countDelimiterBetweenMenuAndQuantity = countDelimiter(input, SEPARATE_BETWEEN_MENU_AND_QUANTITY_DELIMITER);
        int countDelimiterBetweenMenus = countDelimiter(input, SEPARATE_BETWEEN_MENUS_DELIMITER);

        if (hasSpaceBetweenInputFormat(input)) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }

        if (!validateDelimitersAndMenuCount(countDelimiterBetweenMenuAndQuantity,
                countDelimiterBetweenMenus,
                menuCount)) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }
    }

    private List<String> deleteDelimiter(String input) {
        return List.of(input.replace(SEPARATE_BETWEEN_MENU_AND_QUANTITY_DELIMITER, SPACE_DELIMITER)
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
            int delimiterCountBetweenMenuAndQuantity,
            int delimiterCountBetweenMenus,
            int menuCount) {
        return validateDelimiterAndMenuCountRatio(delimiterCountBetweenMenuAndQuantity, menuCount)
                && validateBetweenDelimitersDifference(
                        delimiterCountBetweenMenuAndQuantity, delimiterCountBetweenMenus);
    }

    private boolean validateDelimiterAndMenuCountRatio(
            int delimiterCountBetweenMenuAndQuantity,
            int menuCount) {
        return menuCount / delimiterCountBetweenMenuAndQuantity == MENU_TO_DELIMITER_RATIO;
    }

    private boolean validateBetweenDelimitersDifference(
            int delimiterCountBetweenMenuAndQuantity,
            int delimiterCountBetweenMenus) {
        return delimiterCountBetweenMenuAndQuantity - delimiterCountBetweenMenus == DIFFERENCE_BETWEEN_DELIMITERS;
    }

    private List<String> extractEachValue(List<String> origin, int indexToExtract) {
        return IntStream.range(0, origin.size())
                .filter(i -> i % 2 == indexToExtract)
                .mapToObj(origin::get)
                .toList();
    }

    private List<Integer> convertQuantity(List<String> quantiity) {
        try {
            return quantiity.stream()
                    .map(this::parseInt)
                    .toList();
        } catch (NumberFormatException exception) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }
    }

    private void validateDuplicatedMenuName(List<String> menuName) {
        if (hasDuplicatedMenuName(menuName)) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }
    }

    private boolean hasDuplicatedMenuName(List<String> menuName) {
        return menuName.stream()
                .distinct()
                .count() != menuName.size();
    }

    private Map<String, Integer> makeMap(List<String> key, List<Integer> value) {
        return IntStream.range(0, key.size())
                .boxed()
                .collect(Collectors.toMap(key::get, value::get));
    }
}