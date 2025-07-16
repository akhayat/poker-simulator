package com.akhayat.poker.simulator.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.akhayat.poker.simulator.evaluator.PokerHandEvaluation;

class SevenCardHandTest {

    @Test
    public void testSevenCardHandConstructor() {
        SevenCardHand hand = new SevenCardHand(List.of(
                new Card("a", "c"),
                new Card("6", "h"),
                new Card("7", "d"),
                new Card("8", "s"),
                new Card("9", "c"),
                new Card("q", "h"),
                new Card("t", "d")
        ));
        
        assertThat(hand.getHand()).isEqualTo(List.of(
                new Card("a", "c"),
                new Card("6", "h"),
                new Card("7", "d"),
                new Card("8", "s"),
                new Card("9", "c"),
                new Card("q", "h"),
                new Card("t", "d")
        ));
        assertThat(hand.getOrdered()).isEqualTo(List.of(
                new Card("a", "c"),
                new Card("q", "h"),
                new Card("t", "d"),
                new Card("9", "c"),
                new Card("8", "s"),
                new Card("7", "d"),
                new Card("6", "h")
        ));
        assertThat(hand.getEvaluation()).isNotNull();
        assertThat(hand.getEvaluation().getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.STRAIGHT);
        assertThat(hand.getEvaluation().getStrength()).isEqualTo(Card.Rank.TEN);
        
        hand = SevenCardHand.fromStrings("k", "c", "2", "h", "A", "d", "A", "s", "A", "c", "2", "d", "5", "d");
        
        assertThat(hand.getHand()).isEqualTo(List.of(
                new Card("k", "c"),
                new Card("2", "h"),
                new Card("A", "d"),
                new Card("A", "s"),
                new Card("A", "c"),
                new Card("2", "d"),
                new Card("5", "d")
        ));
        
        assertThat(hand.getOrdered()).isEqualTo(List.of(
                new Card("A", "d"),
                new Card("A", "s"),
                new Card("A", "c"),
                new Card("k", "c"),
                new Card("5", "d"),
                new Card("2", "h"),
                new Card("2", "d")

        ));
        
        assertThat(hand.getEvaluation()).isNotNull();
        assertThat(hand.getEvaluation().getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.FULL_HOUSE);
        assertThat(hand.getEvaluation().getStrength()).isEqualTo(Card.Rank.ACE);
        assertThat(hand.getEvaluation().getSecondaryStrength()).isEqualTo(Card.Rank.DEUCE);
        assertThat(hand.getEvaluation().getKickers()).isEmpty();
    }
    
    @Test
    public void testSevenCardHandConstructorError() {
        assertThatThrownBy(() -> {
            new SevenCardHand(List.of(
                new Card("a", "c"),
                new Card("q", "h"),
                new Card("q", "d"),
                new Card("2", "s"),
                new Card("9", "c")));
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            new SevenCardHand(List.of(
                new Card("4", "c"),
                new Card("q", "h"),
                new Card("q", "d"),
                new Card("2", "s"),
                new Card("4", "d"),
                new Card("3", "h")));
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            new SevenCardHand(List.of(
                new Card("4", "c"),
                new Card("q", "h"),
                new Card("q", "d"),
                new Card("2", "s"),
                new Card("4", "d"),
                new Card("3", "h"),
                new Card("5", "c"),
                new Card("6", "d")));
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            new SevenCardHand(List.of());
        }).isInstanceOf(IllegalArgumentException.class);
        
        List<Card> cards = null;
        assertThatThrownBy(() -> {
            new SevenCardHand(cards);
        }).isInstanceOf(IllegalArgumentException.class);
    }
    
    private SevenCardHand createHand(String... cards) {
        return SevenCardHand.fromStrings(cards);
    }
    
    // straight fushes
    SevenCardHand sfToQueenwS = createHand("7", "c", "t", "c", "6", "c", "9", "c", "j", "c", "q", "c", "8", "c");
    SevenCardHand sfToQueenwH = createHand("A", "c", "t", "h", "2", "h", "9", "h", "j", "h", "q", "h", "8", "h");
    SevenCardHand sfTo5wD = createHand("3", "d", "A", "d", "2", "d", "Q", "d", "j", "d", "4", "d", "5", "d");
    
    // quads
    SevenCardHand quadAw8kicker = createHand("A", "c", "A", "h", "A", "d", "8", "s", "2", "c", "3", "d", "A", "s");
    SevenCardHand quad8wAkicker = createHand("8", "c", "8", "h", "8", "d", "A", "s", "A", "c", "A", "d", "8", "s");

    // full houses
    SevenCardHand fullHouseQueenOver5 = createHand("Q", "c", "Q", "h", "Q", "d", "5", "s", "5", "c", "2", "d", "j", "h");
    SevenCardHand fullHouse9Over8 = createHand("9", "c", "9", "h", "9", "d", "8", "s", "8", "c", "A", "d", "8", "h");
    
    // flushes
    SevenCardHand flushToAce = createHand("2", "c", "A", "c", "Q", "c", "J", "c", "8", "c", "4", "c", "K", "h");
    SevenCardHand flushToAceLower = createHand("2", "s", "A", "s", "Q", "c", "J", "s", "8", "s", "4", "s", "K", "d");
    SevenCardHand flushToQueen = createHand("2", "d", "Q", "d", "J", "d", "8", "d", "4", "d", "K", "h", "A", "h");
    
    // straights
    SevenCardHand straightTo5 = createHand("3", "c", "4", "h", "5", "d", "A", "s", "7", "c", "8", "d", "2", "h");
    SevenCardHand straightToJack = createHand("J", "c", "7", "h", "6", "d", "5", "s", "T", "c", "9", "d", "8", "h");
    SevenCardHand straightToJack2 = createHand("J", "c", "7", "h", "J", "d", "J", "s", "T", "c", "9", "d", "8", "h");
    
    // three of a kind
    SevenCardHand threeOfKing = createHand("K", "c", "K", "h", "K", "d", "2", "s", "5", "c", "3", "d", "A", "s");
    SevenCardHand threeOfKingLower = createHand("5", "c", "K", "h", "K", "d", "2", "s", "K", "c", "3", "d", "Q", "s");
    SevenCardHand threeOfSix = createHand("Q", "c", "A", "h", "6", "d", "2", "s", "6", "c", "6", "s", "K", "s");
    
    // two pairs
    SevenCardHand twoPairAceOverQueen = createHand("A", "c", "A", "h", "Q", "d", "Q", "s", "J", "c", "3", "d", "3", "s");
    SevenCardHand twoPairAceOverQueenLower = createHand("A", "c", "A", "h", "Q", "d", "Q", "s", "T", "c", "3", "d", "T", "s");
    
    // one pair
    SevenCardHand pairOfKings = createHand("K", "c", "K", "h", "2", "d", "3", "s", "5", "c", "9", "d", "A", "s");
    SevenCardHand pairOfKingsLower = createHand("K", "c", "K", "h", "2", "d", "3", "s", "5", "c", "4", "d", "Q", "s");
    
    // high card
    SevenCardHand highCardAce = createHand("A", "c", "K", "h", "Q", "d", "J", "s", "4", "c", "9", "d", "8", "h");
    SevenCardHand NineHighCard = createHand("9", "c", "7", "h", "8", "d", "5", "s", "4", "c", "3", "d", "2", "h");

    @Test
    public void testStraightFlush() {
        PokerHandEvaluation evaluation = sfToQueenwS.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.STRAIGHT_FLUSH);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.QUEEN);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEmpty();
        
        evaluation = sfToQueenwH.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.STRAIGHT_FLUSH);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.QUEEN);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEmpty();
        
        evaluation = sfTo5wD.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.STRAIGHT_FLUSH);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.FIVE);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEmpty();
    }
    
    @Test
    public void testFourOfAKind() {
        PokerHandEvaluation evaluation = quadAw8kicker.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.FOUR_OF_A_KIND);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.ACE);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).hasSize(1);
        assertThat(evaluation.getKickers().get(0).getRank()).isEqualTo(Card.Rank.EIGHT);
        
        evaluation = quad8wAkicker.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.FOUR_OF_A_KIND);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.EIGHT);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).hasSize(1);
        assertThat(evaluation.getKickers().get(0).getRank()).isEqualTo(Card.Rank.ACE);
    }
    
    @Test
    public void testFullHouse() {
        PokerHandEvaluation evaluation = fullHouseQueenOver5.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.FULL_HOUSE);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.QUEEN);
        assertThat(evaluation.getSecondaryStrength()).isEqualTo(Card.Rank.FIVE);
        assertThat(evaluation.getKickers()).isEmpty();
        
        evaluation = fullHouse9Over8.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.FULL_HOUSE);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.NINE);
        assertThat(evaluation.getSecondaryStrength()).isEqualTo(Card.Rank.EIGHT);
        assertThat(evaluation.getKickers()).isEmpty();
    }
    
    @Test
    public void testFlush() {
        PokerHandEvaluation evaluation = flushToAce.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.FLUSH);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.ACE);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("Q", "c"),
                new Card("J", "c"),
                new Card("8", "c"),
                new Card("4", "c")
        ));
        
        evaluation = flushToAceLower.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.FLUSH);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.ACE);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("J", "s"),
                new Card("8", "s"),
                new Card("4", "s"),
                new Card("2", "s")
        ));
        
        evaluation = flushToQueen.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.FLUSH);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.QUEEN);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("J", "d"),
                new Card("8", "d"),
                new Card("4", "d"),
                new Card("2", "d")
        ));
    }
    
    @Test
    public void testStraight() {
        PokerHandEvaluation evaluation = straightTo5.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.STRAIGHT);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.FIVE);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEmpty();
        
        evaluation = straightToJack.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.STRAIGHT);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.JACK);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEmpty();
        
        evaluation = straightToJack2.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.STRAIGHT);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.JACK);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEmpty();
    }
    
    @Test
    public void testThreeOfAKind() {
        PokerHandEvaluation evaluation = threeOfKing.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.THREE_OF_A_KIND);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.KING);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("A", "s"),
                new Card("5", "c")

        ));
        
        evaluation = threeOfKingLower.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.THREE_OF_A_KIND);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.KING);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("Q", "s"),
                new Card("5", "c")
        ));
        
        evaluation = threeOfSix.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.THREE_OF_A_KIND);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.SIX);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("A", "h"),
                new Card("K", "s")
        ));
    }
    
    @Test
    public void testTwoPair() {
        PokerHandEvaluation evaluation = twoPairAceOverQueen.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.TWO_PAIR);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.ACE);
        assertThat(evaluation.getSecondaryStrength()).isEqualTo(Card.Rank.QUEEN);
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("J", "c")
        ));
        
        evaluation = twoPairAceOverQueenLower.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.TWO_PAIR);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.ACE);
        assertThat(evaluation.getSecondaryStrength()).isEqualTo(Card.Rank.QUEEN);
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("T", "c")
        ));
    }
    
    @Test
    public void testOnePair() {
        PokerHandEvaluation evaluation = pairOfKings.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.PAIR);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.KING);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("A", "s"),
                new Card("9", "d"),
                new Card("5", "c")
        ));
        
        evaluation = pairOfKingsLower.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.PAIR);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.KING);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("Q", "s"),
                new Card("5", "c"),
                new Card("4", "d")
        ));
    }
    
    @Test
    public void testHighCard() {
        PokerHandEvaluation evaluation = highCardAce.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.HIGH_CARD);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.ACE);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("K", "h"),
                new Card("Q", "d"),
                new Card("J", "s"),
                new Card("9", "d")
        ));
        
        evaluation = NineHighCard.getEvaluation();
        assertThat(evaluation.getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.HIGH_CARD);
        assertThat(evaluation.getStrength()).isEqualTo(Card.Rank.NINE);
        assertThat(evaluation.getSecondaryStrength()).isNull();
        assertThat(evaluation.getKickers()).isEqualTo(List.of(
                new Card("8", "d"),
                new Card("7", "h"),
                new Card("5", "s"),
                new Card("4", "c")

        ));
    }
}
