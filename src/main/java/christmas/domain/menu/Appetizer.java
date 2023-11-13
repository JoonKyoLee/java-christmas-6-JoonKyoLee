package christmas.domain.menu;

public enum Appetizer {
    MUSHROOM_SOUP("양송이수프", 6_000),
    TAPAS("타파스", 5_500),
    CAESAR_SALAD("시저샐러드", 8_000);

    private final String menu;
    private final int price;

    Appetizer(String menu, int price) {
        this.menu = menu;
        this.price = price;
    }
}