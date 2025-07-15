package com.akhayat.poker.simulator.card;


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

}
