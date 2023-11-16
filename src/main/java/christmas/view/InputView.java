package christmas.view;

import camp.nextstep.edu.missionutils.Console;

import christmas.validator.NullAndEmptyValidator;

public class InputView {
    public String readInput() {
        NullAndEmptyValidator nullAndEmptyValidator = new NullAndEmptyValidator();

        String input = Console.readLine();
        nullAndEmptyValidator.validateNullAndEmpty(input);

        return input;
    }
}