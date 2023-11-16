package christmas.controller;

import christmas.domain.customer.Customer;
import christmas.domain.customer.Date;
import christmas.domain.EventBadge;
import christmas.domain.customer.Order;
import christmas.exception.InputException;
import christmas.validator.ConvertInputValidator;
import christmas.validator.MenuValidator;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.function.Supplier;
import java.util.Map;

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

        Map<Integer, Integer> appliedEvent = findAppliedEvent(customer);

        printEventResult(customer, appliedEvent);
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

    private Map<Integer, Integer> findAppliedEvent(Customer customer) {
        return customer.applyDiscountAmount();
    }

    private void printEventResult(Customer customer, Map<Integer, Integer> appliedEvent) {
        int totalPrice = customer.makeTotalPrice();
        int totalDiscountPrice = customer.makeTotalDiscountPrice(appliedEvent);
        int expectedPrice = customer.makeExpectedPrice(totalPrice, totalDiscountPrice);

        outputView.printTotalPriceBeforeDiscount(totalPrice);
        outputView.printFreebieMenu(totalPrice);
        outputView.printEachEventList(appliedEvent);
        outputView.printTotalEventPrice(totalDiscountPrice);
        outputView.printExpectedPriceAfterDiscount(expectedPrice);
        outputView.printBadge(EventBadge.getEventBadgeName(totalDiscountPrice));
    }
}