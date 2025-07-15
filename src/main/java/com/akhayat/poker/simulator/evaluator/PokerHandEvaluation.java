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
    
    @Override
    public String toString() {
        return handType + ": " + strength + " high";
    }
    
    public boolean beats(PokerHandEvaluation other) {
        if (this.handType.getRanking() != other.handType.getRanking()) {
            return this.handType.getRanking() > other.handType.getRanking();
        } else if (this.getStrength().getValue() != other.getStrength().getValue()) {
            return this.getStrength().getValue() > other.getStrength().getValue();
        } else if (this.getSecondaryStrength() != other.getSecondaryStrength()) {
            return this.getSecondaryStrength().getValue() > other.getSecondaryStrength().getValue();
        } else {
           for (int i = 0; i < kickers.size(); i++) {
               int compare = kickers.get(i).compareTo(other.kickers.get(i));
               if (compare != 0) {
                   return compare > 0;
               }
           }
           return false; 
        }
        
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
