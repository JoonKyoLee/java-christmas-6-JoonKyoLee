package christmas.domain.menu;

public enum Beverage {
    ZERO_COKE("제로콜라", 3_000),
    RED_WINE("레드와인", 60_000),
    CHAMPAGNE("샴페인", 25_000);

    private final String menu;
    private final int price;

    Beverage(String menu, int price) {
        this.menu = menu;
        this.price = price;
    }
}