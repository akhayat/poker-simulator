package com.akhayat.poker.simulator.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.akhayat.poker.simulator.evaluator.PokerHandEvaluation;
import com.akhayat.poker.simulator.evaluator.PokerHandEvaluator;

public class PokerHand {
    
    private List<Card> hand;
    private List<Card> ordered;
    private PokerHandEvaluation evaluation = null;
    
    public static final byte HAND_SIZE = 5;
    
    public PokerHand(Card... cards) {
        this(Arrays.asList(cards));
    }
    
    public PokerHand(List<Card> hand) {
       if (hand == null || hand.size() != HAND_SIZE) {
           throw new IllegalArgumentException(
                   "Card list must contain " + HAND_SIZE + " cards.");
       }
       this.hand = hand;
       setOrdered();
       evaluation = PokerHandEvaluator.evaluate(this);
   }
   
   private void setOrdered() {
       ordered = hand.stream()
               .map(card -> new Card(card))
               .collect(Collectors.toList());
       ordered.sort(Collections.reverseOrder());
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
       throw new UnsupportedOperationException();
   }
   
   public boolean tiesWith(PokerHand other) {
       throw new UnsupportedOperationException();
   }
   
   public boolean losesTo(PokerHand other) {
       throw new UnsupportedOperationException();
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
   
   public static PokerHand fromStrings(String... cardStrings) {
       if (cardStrings.length % 2 != 0) {
           throw new IllegalArgumentException(
                   "Card strings must be in pairs of rank and suit.");
       }
       List<Card> cards = new ArrayList<>(cardStrings.length / 2);
       for (int i = 0; i < cardStrings.length; i += 2) {
           cards.add(new Card(cardStrings[i], cardStrings[i + 1]));
       }
       return new PokerHand(cards);
   }

}
