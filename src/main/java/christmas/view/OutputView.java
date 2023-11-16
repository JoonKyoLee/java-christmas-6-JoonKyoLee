package christmas.view;

import static christmas.view.OutputMessage.*;

import christmas.constant.OutputConstant;
import christmas.domain.menu.Menu;
import christmas.exception.InputException;

import java.util.Map;

public class OutputView {

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
            System.out.printf(OutputConstant.MENU_FORMAT, key, value);
            printNewLine();
        });
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        printNewLineAndPrintln(VIEW_PAY_AMOUNT_BEFORE_DISCOUNT.getMessage());

        printfAndNewLine(OutputConstant.PAYMENT_FORMAT, totalPrice);
    }

    public void printFreebieMenu(int totalPrice) {
        printNewLineAndPrintln(VIEW_FREEBIE_MENU.getMessage());

        String freebieFormat = makeFreebieFormat(totalPrice);
        System.out.println(freebieFormat);
    }

    private String makeFreebieFormat(int totalPrice) {
        if (totalPrice < OutputConstant.MINIMUM_PRICE_TO_GIVE_FREEBIE_MENU) {
            return OutputConstant.HAS_NOTHING;
        }

        return String.format(OutputConstant.MENU_FORMAT,
                Menu.CHAMPAGNE.getMenuName(), OutputConstant.NUMBER_OF_FREEBIE_MENU);
    }

    public void printEachEventList(Map<Integer, Integer> appliedEvent) {
        printNewLineAndPrintln(VIEW_EVENT_PRICE.getMessage());

        String eventList = EachOfEventResultMessage.makeResultFormOfEachEvent(appliedEvent);
        System.out.println(eventList);
    }

    public void printTotalEventPrice(int totalDiscountPrice) {
        System.out.println(VIEW_TOTAL_EVENT_PRICE.getMessage());

        if (totalDiscountPrice > 0) {
            System.out.print(OutputConstant.MINUS_SIGN);
        }
        printfAndNewLine(OutputConstant.PAYMENT_FORMAT, totalDiscountPrice);
    }

    public void printExpectedPriceAfterDiscount(int expectedPrice) {
        printNewLineAndPrintln(VIEW_EXPECTED_PRICE_AFTER_APPLYING_DISCOUNT.getMessage());
        printfAndNewLine(OutputConstant.PAYMENT_FORMAT, expectedPrice);
    }

    public void printBadge(String badge) {
        printNewLine();
        System.out.println(VIEW_BADGE.getMessage());

        System.out.println(badge);
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