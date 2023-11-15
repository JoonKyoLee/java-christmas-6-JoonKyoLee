package christmas.view;

import static christmas.view.OutputMessage.*;

import christmas.exception.InputException;
import java.util.Map;

public class OutputView {
    private static final String MENU_FORMAT = "%s %d개";

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