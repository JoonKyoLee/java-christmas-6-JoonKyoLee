package christmas.view;

import static christmas.view.OutputMessage.*;

import christmas.exception.InputException;

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

    public void printErrorMessage(InputException exception) {
        System.out.println(exception.getMessage());
    }
}