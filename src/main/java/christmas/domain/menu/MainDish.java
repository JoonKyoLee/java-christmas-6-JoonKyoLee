package christmas.domain.menu;

public enum MainDish {
    T_BONE_STEAK("티본스테이크", 55_000),
    BARBECUE_RIBS("바비큐립", 54_000),
    SEAFOOD_PASTA("해산물파스타", 35_000),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000);

    private final String menu;
    private final int price;

    MainDish(String menu, int price) {
        this.menu = menu;
        this.price = price;
    }
}
