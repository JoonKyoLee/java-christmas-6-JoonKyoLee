package christmas.domain.menu;

public enum Dessert {
    CHOCOLATE_CAKE("초코케이크", 15_000),
    ICE_CREAM("아이스크림", 5_000);

    private final String menu;
    private final int price;

    Dessert(String menu, int price) {
        this.menu = menu;
        this.price = price;
    }
}
