package com.akhayat.poker.simulator.evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.akhayat.poker.simulator.card.Card;
import com.akhayat.poker.simulator.card.PokerHand;
import com.akhayat.poker.simulator.evaluator.PokerHandEvaluation.PokerHandType;

public class PokerHandEvaluator {
    
    public static final byte HAND_SIZE = 5;

    PokerHand hand;
    
    private Map<Card.Rank, Integer> histogram;

    /**
     * Constructs a five card hand.
     * Surprise, suprise, it freaks out if hand isn't of length 5.
     */
    public PokerHandEvaluator(PokerHand hand) {
        this.hand = hand;
        setHistogram();
    }
    
    private void setHistogram() {
        histogram = hand.getOrdered().stream()
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
        return hand.getOrdered().stream()
                .filter(card -> card.getRank() != strength && card.getRank() != secondaryStrength)
                .collect(Collectors.toList());
    }

    private Optional<PokerHandEvaluation> highCardValue() {
        return Optional.of(new PokerHandEvaluation(
                PokerHandType.HIGH_CARD, hand.getOrdered().get(0).getRank(),
                getKickers(hand.getOrdered().get(0).getRank())));
    }
    
    private Optional<PokerHandEvaluation> pairValue() {
       Card.Rank strength = getStrength(2);
       if (strength != null) {
           return Optional.of(new PokerHandEvaluation(
                   PokerHandType.PAIR, strength, getKickers(strength)));
       }
       return Optional.empty();
    }

    private Optional<PokerHandEvaluation> twoPairValue() {
        Card.Rank strength = getStrength(2);
        Card.Rank secondaryStrength = getStrength(2, strength);
        if (strength != null && secondaryStrength != null) {
            return Optional.of(new PokerHandEvaluation(
                    PokerHandType.TWO_PAIR, strength, secondaryStrength,
                    getKickers(strength, secondaryStrength)));
        }
        return Optional.empty();
    }

    private Optional<PokerHandEvaluation> threeOfAKindValue() {
        Card.Rank strength = getStrength(3);
        if (strength != null) {
            return Optional.of(new PokerHandEvaluation(
                    PokerHandType.THREE_OF_A_KIND, strength, getKickers(strength)));
        }
        return Optional.empty();
    }

    private Optional<PokerHandEvaluation> straightValue() {
        if (containsStraight()) {
            Card.Rank strength = aceToFiveStraight() ? Card.Rank.FIVE : hand.getOrdered().get(0).getRank();
            return Optional.of(new PokerHandEvaluation(PokerHandType.STRAIGHT, strength, new ArrayList<>(0)));
        }
        return Optional.empty();
    }
    
    private boolean containsStraight() {
        return IntStream.range(0, hand.getOrdered().size() - 1)
                .allMatch(i -> hand.getOrdered().get(i).getRank().getValue() - 1
                        == hand.getOrdered().get(i + 1).getRank().getValue())
                || aceToFiveStraight();
    }

    private boolean aceToFiveStraight() {
        return hand.getOrdered().get(0).getRank() == Card.Rank.ACE
                && hand.getOrdered().get(1).getRank() == Card.Rank.FIVE
                && hand.getOrdered().get(2).getRank() == Card.Rank.FOUR
                && hand.getOrdered().get(3).getRank() == Card.Rank.THREE
                && hand.getOrdered().get(4).getRank() == Card.Rank.DEUCE;
    }

    private Optional<PokerHandEvaluation> flushValue() {
        if (containsFlush()) {
            return Optional.of(new PokerHandEvaluation(PokerHandType.FLUSH,
                    hand.getOrdered().get(0).getRank(), getKickers(hand.getOrdered().get(0).getRank())));
        }
        return Optional.empty();
    }
    
    private boolean containsFlush() {
        return hand.getHand().stream().allMatch(card -> card.getSuit() == hand.getOrdered().get(0).getSuit());
    }

    private Optional<PokerHandEvaluation> fullHouseValue() {
        Card.Rank strength = getStrength(3);
        Card.Rank secondaryStrength = getStrength(2, strength);
        if (strength != null && secondaryStrength != null) {
            return Optional.of(new PokerHandEvaluation(
                    PokerHandType.FULL_HOUSE, strength, secondaryStrength, null));
        }
        return Optional.empty();
    }

    private Optional<PokerHandEvaluation> fourOfAKindValue() {
        Card.Rank strength = getStrength(4);
        if (strength != null) {
            return Optional.of(new PokerHandEvaluation(
                    PokerHandType.FOUR_OF_A_KIND, strength, getKickers(strength)));
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
                    PokerHandType.STRAIGHT_FLUSH, straightValue.getStrength(), new ArrayList<>(0)));
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
    
    public static PokerHandEvaluation evaluate(PokerHand hand) {
        return new PokerHandEvaluator(hand).evaluate();
    }

    @Override
    public String toString() {
        return hand.toString() + " -> " + evaluate();
    }
    

}
