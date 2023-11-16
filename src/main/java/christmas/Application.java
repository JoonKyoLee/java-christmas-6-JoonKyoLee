package christmas;

import christmas.controller.ChristmasEventManager;
import christmas.validator.ConvertInputValidator;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ChristmasEventManager christmasEventManager = new ChristmasEventManager(
                new InputView(),
                new OutputView(),
                new ConvertInputValidator()
        );

        christmasEventManager.run();
    }
}