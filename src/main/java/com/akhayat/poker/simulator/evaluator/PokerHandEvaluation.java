package com.akhayat.poker.simulator.evaluator;

import java.util.List;

import com.akhayat.poker.simulator.card.Card;
import com.akhayat.poker.simulator.card.Card.Rank;

public class PokerHandEvaluation {
    
    private Rank strength;
    private Rank secondaryStrength;
    private List<Card> kickers;
    private PokerHand handType;
    
    public PokerHandEvaluation(PokerHand handType, Rank strength, List<Card> kickers) {
        this(handType, strength, null, kickers);
    }
    
    public PokerHandEvaluation(PokerHand handType, Rank strength, Rank secondaryStrength, List<Card> kickers) {
        this.handType = handType;
        this.strength = strength;
        this.secondaryStrength = secondaryStrength;
        this.kickers = kickers;
    }

    public Rank getStrength() {
        return strength;
    }

    public List<Card> getKickers() {
        return kickers;
    }

    public PokerHand getHandType() {
        return handType;
    }

    public Rank getSecondaryStrength() {
        return secondaryStrength;
    }
    
    @Override
    public String toString() {
        return handType + ": " + strength + " high";
    }

    public enum PokerHand {
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
        
        PokerHand(int ranking) {
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
