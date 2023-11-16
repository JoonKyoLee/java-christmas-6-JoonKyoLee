package christmas.exception;

public enum ErrorMessage {
    INPUT_IS_NULL("null 값이 입력 되었습니다."),
    INPUT_IS_EMPTY("빈 값이 입력 되었습니다."),

    DATE_FORMAT_IS_NOT_VALID("유효하지 않은 날짜입니다."),

    ORDER_FORMAT_IS_NOT_VALID("유효하지 않은 주문입니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String READ_AGAIN_MESSAGE = " 다시 입력해 주세요.";
    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return ERROR_PREFIX + errorMessage + READ_AGAIN_MESSAGE;
    }
}