package christmas.view;

import static christmas.view.OutputMessage.*;

import christmas.domain.menu.Menu;
import christmas.exception.InputException;

import java.util.Map;

public class OutputView {
    private static final String MENU_FORMAT = "%s %d개";
    private static final String PAYMENT_FORMAT = "%,d원";
    private static final String HAS_NOTHING = "없음";
    private static final String MINUS_SIGN = "-";
    private static final int NUMBER_OF_FREEBIE_MENU = 1;
    private static final int MINIMUM_PRICE_TO_GIVE_FREEBIE_MENU = 120_000;

    public void printGreetingMessage() {
        System.out.println(GREETING.getMessage());
    }

    public void askDate() {
        System.out.println(ASK_DATE.getMessage());
    }

    public void askMenuAndQuantity() {
        System.out.println(ASK_MENU_AND_QUANTITY.getMessage());
    }

    public void printEventIntroduction(int date) {
        printfAndNewLine(VIEW_EVENT_INTRODUCTION.getMessage(), date);
    }

    public void printOrderList(Map<String, Integer> order) {
        printNewLineAndPrintln(VIEW_ORDERED_MENU.getMessage());

        order.forEach((key, value) -> {
            System.out.printf(MENU_FORMAT, key, value);
            printNewLine();
        });
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        printNewLineAndPrintln(VIEW_PAY_AMOUNT_BEFORE_DISCOUNT.getMessage());

        printfAndNewLine(PAYMENT_FORMAT, totalPrice);
    }

    public void printFreebieMenu(int totalPrice) {
        printNewLineAndPrintln(VIEW_FREEBIE_MENU.getMessage());

        String freebieFormat = makeFreebieFormat(totalPrice);
        System.out.println(freebieFormat);
    }

    private String makeFreebieFormat(int totalPrice) {
        if(totalPrice < MINIMUM_PRICE_TO_GIVE_FREEBIE_MENU) {
            return HAS_NOTHING;
        }

        return String.format(MENU_FORMAT, Menu.CHAMPAGNE.getMenuName(), NUMBER_OF_FREEBIE_MENU);
    }

    public void printEachEventList(Map<Integer, Integer> appliedEvent) {
        printNewLineAndPrintln(VIEW_EVENT_PRICE.getMessage());

        String eventList = EachOfEventResultMessage.makeResultFormOfEachEvent(appliedEvent);
        System.out.println(eventList);
    }

    private void printNewLineAndPrintln(String message) {
        printNewLine();
        System.out.println(message);
    }

    private void printfAndNewLine(String message, int number) {
        System.out.printf(message, number);
        printNewLine();
    }

    private void printNewLine() {
        System.out.println();
    }

    public void printErrorMessage(InputException exception) {
        System.out.println(exception.getMessage());
    }
}