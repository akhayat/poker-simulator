package com.akhayat.poker.simulator.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.akhayat.poker.simulator.card.Card.Rank;

public class CardTest {

    @Test
    public void testConstructors() {
        Card a = new Card(2, 'H');
        Card b = new Card(Card.Rank.JACK, Card.Suit.DIAMONDS);
        Card c = new Card("ACE", "SPADES");
        Card d = new Card(a);
        Card e = new Card("5", "clubs");
        Card f = new Card(Card.Rank.fromValue(10), Card.Suit.fromChar('C'));
        
        assertEquals(a.getRank(), Card.Rank.DEUCE);
        assertEquals(a.getSuit(), Card.Suit.HEARTS);
        
        assertEquals(b.getRank(), Card.Rank.JACK);
        assertEquals(b.getSuit(), Card.Suit.DIAMONDS);
        
        assertEquals(c.getRank(), Card.Rank.ACE);
        assertEquals(c.getSuit(), Card.Suit.SPADES);
        
        assertEquals(d.getRank(), Card.Rank.DEUCE);
        assertEquals(d.getSuit(), Card.Suit.HEARTS);
        
        assertEquals(e.getRank(), Card.Rank.FIVE);
        assertEquals(e.getSuit(), Card.Suit.CLUBS);
        
        assertEquals(f.getRank(), Card.Rank.TEN);
        assertEquals(f.getSuit(), Card.Suit.CLUBS);
    }
    
    @Test
    public void testErrors() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Card("INVALID", "H");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Card(1, 'c');
            new Card(10, 'X');
        });
    }
    
    @Test
    public void testEquals() {
        Card a = new Card("K", "H");
        Card b = new Card(Rank.KING, Card.Suit.HEARTS);
        Card c = new Card("K", "D");
        Card d = new Card(Rank.TEN, Card.Suit.DIAMONDS);
        
        assertEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, d);
    }
    
    @Test
    public void testCompareTo() {
        Card a = new Card("2", "H");
        Card b = new Card("3", "D");
        Card c = new Card("A", "S");
        Card d = new Card("K", "C");
        
        assertTrue(a.compareTo(b) < 0); 
        assertTrue(b.compareTo(a) > 0);
        assertTrue(a.compareTo(c) < 0);
        assertTrue(c.compareTo(d) > 0); 
        assertTrue(a.compareTo(new Card("2", "C")) == 0);
    }
    
    @Test
    public void testToString() {
        Card a = new Card("Q", "H");
        Card b = new Card("10", "D");
        Card c = new Card("2", "S");
        Card d = new Card("7", "C");
        Card e = new Card("A", "H");
        
        assertEquals("Q♡", a.toString());
        assertEquals("10♢", b.toString());
        assertEquals("2♠", c.toString());
        assertEquals("7♣", d.toString());
        assertEquals("A♡", e.toString());
    }

}
