package christmas.view;

public enum OutputMessage {
    GREETING("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다."),

    ASK_DATE("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"),
    ASK_MENU_AND_QUANTITY("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"),

    VIEW_EVENT_INTRODUCTION("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),
    VIEW_ORDERED_MENU("<주문 메뉴>"),
    VIEW_PAY_AMOUNT_BEFORE_DISCOUNT("<할인 전 총주문 금액>"),
    VIEW_FREEBIE_MENU("<증정 메뉴>"),
    VIEW_EVENT_PRICE("<혜택 내역>"),
    VIEW_TOTAL_EVENT_PRICE("<총혜택 금액>"),
    VIEW_EXPECTED_PRICE_AFTER_APPLYING_DISCOUNT("<할인 후 예상 결제 금액>"),
    VIEW_BADGE("<12월 이벤트 배지>");

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}