package com.akhayat.poker.simulator.card;

/**
 * Represents a standard playing card with a rank and a suit.
 * Implements Comparable so they can be easily sorted.
 */
public class Card implements Comparable<Card> {

    private Rank rank;
    private Suit suit;
    
    public Card(int rank, char suit) {
        this(Rank.fromValue(rank), Suit.fromChar(suit));
    }
    
    public Card(String rank, String suit) {
        this(Rank.fromString(rank), Suit.fromString(suit));
    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
    
    public Card(Card c) {
        this.rank = c.rank;
        this.suit = c.suit;
    }

    public int getValue() {
        return rank.getValue();
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }

    @Override
    public int compareTo(Card otherCard) {
        return getRank().compareTo(otherCard.getRank());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Card otherCard = (Card)obj;
        if (otherCard.rank != this.rank || otherCard.suit != this.suit) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }
    
    public enum Suit {
        CLUBS    ('C'),
        SPADES   ('S'),
        HEARTS   ('H'),
        DIAMONDS ('D');
        
        private final char letter;
        
        private Suit(char letter) {
            this.letter = letter;
        }
        
        private Suit(String letter) {
            this(letter.toUpperCase().charAt(0));
        }
        
        public static Suit fromChar(char letter) {
            for (Suit suit : Suit.values()) {
                if (suit.letter == letter) {
                    return suit;
                }
            }
            throw new IllegalArgumentException("Invalid suit character: " + letter);
        }
        
        public static Suit fromString(String suit) {
            if (suit == null || suit.isEmpty()) {
                throw new IllegalArgumentException("Suit cannot be null or empty");
            }
            switch (suit.toUpperCase()) {
                case "C": 
                case "CLUBS": return CLUBS;
                case "S": 
                case "SPADES": return SPADES;
                case "H": 
                case "HEARTS": return HEARTS;
                case "D": 
                case "DIAMONDS": return DIAMONDS;
                default: throw new IllegalArgumentException("Invalid suit string: " + suit);
            }
        }

        /**
        * A fancy override of toString() that prints the Unicode character (UTF-16)
        * corresponding to the suit's image.
        */
        @Override
        public String toString() {
            switch(this) {
                case SPADES:   return "\u2660";
                case HEARTS:   return "\u2661";
                case DIAMONDS: return "\u2662";
                case CLUBS:    return "\u2663";
                default:       return "";
            }
        }
    }
    
    public enum Rank {
        DEUCE (2),
        THREE (3),
        FOUR  (4),
        FIVE  (5),
        SIX   (6),
        SEVEN (7),
        EIGHT (8),
        NINE  (9),
        TEN   (10),
        JACK  (11),
        QUEEN (12),
        KING  (13),
        ACE   (14);

        private final int value;

        private Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
        
        public static Rank fromValue(int value) {
            for (Rank rank : Rank.values()) {
                if (rank.getValue() == value) {
                    return rank;
                }
            }
            throw new IllegalArgumentException("Invalid rank value: " + value);
        }
        
        public static Rank fromString(String rank) {
            if (rank == null || rank.isEmpty()) {
                throw new IllegalArgumentException("Rank cannot be null or empty");
            }
            switch (rank.toUpperCase()) {
                case "2": 
                case "DEUCE": 
                case "TWO": return DEUCE;
                case "3": 
                case "THREE": return THREE;
                case "4": 
                case "FOUR": return FOUR;
                case "5": 
                case "FIVE": return FIVE;
                case "6": 
                case "SIX": return SIX;
                case "7": 
                case "SEVEN": return SEVEN;
                case "8": 
                case "EIGHT": return EIGHT;
                case "9": 
                case "NINE": return NINE;
                case "10": 
                case "T":
                case "TEN": return TEN;
                case "J": 
                case "JACK": return JACK;
                case "Q": 
                case "QUEEN": return QUEEN;
                case "K": 
                case "KING": return KING;
                case "A": 
                case "ACE": return ACE;
                default: throw new IllegalArgumentException("Invalid rank string: " + rank);
            }
        }

        /**
         * The string representation of Rank is either the number of the card,
         * or the first letter of the rank (J,Q,K,A) if it's a face card.
         */
        @Override
        public String toString() {
            if (value > 1 && value < 11) {
                return Integer.toString(value);
            } else {
                return name().toString().substring(0, 1);
            }
        }
    }
}
