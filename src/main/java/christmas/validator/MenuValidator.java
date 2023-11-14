package christmas.validator;

import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

import java.util.Map;

public class MenuValidator {
    private static final int MINIMUM_THRESHOLD_PER_MENU = 0;
    private static final int TOTAL_MENU_THRESHOLD = 20;

    public MenuValidator() {
    }

    public void validate(Map<String, Integer> menu) {
        validateEachMenuQuantity(menu);
        validateTotalMenuQuantity(menu);
    }

    private void validateEachMenuQuantity(Map<String, Integer> menu) {
        if (!isOverMinimumQuantityPerMenu(menu)) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }
    }

    private boolean isOverMinimumQuantityPerMenu(Map<String, Integer> menu) {
        return menu.values()
                .stream()
                .allMatch(quantity -> quantity > MINIMUM_THRESHOLD_PER_MENU);
    }

    private void validateTotalMenuQuantity(Map<String, Integer> menu) {
        if (isToTalMenuQuantityOverMaximum(menu)) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }
    }

    private boolean isToTalMenuQuantityOverMaximum(Map<String, Integer> menu) {
        return menu.values()
                .stream()
                .reduce(0, Integer::sum) > TOTAL_MENU_THRESHOLD;
    }
}