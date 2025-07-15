package com.akhayat.poker.simulator.evaluator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.akhayat.poker.simulator.card.Card;
import com.akhayat.poker.simulator.card.PokerHand;

public class PokerHandEvaluatorTest {

    private PokerHandEvaluation evaluateHand(String... cards) {
        return new PokerHand(List.of(new Card(cards[0], cards[1]),
                       new Card(cards[2], cards[3]),
                       new Card(cards[4], cards[5]),
                       new Card(cards[6], cards[7]),
                       new Card(cards[8], cards[9]))).getEvaluation();
    }
    
    @Test
    public void testStraightFlush() {
        PokerHandEvaluation evaluation = evaluateHand(
                "10", "C", 
                "J", "C", 
                "Q", "C", 
                "K", "C", 
                "A", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.STRAIGHT_FLUSH, evaluation.getHandType());
        assertEquals(Card.Rank.ACE, evaluation.getStrength());
        assertEquals(List.of(), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "4", "d", 
                "8", "d", 
                "7", "d", 
                "5", "d", 
                "6", "d");
        
        assertEquals(PokerHandEvaluation.PokerHandType.STRAIGHT_FLUSH, evaluation.getHandType());
        assertEquals(Card.Rank.EIGHT, evaluation.getStrength());
        assertEquals(List.of(), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "3", "h", 
                "2", "h", 
                "A", "h", 
                "5", "h", 
                "4", "h");
        
        assertEquals(PokerHandEvaluation.PokerHandType.STRAIGHT_FLUSH, evaluation.getHandType());
        assertEquals(Card.Rank.FIVE, evaluation.getStrength());
        assertEquals(List.of(), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "2", "s", 
                "3", "s", 
                "4", "s", 
                "5", "s", 
                "7", "s");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.STRAIGHT_FLUSH, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "q", "d", 
                "9", "s", 
                "8", "s", 
                "10", "s", 
                "j", "s");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.STRAIGHT_FLUSH, evaluation.getHandType());
    }
    
    @Test
    public void testFourOfAKind() {
        PokerHandEvaluation evaluation = evaluateHand(
                "2", "C", 
                "2", "D", 
                "3", "H", 
                "2", "S", 
                "2", "H");
        
        assertEquals(PokerHandEvaluation.PokerHandType.FOUR_OF_A_KIND, evaluation.getHandType());
        assertEquals(Card.Rank.DEUCE, evaluation.getStrength());
        assertEquals(List.of(new Card("3", "H")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "A", "C", 
                "A", "D", 
                "A", "H", 
                "A", "S", 
                "K", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.FOUR_OF_A_KIND, evaluation.getHandType());
        assertEquals(Card.Rank.ACE, evaluation.getStrength());
        assertEquals(List.of(new Card("K", "C")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "j", "C", 
                "j", "D", 
                "10", "H", 
                "10", "S", 
                "j", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.FOUR_OF_A_KIND, evaluation.getHandType());
    }
    
    @Test
    public void testFullHouse() {
        PokerHandEvaluation evaluation = evaluateHand(
                "2", "C", 
                "2", "D", 
                "3", "H", 
                "2", "S", 
                "3", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.FULL_HOUSE, evaluation.getHandType());
        assertEquals(Card.Rank.DEUCE, evaluation.getStrength());
        assertEquals(Card.Rank.THREE, evaluation.getSecondaryStrength());
        assertEquals(List.of(), evaluation.getKickers());
        
        evaluation = evaluateHand(
                "K", "C", 
                "A", "D", 
                "A", "H", 
                "K", "S", 
                "A", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.FULL_HOUSE, evaluation.getHandType());
        assertEquals(Card.Rank.ACE, evaluation.getStrength());
        assertEquals(Card.Rank.KING, evaluation.getSecondaryStrength());
        assertEquals(List.of(), evaluation.getKickers());
        
        evaluation = evaluateHand(
                "j", "C", 
                "j", "D", 
                "9", "H", 
                "10", "S", 
                "j", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.FULL_HOUSE, evaluation.getHandType());
    }
    
    @Test
    public void testFlush() {
        PokerHandEvaluation evaluation = evaluateHand(
                "2", "C", 
                "4", "C", 
                "6", "C", 
                "8", "C", 
                "10", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.FLUSH, evaluation.getHandType());
        assertEquals(Card.Rank.TEN, evaluation.getStrength());
        assertEquals(List.of(new Card("8", "C"), new Card("6", "C"), new Card("4", "C"), new Card("2", "C")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "4", "H", 
                "K", "H", 
                "Q", "H", 
                "J", "H", 
                "10", "H");
        
        assertEquals(PokerHandEvaluation.PokerHandType.FLUSH, evaluation.getHandType());
        assertEquals(Card.Rank.KING, evaluation.getStrength());
        assertEquals(List.of(new Card("Q", "H"), new Card("J", "H"), new Card("10", "H"), new Card("4", "H")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "6", "S",
                "3", "S",
                "4", "S",
                "5", "S",
                "7", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.FLUSH, evaluation.getHandType());
    }
    
    @Test
    public void testStraight() {
        PokerHandEvaluation evaluation = evaluateHand(
                "q", "C", 
                "j", "D", 
                "8", "H", 
                "10", "S", 
                "9", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.STRAIGHT, evaluation.getHandType());
        assertEquals(Card.Rank.QUEEN, evaluation.getStrength());
        assertEquals(List.of(), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "5", "C", 
                "3", "D", 
                "6", "H", 
                "4", "S", 
                "7", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.STRAIGHT, evaluation.getHandType());
        assertEquals(Card.Rank.SEVEN, evaluation.getStrength());
        assertEquals(List.of(), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "4", "H", 
                "5", "D", 
                "2", "S", 
                "3", "C", 
                "A", "H");
        
        assertEquals(PokerHandEvaluation.PokerHandType.STRAIGHT, evaluation.getHandType());
        assertEquals(Card.Rank.FIVE, evaluation.getStrength());
        assertEquals(List.of(), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "3", "S",
                "2", "H",
                "4", "S",
                "5", "C",
                "7", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.STRAIGHT, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "A", "S",
                "K", "S",
                "Q", "S",
                "J", "S",
                "10", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.STRAIGHT, evaluation.getHandType());
    }
    
    @Test
    public void testThreeOfAKind() {
        PokerHandEvaluation evaluation = evaluateHand(
                "2", "C", 
                "2", "D", 
                "A", "H", 
                "2", "S", 
                "4", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.THREE_OF_A_KIND, evaluation.getHandType());
        assertEquals(Card.Rank.DEUCE, evaluation.getStrength());
        assertEquals(List.of(new Card("A", "H"), new Card("4", "C")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "Q", "C", 
                "A", "D", 
                "A", "H", 
                "K", "S", 
                "A", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.THREE_OF_A_KIND, evaluation.getHandType());
        assertEquals(Card.Rank.ACE, evaluation.getStrength());
        assertEquals(List.of(new Card("K", "S"), new Card("Q", "C")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "j", "C",
                "j", "D",
                "10", "H",
                "10", "S",
                "j", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.THREE_OF_A_KIND, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "A", "C",
                "j", "D",
                "10", "H",
                "10", "S",
                "j", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.THREE_OF_A_KIND, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "j", "C",
                "j", "D",
                "j", "H",
                "10", "S",
                "j", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.THREE_OF_A_KIND, evaluation.getHandType());
    }
    
    @Test
    public void testTwoPair() {
        PokerHandEvaluation evaluation = evaluateHand(
                "2", "C", 
                "4", "C", 
                "3", "H", 
                "2", "S", 
                "3", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.TWO_PAIR, evaluation.getHandType());
        assertEquals(Card.Rank.THREE, evaluation.getStrength());
        assertEquals(Card.Rank.DEUCE, evaluation.getSecondaryStrength());
        assertEquals(List.of(new Card("4", "C")), evaluation.getKickers());
        
        evaluation = evaluateHand(
                "A", "C", 
                "A", "D", 
                "6", "H", 
                "6", "S", 
                "Q", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.TWO_PAIR, evaluation.getHandType());
        assertEquals(Card.Rank.ACE, evaluation.getStrength());
        assertEquals(Card.Rank.SIX, evaluation.getSecondaryStrength());
        assertEquals(List.of(new Card("Q", "C")), evaluation.getKickers());
        
        evaluation = evaluateHand(
                "j", "C",
                "j", "D",
                "10", "H",
                "10", "S",
                "j", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.TWO_PAIR, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "4", "C",
                "4", "D",
                "4", "H",
                "10", "S",
                "4", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.TWO_PAIR, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "j", "C",
                "j", "D",
                "10", "H",
                "3", "S",
                "6", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.TWO_PAIR, evaluation.getHandType());
    }
    
    @Test
    public void testPair() {
        PokerHandEvaluation evaluation = evaluateHand(
                "2", "C", 
                "4", "C", 
                "3", "H", 
                "2", "S", 
                "5", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.PAIR, evaluation.getHandType());
        assertEquals(Card.Rank.DEUCE, evaluation.getStrength());
        assertEquals(List.of(new Card("5", "C"), new Card("4", "C"), new Card("3", "H")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "A", "C", 
                "A", "D", 
                "6", "H", 
                "Q", "S", 
                "K", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.PAIR, evaluation.getHandType());
        assertEquals(Card.Rank.ACE, evaluation.getStrength());
        assertEquals(List.of(new Card("K", "C"), new Card("Q", "S"), new Card("6", "H")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "j", "C",
                "A", "D",
                "10", "H",
                "3", "S",
                "j", "S");
        
        assertEquals(PokerHandEvaluation.PokerHandType.PAIR, evaluation.getHandType());
        assertEquals(Card.Rank.JACK, evaluation.getStrength());
        assertEquals(List.of(new Card("A", "D"), new Card("10", "H"), new Card("3", "S")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "4", "C",
                "4", "D",
                "4", "H",
                "10", "S",
                "4", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.PAIR, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "Q", "C",
                "Q", "D",
                "4", "H",
                "10", "S",
                "4", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.PAIR, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "10", "C",
                "10", "D",
                "7", "H",
                "10", "S",
                "7", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.PAIR, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "10", "C",
                "10", "D",
                "A", "H",
                "10", "S",
                "7", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.PAIR, evaluation.getHandType());
    }
    
    @Test
    public void testHighCard() {
        PokerHandEvaluation evaluation = evaluateHand(
                "2", "C", 
                "T", "C", 
                "3", "H", 
                "5", "S", 
                "6", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.HIGH_CARD, evaluation.getHandType());
        assertEquals(Card.Rank.TEN, evaluation.getStrength());
        assertEquals(List.of(new Card("6", "C"), new Card("5", "S"), new Card("3", "H"), new Card("2", "C")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "A", "C", 
                "K", "D", 
                "Q", "H", 
                "J", "S", 
                "9", "C");
        
        assertEquals(PokerHandEvaluation.PokerHandType.HIGH_CARD, evaluation.getHandType());
        assertEquals(Card.Rank.ACE, evaluation.getStrength());
        assertEquals(List.of(new Card("K", "D"), new Card("Q", "H"), new Card("J", "S"), new Card("9", "C")), evaluation.getKickers());
        assertEquals(null, evaluation.getSecondaryStrength());
        
        evaluation = evaluateHand(
                "7", "C",
                "8", "D",
                "9", "H",
                "10", "S",
                "J", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.HIGH_CARD, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "A", "C",
                "8", "D",
                "9", "H",
                "A", "S",
                "J", "S");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.HIGH_CARD, evaluation.getHandType());
        
        evaluation = evaluateHand(
                "2", "C",
                "8", "C",
                "9", "C",
                "A", "C",
                "J", "C");
        
        assertNotEquals(PokerHandEvaluation.PokerHandType.HIGH_CARD, evaluation.getHandType());
    }
}
