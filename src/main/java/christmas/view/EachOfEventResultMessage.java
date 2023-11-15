package christmas.view;

import java.util.Arrays;
import java.util.Map;

public enum EachOfEventResultMessage {
    D_DAY_DISCOUNT(1, "크리스마스 디데이 할인:"),
    WEEKDAY_DISCOUNT(2, "평일 할인:"),
    WEEKEND_DISCOUNT(3, "주말 할인:"),
    SPECIAL_DISCOUNT(4, "특별 할인:"),
    GRANT_EVENT(5, "증정 이벤트:");

    private static final String NOTHING = "없음";
    private static final String SPACE = " ";
    private static final String PRICE_UNIT = "-%,d원";
    private static final String NEXT_LINE = "\n";
    private final int messageIndex;
    private final String message;

    EachOfEventResultMessage(int messageIndex, String message) {
        this.messageIndex = messageIndex;
        this.message = message;
    }

    public String getMessage() {
        return message + SPACE;
    }

    public static String makeResultFormOfEachEvent(Map<Integer, Integer> discountPrice) {
        StringBuilder sb = new StringBuilder();

        discountPrice.forEach((key, value) -> {
            String message = EachOfEventResultMessage.getEvent(key).getMessage();
            sb.append(message)
                    .append(String.format(PRICE_UNIT, value))
                    .append(NEXT_LINE);
        });

        if (sb.isEmpty()) {
            return NOTHING + NEXT_LINE;
        }
        return sb.toString();
    }

    private static EachOfEventResultMessage getEvent(int index) {
        return Arrays.stream(values())
                .filter(event -> event.messageIndex == index)
                .findFirst()
                .orElse(null);
    }
}