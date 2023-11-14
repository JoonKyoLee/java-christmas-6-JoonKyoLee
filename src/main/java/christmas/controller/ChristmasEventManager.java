package christmas.controller;

import christmas.domain.Date;
import christmas.exception.InputException;
import christmas.validator.ConvertInputValidator;
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

    private Date takeDate() {
        outputView.askDate();
        String input = inputView.readInput();
        return new Date(convertInputValidator.convertDate(input));
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
}