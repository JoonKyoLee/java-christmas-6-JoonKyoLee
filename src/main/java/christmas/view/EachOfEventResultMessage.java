package christmas.view;

public enum EachOfEventResultMessage {
    D_DAY_DISCOUNT(1, "크리스마스 디데이 할인:"),
    WEEKDAY_DISCOUNT(2, "평일 할인:"),
    WEEKEND_DISCOUNT(3, "주말 할인:"),
    SPECIAL_DISCOUNT(4, "특별 할인:"),
    GRANT_EVENT(5, "증정 이벤트:");

    private static final String SPACE = " ";
    private final int messageIndex;
    private final String message;

    EachOfEventResultMessage(int messageIndex, String message) {
        this.messageIndex = messageIndex;
        this.message = message;
    }

    public String getMessage() {
        return message + SPACE;
    }
}
