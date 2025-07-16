package com.akhayat.poker.simulator.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.akhayat.poker.simulator.evaluator.PokerHandEvaluation;
import com.akhayat.poker.simulator.evaluator.PokerHandEvaluator;

public class SevenCardHand {
    
    private List<Card> sevenCards;
    private PokerHand bestFiveCardHand;
    private PokerHandEvaluation evaluation;
    
    public static final byte HAND_SIZE = 7;
    
    public SevenCardHand(Card... cards) {
        this(List.of(cards));
    }
    
    public SevenCardHand(List<Card> cards) {
        if (cards == null || cards.size() != HAND_SIZE) {
            throw new IllegalArgumentException(
                    "Card list must contain " + HAND_SIZE + " cards.");
        }
        this.sevenCards = new ArrayList<>(cards);
        findBestFiveCardHand();
    }
    
    private void findBestFiveCardHand() {
        List<List<Card>> allCombinations = generateFiveCardCombinations();
        PokerHand bestHand = null;
        PokerHandEvaluation bestEvaluation = null;
        
        for (List<Card> combination : allCombinations) {
            PokerHand currentHand = new PokerHand(combination);
            PokerHandEvaluation currentEvaluation = currentHand.getEvaluation();
            
            if (bestEvaluation == null || currentEvaluation.beats(bestEvaluation)) {
                bestHand = currentHand;
                bestEvaluation = currentEvaluation;
            }
        }
        
        this.bestFiveCardHand = bestHand;
        this.evaluation = bestEvaluation;
    }
    
    private List<List<Card>> generateFiveCardCombinations() {
        List<List<Card>> combinations = new ArrayList<>();
        generateCombinations(sevenCards, new ArrayList<>(), 0, 5, combinations);
        return combinations;
    }
    
    private void generateCombinations(List<Card> cards, List<Card> current, 
                                    int start, int remaining, List<List<Card>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = start; i <= cards.size() - remaining; i++) {
            current.add(cards.get(i));
            generateCombinations(cards, current, i + 1, remaining - 1, result);
            current.remove(current.size() - 1);
        }
    }
    
    public List<Card> getSevenCards() {
        return new ArrayList<>(sevenCards);
    }
    
    public PokerHand getBestFiveCardHand() {
        return bestFiveCardHand;
    }
    
    public PokerHandEvaluation getEvaluation() {
        return evaluation;
    }
    
    public boolean beats(SevenCardHand other) {
        return this.evaluation.beats(other.evaluation);
    }
    
    public boolean tiesWith(SevenCardHand other) {
        return !this.beats(other) && !other.beats(this);
    }
    
    public boolean losesTo(SevenCardHand other) {
        return other.beats(this);
    }
    
    public List<Card> getHand() {
        return sevenCards;
    }
    
    public List<Card> getOrdered() {
        List<Card> ordered = sevenCards.stream()
                .map(card -> new Card(card))
                .collect(Collectors.toList());
        ordered.sort(Collections.reverseOrder());
        return ordered;
    }
    
    @Override
    public String toString() {
        return sevenCards + " -> Best: " + bestFiveCardHand.getHand() + " -> " + evaluation;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SevenCardHand)) {
            return false;
        }
        SevenCardHand other = (SevenCardHand) o;
        return sevenCards.equals(other.sevenCards);
    }
    
    @Override
    public int hashCode() {
        return sevenCards.hashCode();
    }
    
    public static SevenCardHand fromStrings(String... cardStrings) {
        return new SevenCardHand(PokerHand.cardListFromStrings(cardStrings));
    }
}
