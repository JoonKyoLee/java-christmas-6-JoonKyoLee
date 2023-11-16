package christmas.domain;

import java.util.Arrays;
import java.util.Optional;

public enum EventBadge {
    Santa("산타", 20_000),
    Tree("트리", 10_000),
    Star("스타", 5_000),
    None("없음", 0);

    private final String badgeName;
    private final int benefitAmount;

    EventBadge(String badgeName, int benefitAmount) {
        this.badgeName = badgeName;
        this.benefitAmount = benefitAmount;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public static String getEventBadgeName(int benefitAmount) {
        return Arrays.stream(values())
                .filter(eventBadge -> eventBadge.benefitAmount <= benefitAmount)
                .findFirst()
                .map(EventBadge::getBadgeName)
                .or(() -> Optional.of(None.badgeName))
                .get();
    }
}