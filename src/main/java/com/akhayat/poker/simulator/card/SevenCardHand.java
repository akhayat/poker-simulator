package com.akhayat.poker.simulator.card;

import java.util.List;

public class SevenCardHand extends PokerHand {
    
    public SevenCardHand(Card... cards) {
        super(cards);
    }
    
    public SevenCardHand(List<Card> cards) {
        super(cards);
    }
    
   public static SevenCardHand fromStrings(String... cardStrings) {
       return new SevenCardHand(PokerHand.cardListFromStrings(cardStrings));
   }
}
