package com.akhayat.poker.simulator.card;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.akhayat.poker.simulator.evaluator.PokerHandEvaluation;
import com.akhayat.poker.simulator.evaluator.PokerHandEvaluator;

public class SevenCardHand extends PokerHand {
    
    public SevenCardHand(Card... cards) {
        super(Arrays.asList(cards));
    }
    
    public SevenCardHand(List<Card> cards) {
        super(7, cards);
        this.evaluation = evaluateSevenCards(cards);
    }
    
   public static SevenCardHand fromStrings(String... cardStrings) {
       return new SevenCardHand(PokerHand.cardListFromStrings(cardStrings));
   }
   
   private PokerHandEvaluation evaluateSevenCards(List<Card> cards) {
       PokerHandEvaluation bestEvaluation = null;
       for (int i = 0; i < cards.size() - 1; i++) {
           for (int j = i + 1; j < cards.size(); j++) {
               List<Card> subHand = cards.stream()
                       .map(Card::new)
                       .collect(Collectors.toList());
               subHand.removeAll(List.of(cards.get(i), cards.get(j)));
               PokerHandEvaluation subEval = PokerHandEvaluator.evaluate(new PokerHand(subHand));
               bestEvaluation = Optional.ofNullable(bestEvaluation).orElse(subEval);
               bestEvaluation = bestEvaluation.beats(subEval) ? bestEvaluation : subEval;
           }
       }
       return bestEvaluation;
   }
}
