package com.akhayat.poker.simulator.evaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.akhayat.poker.simulator.card.Card;
import com.akhayat.poker.simulator.evaluator.PokerHandEvaluation.PokerHand;

public class FiveCardHandEvaluator {
    
    public static final byte HAND_SIZE = 5;

    /**
     * Represents the hand in whatever order it was created.
     */
    private List<Card> hand;
    /**
     * Represents the hand, but ordered from lowest to highest.
     */
    private List<Card> ordered;
    
    private Map<Card.Rank, Integer> histogram;

    /**
     * Constructs a five card hand.
     * Surprise, suprise, it freaks out if hand isn't of length 5.
     */
    public FiveCardHandEvaluator(List<Card> hand) {
        if (hand.size() != HAND_SIZE) {
            throw new IllegalArgumentException(
                    "Card array must contain " + HAND_SIZE + " cards.");
        }
        this.hand = hand;
        setOrdered();
        setHistogram();
    }

    /**
     * Creates a copy of hand, sorts it, and sticks it in ordered.
     */
    private void setOrdered() {
        ordered = hand.stream()
                .map(card -> new Card(card))
                .collect(Collectors.toList());
        ordered.sort(Collections.reverseOrder());
    }
    
    private void setHistogram() {
        histogram = ordered.stream()
                .collect(Collectors.groupingBy(
                        Card::getRank, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, e -> e.getValue().intValue()));
    }
    
    private Card.Rank getStrength(int numberOfCards) {
        return getStrength(numberOfCards, null);
    }
    
    private Card.Rank getStrength(int numberOfCards, Card.Rank excludedRank) {
        return histogram.entrySet().stream()
                .filter(e -> e.getValue() == numberOfCards && e.getKey() != excludedRank)
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    private List<Card> getKickers(Card.Rank strength) {
        return getKickers(strength, null);
    }
    
    private List<Card> getKickers(Card.Rank strength, Card.Rank secondaryStrength) {
        return ordered.stream()
                .filter(card -> card.getRank() != strength && card.getRank() != secondaryStrength)
                .collect(Collectors.toList());
    }

    private Optional<PokerHandEvaluation> highCardValue() {
        return Optional.of(new PokerHandEvaluation(
                PokerHand.HIGH_CARD, ordered.get(0).getRank(),
                getKickers(ordered.get(0).getRank())));
    }
    
    private Optional<PokerHandEvaluation> pairValue() {
       Card.Rank strength = getStrength(2);
       if (strength != null) {
           return Optional.of(new PokerHandEvaluation(
                   PokerHand.PAIR, strength, getKickers(strength)));
       }
       return Optional.empty();
    }

    private Optional<PokerHandEvaluation> twoPairValue() {
        Card.Rank strength = getStrength(2);
        Card.Rank secondaryStrength = getStrength(2, strength);
        if (strength != null && secondaryStrength != null) {
            return Optional.of(new PokerHandEvaluation(
                    PokerHand.TWO_PAIR, strength, secondaryStrength,
                    getKickers(strength, secondaryStrength)));
        }
        return Optional.empty();
    }

    private Optional<PokerHandEvaluation> threeOfAKindValue() {
        Card.Rank strength = getStrength(3);
        if (strength != null) {
            return Optional.of(new PokerHandEvaluation(
                    PokerHand.THREE_OF_A_KIND, strength, getKickers(strength)));
        }
        return Optional.empty();
    }

    private Optional<PokerHandEvaluation> straightValue() {
        if (containsStraight()) {
            Card.Rank strength = aceToFiveStraight() ? Card.Rank.FIVE : ordered.get(0).getRank();
            return Optional.of(new PokerHandEvaluation(PokerHand.STRAIGHT, strength, new ArrayList<>(0)));
        }
        return Optional.empty();
    }
    
    private boolean containsStraight() {
        return IntStream.range(0, ordered.size() - 1)
                .allMatch(i -> ordered.get(i).getRank().getValue() - 1
                        == ordered.get(i + 1).getRank().getValue())
                || aceToFiveStraight();
    }

    private boolean aceToFiveStraight() {
        return ordered.get(0).getRank() == Card.Rank.ACE
                && ordered.get(1).getRank() == Card.Rank.FIVE
                && ordered.get(2).getRank() == Card.Rank.FOUR
                && ordered.get(3).getRank() == Card.Rank.THREE
                && ordered.get(4).getRank() == Card.Rank.DEUCE;
    }

    private Optional<PokerHandEvaluation> flushValue() {
        if (containsFlush()) {
            return Optional.of(new PokerHandEvaluation(PokerHand.FLUSH,
                    ordered.get(0).getRank(), getKickers(ordered.get(0).getRank())));
        }
        return Optional.empty();
    }
    
    private boolean containsFlush() {
        return hand.stream().allMatch(card -> card.getSuit() == ordered.get(0).getSuit());
    }

    private Optional<PokerHandEvaluation> fullHouseValue() {
        Card.Rank strength = getStrength(3);
        Card.Rank secondaryStrength = getStrength(2, strength);
        if (strength != null && secondaryStrength != null) {
            return Optional.of(new PokerHandEvaluation(
                    PokerHand.FULL_HOUSE, strength, secondaryStrength, null));
        }
        return Optional.empty();
    }

    private Optional<PokerHandEvaluation> fourOfAKindValue() {
        Card.Rank strength = getStrength(4);
        if (strength != null) {
            return Optional.of(new PokerHandEvaluation(
                    PokerHand.FOUR_OF_A_KIND, strength, getKickers(strength)));
        }
        return Optional.empty();
    }

    /**
     * Returns true if the hand contains both a straight and a flush.
     */
    private Optional<PokerHandEvaluation> straightFlushValue() {
        PokerHandEvaluation straightValue = straightValue().orElse(null);
        if (straightValue != null && containsFlush()) {
            return Optional.of(new PokerHandEvaluation(
                    PokerHand.STRAIGHT_FLUSH, straightValue.getStrength(), new ArrayList<>(0)));
        }
        return Optional.empty();
    }
    
    public PokerHandEvaluation evaluate() {
        return Stream.of(straightFlushValue(),
                fourOfAKindValue(),
                fullHouseValue(),
                flushValue(),
                straightValue(),
                threeOfAKindValue(),
                twoPairValue(),
                pairValue(),
                highCardValue())
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst().get();
    }
    
    public static PokerHandEvaluation evaluate(List<Card> hand) {
        return new FiveCardHandEvaluator(hand).evaluate();
    }

    @Override
    public String toString() {
        return hand.toString() + " -> " + evaluate();
    }
    
    public static void main(String[] args) {
        List<Card> hand = List.of(
                new Card("A", "s"),
                new Card("A", "d"),
                new Card("2", "c"),
                new Card("2", "h"),
                new Card("2", "d"));
        
        FiveCardHandEvaluator evaluator = new FiveCardHandEvaluator(hand);
        System.out.println(evaluator);
    }

}
