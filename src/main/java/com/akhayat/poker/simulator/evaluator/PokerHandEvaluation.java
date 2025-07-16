package com.akhayat.poker.simulator.evaluator;

import java.util.ArrayList;
import java.util.List;

import com.akhayat.poker.simulator.card.Card;
import com.akhayat.poker.simulator.card.Card.Rank;

public class PokerHandEvaluation {
    
    private Rank strength;
    private Rank secondaryStrength;
    private List<Card> kickers = new ArrayList<>(4);
    private PokerHandType handType;
    
    public PokerHandEvaluation(PokerHandType handType, Rank strength) {
        this(handType, strength, null, null);
    }
    
    public PokerHandEvaluation(PokerHandType handType, Rank strength, List<Card> kickers) {
        this(handType, strength, null, kickers);
    }
    
    public PokerHandEvaluation(PokerHandType handType, Rank strength, Rank secondaryStrength) {
        this(handType, strength, secondaryStrength, null);
    }
    
    public PokerHandEvaluation(PokerHandType handType, Rank strength, Rank secondaryStrength, List<Card> kickers) {
        this.handType = handType;
        this.strength = strength;
        this.secondaryStrength = secondaryStrength;
        this.kickers = kickers == null ? this.kickers : kickers;
    }

    public Rank getStrength() {
        return strength;
    }

    public List<Card> getKickers() {
        return kickers;
    }

    public PokerHandType getHandType() {
        return handType;
    }

    public Rank getSecondaryStrength() {
        return secondaryStrength;
    }
    
    public boolean beats(PokerHandEvaluation other) {
        // First compare hand type rankings
        if (this.handType.getRanking() != other.handType.getRanking()) {
            return this.handType.getRanking() > other.handType.getRanking();
        }
        
        // Same hand type, compare primary strength
        if (this.strength != other.strength) {
            return this.strength.compareTo(other.strength) > 0;
        }
        
        // Same primary strength, compare secondary strength if both exist
        if (this.secondaryStrength != null && other.secondaryStrength != null) {
            if (this.secondaryStrength != other.secondaryStrength) {
                return this.secondaryStrength.compareTo(other.secondaryStrength) > 0;
            }
        } else if (this.secondaryStrength != null) {
            return true; // This hand has secondary strength, other doesn't
        } else if (other.secondaryStrength != null) {
            return false; // Other hand has secondary strength, this doesn't
        }
        
        // Compare kickers high to low
        int minKickers = Math.min(this.kickers.size(), other.kickers.size());
        for (int i = 0; i < minKickers; i++) {
            int comparison = this.kickers.get(i).getRank().compareTo(other.kickers.get(i).getRank());
            if (comparison != 0) {
                return comparison > 0;
            }
        }
        
        // All comparisons are equal
        return false;
    }
    
    @Override
    public String toString() {
        return handType + ": " + strength + " high";
    }
    
    

    public enum PokerHandType {
        HIGH_CARD(0),
        PAIR(1),
        TWO_PAIR(2),
        THREE_OF_A_KIND(3),
        STRAIGHT(4),
        FLUSH(5),
        FULL_HOUSE(6),
        FOUR_OF_A_KIND(7),
        STRAIGHT_FLUSH(8);
        
        byte ranking;
        
        PokerHandType(int ranking) {
            this.ranking = (byte) ranking;
        }
        public byte getRanking() {
            return ranking;
        }
        
        @Override
        public String toString() {
            return this.name().replace("_", " ").toLowerCase();
        }
    }
}
