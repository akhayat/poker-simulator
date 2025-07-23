package com.akhayat.poker.simulator.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.akhayat.poker.simulator.evaluator.PokerHandEvaluation;
import com.akhayat.poker.simulator.evaluator.PokerHandEvaluator;

public abstract class PokerHand {
    
    List<Card> hand;
    List<Card> ordered;
    PokerHandEvaluation evaluation = null;
    
    private static final List<Integer> SUPPORTED_HAND_SIZES = List.of(5, 7);
    
    public PokerHand(List<Card> hand, int handSize) {
       if (hand == null || hand.size() != handSize) {
           throw new IllegalArgumentException(
                   "Card list must contain " + handSize + " cards.");
       }
       this.hand = hand;
       setOrdered();
       evaluation = evaluate();
   }
   
   private void setOrdered() {
       ordered = hand.stream()
               .map(card -> new Card(card))
               .collect(Collectors.toList());
       ordered.sort(Collections.reverseOrder());
   }
   
   protected PokerHandEvaluation evaluate() {
       return PokerHandEvaluator.evaluate(this);
   }
      
   public List<Card> getOrdered() {
       return ordered;
   }
   
   public List<Card> getHand() {
       return hand;
   }
   
   public PokerHandEvaluation getEvaluation() {
       return evaluation;
   }
   
   @Override
   public String toString() {
       return hand + " -> " + evaluation;
   }
   
   public boolean beats(PokerHand other) {
       return this.evaluation.beats(other.evaluation);
   }

   public boolean tiesWith(PokerHand other) {
       return !this.beats(other) && !other.beats(this);
   }

   public boolean losesTo(PokerHand other) {
       return other.beats(this);
   }
   
   @Override
   public boolean equals(Object o) {
       if (this == o) {
           return true;
       }
       if (!(o instanceof PokerHand)) {
           return false;
       }
       PokerHand other = (PokerHand) o;
       return hand.equals(other.hand);
   }
   
   @Override
   public int hashCode() {
       return hand.hashCode();
   }
   
    // Add helper method for SevenCardHand
    protected static List<Card> cardListFromStrings(String... cardStrings) {
        if (cardStrings.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "Card strings must be in pairs of rank and suit.");
        }
        List<Card> cards = new ArrayList<>(cardStrings.length / 2);
        for (int i = 0; i < cardStrings.length; i += 2) {
            cards.add(new Card(cardStrings[i], cardStrings[i + 1]));
        }
        return cards;
    }
    
    
    public static PokerHand fromStrings(String... cardStrings) {
        List<Card> cards = cardListFromStrings(cardStrings);
        if (cards.size() == 5) {
            return new FiveCardHand(cards);
        } else if (cards.size() == 7) {
            return new SevenCardHand(cards);
        }
        throw new IllegalArgumentException(
               "Unsupported hand size: " + cards.size() + ". Supported sizes are: " + SUPPORTED_HAND_SIZES);
    }

}
