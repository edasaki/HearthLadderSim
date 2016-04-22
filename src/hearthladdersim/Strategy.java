package hearthladdersim;

public enum Strategy {
    FAST(0.52, 5, "52% win rate with 5 minute matches. Aggro face decks. Face Hunter, Face Paladin"),
    MEDIUM(0.55, 7, "55% win rate with 7 minute matches. Mid-range decks. Examples: Midrange Druid, Secret Paladin"),
    EQUAL_SLOW1(0.56, 10, "56% win rate, time similar to rurning a FAST (52% 5 minute) deck. Slow control decks. Win-rate equivalent to MEDIUM decks. Examples: Control Warrior, Dragon Priest"),
    EQUAL_SLOW2(0.57, 10, "57% win rate, time similar to rurning a FAST (52% 5 minute) deck. Slow control decks. Win-rate equivalent to MEDIUM decks. Examples: Control Warrior, Dragon Priest"),
    BAD_SLOW(0.55, 10, "55% win rate with 10 minute matches. Slow control decks. Win-rate equivalent to MEDIUM decks. Examples: Control Warrior, Dragon Priest"),
    SLOW(0.62, 10, "62% win rate with 10 minute matches. Slow control decks. Played well enough to maintain a high win rate. Examples: Control Warrior, Dragon Priest"),
    MY_PATRON(0.58, 7, "My own 58% win rate on Patron (19-14) with an average of 7 minute matches."),
    MY_FLOOD_PALLY(0.71, 7, "My own 71% win rate on Flood Pally (30-12) with an average of 6 minute matches."),
    MY_FACE_HUNTER(0.57, 5, "My own 57% win rate on Face Hunter (41-31) with an average of 5 minute matches."),
    MY_MURLOC_PALLY(0.47, 8, "My own 47% win rate on Anyfin Paladin (26-29) with an average of 8 minute matches."),
    MY_MILL_ROGUE(0.50, 9, "My own 50% win rate on Mill Rogue (19-19) with an average of 9 minute matches."),
    SWITCH_STRATEGY_1(0, 0, "Play FAST decks until 2 wins in a row, then play SLOW to attempt to get a longer winstreak. Won't ever play SLOW in legend."),
    SWITCH_STRATEGY_2(0, 0, "Play FAST decks until 2 wins in a row, then play BAD_SLOW to attempt to get a longer winstreak. Won't ever play BAD_SLOW in legend."),
    SWITCH_STRATEGY_3(0, 0, "Play SLOW decks until 2 wins in a row, then play FAST to attempt to get a longer winstreak. Won't ever play FAST in legend."),

    CARD_MASTER(0.95, 5, "95% win rate with 5 minutes matches. Pls teach me your ways.");
    private double rate;
    private int time;
    private String desc;

    public double getTime(int streak, int rank) {
        if (this == Strategy.SWITCH_STRATEGY_1) {
            if (streak > 2 && rank > 5)
                return SLOW.time;
            else
                return FAST.time;
        } else if (this == Strategy.SWITCH_STRATEGY_2) {
            if (streak > 2 && rank > 5)
                return BAD_SLOW.time;
            else
                return FAST.time;
        } else if (this == Strategy.SWITCH_STRATEGY_3) {
            if (streak > 2 && rank > 5)
                return FAST.time;
            else
                return SLOW.time;
        } else {
            return time;
        }
    }

    public double getRate(int streak, int rank) {
        if (this == Strategy.SWITCH_STRATEGY_1) {
            if (streak > 2 && rank > 5)
                return SLOW.rate;
            else
                return FAST.rate;
        } else if (this == Strategy.SWITCH_STRATEGY_2) {
            if (streak > 2 && rank > 5)
                return BAD_SLOW.rate;
            else
                return FAST.rate;
        } else if (this == Strategy.SWITCH_STRATEGY_3) {
            if (streak > 2 && rank > 5)
                return FAST.rate;
            else
                return SLOW.rate;
        } else {
            return rate;
        }
    }

    public String getDescription() {
        return desc;
    }

    Strategy(double rate, int time, String description) {
        this.rate = rate;
        this.time = time;
        this.desc = description;
    }
}