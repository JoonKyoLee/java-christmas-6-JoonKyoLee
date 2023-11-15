package christmas.controller;

import christmas.domain.Customer;
import christmas.domain.Date;
import christmas.domain.Order;
import christmas.exception.InputException;
import christmas.validator.ConvertInputValidator;
import christmas.validator.MenuValidator;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.function.Supplier;

public class ChristmasEventManager {
    private final InputView inputView;
    private final OutputView outputView;
    private final ConvertInputValidator convertInputValidator;

    public ChristmasEventManager(
            InputView inputView,
            OutputView outputView,
            ConvertInputValidator convertInputValidator
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.convertInputValidator = convertInputValidator;
    }

    public void run() {
        Customer customer = readCustomerRequest();

        printCustomerOrder(customer);
    }

    private Customer readCustomerRequest() {
        outputView.printGreetingMessage();

        Date date = repeatUntilReadValidInput(this::takeDate);
        Order order = repeatUntilReadValidInput(this::takeOrder);

        return new Customer(date, order);
    }

    private Date takeDate() {
        outputView.askDate();
        String input = inputView.readInput();
        return new Date(convertInputValidator.convertDate(input));
    }

    private Order takeOrder() {
        outputView.askMenuAndQuantity();
        String order = inputView.readInput();
        return new Order(new MenuValidator(), convertInputValidator.convertOrder(order));
    }

    private <T> T repeatUntilReadValidInput(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (InputException exception) {
                outputView.printErrorMessage(exception);
            }
        }
    }

    private void printCustomerOrder(Customer customer) {
        outputView.printEventIntroduction(customer.getVisitingDate());
        outputView.printOrderList(customer.getCustomerOrder());
    }
}