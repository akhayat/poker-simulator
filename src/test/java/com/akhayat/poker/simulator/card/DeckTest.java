package com.akhayat.poker.simulator.card;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DeckTest {

    @Test
    public void testDealAndShuffle() {
        Deck deck = new Deck();
        assertEquals(new Card(2, 'C'), deck.dealCard());
        assertEquals(new Card(3, 'C'), deck.dealCard());
        assertEquals(new Card(4, 'C'), deck.peekTopCard());
        assertEquals(new Card(4, 'C'), deck.dealCard());
        assertEquals(new ArrayList<Card>(), deck.dealCards(-12));
        assertEquals(List.of(new Card(5, 'C'), new Card(6, 'C'), new Card(7, 'C'), new Card(8, 'C'), new Card(9, 'C')), deck.dealCards(5));
        assertEquals(new Card(10, 'C'), deck.peekTopCard());
        assertEquals(44, deck.dealCards(52).size());
        assertEquals(0, deck.dealCards(1).size());
        assertEquals(null, deck.dealCard());
        assertEquals(52, deck.getTopCardIndex());
        assertEquals(null, deck.dealCard());
        assertEquals(52, deck.getTopCardIndex());
        
        deck.resetTopCard();
        assertEquals( new Card(2, 'C'), deck.peekTopCard());
        
        deck.cut();
        assertEquals(new Card(2, 'H'), deck.peekTopCard());
        
        Deck deck2 = new Deck(10);
        // there's an astronomical chance this fails
        assertNotEquals(new Deck(), deck2);
        
        assertEquals(new Deck(), new Deck(-5));
    }
    
    @Test
    public void testPutCards() {
        Deck deck = new Deck();
        deck.putCard(new Card("a", "h"), 26);
        
        assertThat(deck.cardAt(26).getRank()).isEqualTo(Card.Rank.ACE);
        assertThat(deck.cardAt(26).getSuit()).isEqualTo(Card.Suit.HEARTS);
        
        deck.putCardOnTop(new Card("a", "h"));
        
        assertThat(deck.cardAt(0).getRank()).isEqualTo(Card.Rank.ACE);
        assertThat(deck.cardAt(0).getSuit()).isEqualTo(Card.Suit.HEARTS);
        assertThat(deck.cardAt(1).getRank()).isEqualTo(Card.Rank.DEUCE);
        assertThat(deck.cardAt(1).getSuit()).isEqualTo(Card.Suit.CLUBS);
        assertThat(deck.cardAt(26)).isNotEqualTo(new Card("a", "h"));
        assertThat(deck.size()).isEqualTo(52);
        
        deck.putCard(new Card("a", "h"), 51);
        
        assertThat(deck.cardAt(51).getRank()).isEqualTo(Card.Rank.ACE);
        assertThat(deck.cardAt(51).getSuit()).isEqualTo(Card.Suit.HEARTS);
        assertThat(deck.cardAt(0).getRank()).isNotEqualTo(Card.Rank.ACE);
        
        
        deck.putCard(new Card("10", "d"), -5);
        
        assertThat(deck.cardAt(0).getRank()).isEqualTo(Card.Rank.TEN);
        assertThat(deck.cardAt(0).getSuit()).isEqualTo(Card.Suit.DIAMONDS);
        
        deck.putCard(new Card("Q", "s"), 51);
        
        assertThat(deck.cardAt(51).getRank()).isEqualTo(Card.Rank.QUEEN);
        assertThat(deck.cardAt(51).getSuit()).isEqualTo(Card.Suit.SPADES);
        
        deck.putCard(new Card("7", "s"), 52);
        
        assertThat(deck.cardAt(51).getRank()).isEqualTo(Card.Rank.SEVEN);
        assertThat(deck.cardAt(51).getSuit()).isEqualTo(Card.Suit.SPADES);
        
        deck.putCards(List.of(new Card("j", "c"), new Card("4", "d")), 26);
        
        assertThat(deck.cardAt(26).getRank()).isEqualTo(Card.Rank.JACK);
        assertThat(deck.cardAt(26).getSuit()).isEqualTo(Card.Suit.CLUBS);
        assertThat(deck.cardAt(27).getRank()).isEqualTo(Card.Rank.FOUR);
        assertThat(deck.cardAt(27).getSuit()).isEqualTo(Card.Suit.DIAMONDS);
        
        deck.putCards(List.of(new Card("2", "h"), new Card("3", "s"), new Card("2", "h")), 0);
        
        assertThat(deck.cardAt(0).getRank()).isEqualTo(Card.Rank.DEUCE);
        assertThat(deck.cardAt(0).getSuit()).isEqualTo(Card.Suit.HEARTS);
        assertThat(deck.cardAt(1).getRank()).isEqualTo(Card.Rank.THREE);
        assertThat(deck.cardAt(1).getSuit()).isEqualTo(Card.Suit.SPADES);
        assertThat(deck.cardAt(2)).isNotEqualTo(new Card("2", "h"));
        
        deck.putCards(List.of(), 0);
        
        assertThat(deck.cardAt(0).getRank()).isEqualTo(Card.Rank.DEUCE);
        assertThat(deck.cardAt(0).getSuit()).isEqualTo(Card.Suit.HEARTS);
        assertThat(deck.size()).isEqualTo(52);
        
        deck.putCards(null, 0);
        
        assertThat(deck.cardAt(0).getRank()).isEqualTo(Card.Rank.DEUCE);
        assertThat(deck.cardAt(0).getSuit()).isEqualTo(Card.Suit.HEARTS);
                
    }

}
