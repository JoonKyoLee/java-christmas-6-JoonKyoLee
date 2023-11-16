package christmas.validator;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuType;
import christmas.exception.ErrorMessage;
import christmas.exception.InputException;

import java.util.Map;
import java.util.Objects;

public class MenuValidator {
    private static final int MINIMUM_THRESHOLD_PER_MENU = 0;
    private static final int TOTAL_MENU_THRESHOLD = 20;

    public MenuValidator() {
    }

    public void validate(Map<String, Integer> menu) {
        validateEachMenuQuantity(menu);
        validateTotalMenuQuantity(menu);
        validateMenuNameInMenuList(menu);
        validateMenuCategories(menu);
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

    private void validateMenuNameInMenuList(Map<String, Integer> menu) {
        if (hasMenuNameOutOfMenuList(menu)) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }
    }

    private boolean hasMenuNameOutOfMenuList(Map<String, Integer> menu) {
        return menu.keySet()
                .stream()
                .map(Menu::getMenu)
                .filter(Objects::nonNull)
                .count() != menu.size();
    }

    private void validateMenuCategories(Map<String, Integer> menu) {
        if (hasOnlyBeverages(menu)) {
            throw new InputException(ErrorMessage.ORDER_FORMAT_IS_NOT_VALID);
        }
    }

    private boolean hasOnlyBeverages(Map<String, Integer> menu) {
        return menu.keySet()
                .stream()
                .allMatch(menuName -> {
                    Menu menuItem = Menu.getMenu(menuName);
                    return menuItem.getMenuType().equals(MenuType.BEVERAGE);
                });
    }
}