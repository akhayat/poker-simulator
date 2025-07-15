package com.akhayat.poker.simulator.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.akhayat.poker.simulator.evaluator.PokerHandEvaluation;

class PokerHandTest {
    
    @Test
    public void testPokerHandConstructors() {
        PokerHand hand = new PokerHand(List.of(
                new Card("10", "h"),
                new Card("k", "d"),
                new Card("A", "s"),
                new Card("j", "c"),
                new Card("Q", "h")
        ));
        assertThat(hand.getHand()).isEqualTo(List.of(
                new Card("10", "h"),
                new Card("k", "d"),
                new Card("A", "s"),
                new Card("j", "c"),
                new Card("Q", "h")
        ));
        assertThat(hand.getOrdered()).isEqualTo(List.of(
                new Card("A", "s"),
                new Card("k", "d"),
                new Card("Q", "h"),
                new Card("j", "c"),
                new Card("10", "h")
        ));
        assertThat(hand.getEvaluation()).isNotNull();
        assertThat(hand.getEvaluation().getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.STRAIGHT);
        
        hand = PokerHand.fromStrings("4", "h", "5", "c", "4", "d", "4", "s", "4", "c");
        
        assertThat(hand.getHand()).isEqualTo(List.of(
                new Card("4", "h"),
                new Card("5", "c"),
                new Card("4", "d"),
                new Card("4", "s"),
                new Card("4", "c")
        ));
    }
    
    @Test
    public void testPokerHandConstructorErrors() {
        assertThatThrownBy(() -> {
            new PokerHand(List.of(
                    new Card("10", "h"),
                    new Card("k", "d"),
                    new Card("A", "s"),
                    new Card("j", "c")
            ));
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            new PokerHand(List.of(
                    new Card("10", "h"),
                    new Card("k", "d"),
                    new Card("A", "s"),
                    new Card("j", "c"),
                    new Card("Q", "h"),
                    new Card("2", "d")
            ));
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            new PokerHand(List.of());
        }).isInstanceOf(IllegalArgumentException.class);
        
        List<Card> cards = null;
        assertThatThrownBy(() -> {
            new PokerHand(cards);
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            new PokerHand();
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            PokerHand.fromStrings("10", "h", "k", "d", "A", "s", "j", "c");
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            PokerHand.fromStrings("10", "h", "k", "d", "A", "s", "j", "c", "Q", "h", "2", "d");
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            PokerHand.fromStrings("F", "f", "c", "fdsa");
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            PokerHand.fromStrings("10", "h", "k", "d", "A", "s", "j", "c", "Q");
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            PokerHand.fromStrings("h", "10", "d", "k", "s", "a", "c", "j", "h", "q");
        }).isInstanceOf(IllegalArgumentException.class);
        
    }

}
