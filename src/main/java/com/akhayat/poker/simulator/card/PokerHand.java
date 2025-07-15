package com.akhayat.poker.simulator.card;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.akhayat.poker.simulator.evaluator.PokerHandEvaluation;
import com.akhayat.poker.simulator.evaluator.PokerHandEvaluator;

public class PokerHand {
    
    List<Card> hand;
    List<Card> ordered;
    PokerHandEvaluation evaluation = null;
    
    public static final byte HAND_SIZE = 5;
    
    public PokerHand(Card... cards) {
        this(Arrays.asList(cards));
    }
    
    public PokerHand(List<Card> hand) {
       if (hand.size() != HAND_SIZE) {
           throw new IllegalArgumentException(
                   "Card array must contain " + HAND_SIZE + " cards.");
       }
       this.hand = hand;
       setOrdered();
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
       if (evaluation == null) {
           evaluation = PokerHandEvaluator.evaluate(this);
       }
       return evaluation;
   }
   
   public boolean beats(PokerHand other) {
       return evaluation.beats(other.evaluation);
   }
   
   public boolean ties(PokerHand other) {
       return !this.beats(other) && !other.beats(this);
   }
   
   public boolean losesTo(PokerHand other) {
       return !this.beats(other) && other.beats(this);
   }
   
   @Override
   public String toString() {
       return hand + " -> " + evaluation;
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

}
