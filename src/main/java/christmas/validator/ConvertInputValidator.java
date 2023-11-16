package christmas.validator;

import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class ConvertInputValidator {
    private static final String SEPARATE_BETWEEN_MENUS_DELIMITER = ",";
    private static final String SEPARATE_BETWEEN_MENU_AND_QUANTITY_DELIMITER = "-";
    private static final String SPACE_DELIMITER = " ";
    private static final int MENU_MANE_INDEX = 0;
    private static final int MENU_QUANTITY_INDEX = 1;
    private static final int NUMBERS_OF_ELEMENTS = 2;

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

        validateMenuInputFormat(input);

        List<String> menuName = extractEachValue(deletedDelimiterMenu, MENU_MANE_INDEX);
        List<Integer> menuQuantity = convertQuantity(extractEachValue(deletedDelimiterMenu, MENU_QUANTITY_INDEX));

        validateDuplicatedMenuName(menuName);

        return makeMap(menuName, menuQuantity);
    }


    private int parseInt(String input) {
        return Integer.parseInt(input);
    }

    private void validateMenuInputFormat(String input) {
        if (hasSpaceBetweenInputFormat(input) || !hasValidInputFormat(input)) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }
    }

    private List<String> deleteDelimiter(String input) {
        return List.of(input.replace(SEPARATE_BETWEEN_MENU_AND_QUANTITY_DELIMITER, SPACE_DELIMITER)
                .replace(SEPARATE_BETWEEN_MENUS_DELIMITER, SPACE_DELIMITER)
                .split(SPACE_DELIMITER));
    }

    private boolean hasSpaceBetweenInputFormat(String input) {
        return input.contains(SPACE_DELIMITER);
    }

    private boolean hasValidInputFormat(String input) {
        return Arrays.stream(input.split(SEPARATE_BETWEEN_MENUS_DELIMITER))
                .map(menu -> menu.split(SEPARATE_BETWEEN_MENU_AND_QUANTITY_DELIMITER))
                .allMatch(arr -> arr.length == NUMBERS_OF_ELEMENTS);
    }

    private List<String> extractEachValue(List<String> origin, int indexToExtract) {
        return IntStream.range(0, origin.size())
                .filter(i -> i % NUMBERS_OF_ELEMENTS == indexToExtract)
                .mapToObj(origin::get)
                .toList();
    }

    private List<Integer> convertQuantity(List<String> quantity) {
        try {
            return quantity.stream()
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